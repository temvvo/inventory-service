package com.challenge.adapter.api.validator;

import com.challenge.adapter.api.exception.InvalidCsvContentException;
import com.challenge.adapter.api.model.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
@Service
public class CsvFileValidator {
    private static final Set<String> ALLOWED_TYPES = Set.of("jewellery", "watch");

    private static final int SERIAL_NUMBER_COLUMN = 0;
    private static final int REFERENCE_COLUMN = 1;
    private static final int SIZE_COLUMN = 2;
    private static final int TYPE_COLUMN = 3;
    private static final int PRODUCTION_DATE_COLUMN = 4;


    public List<ProductDto> validate(InputStream inputStream) {
        log.info("Starting CSV file validation and parsing...");
        List<ProductDto> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1 && line.toLowerCase().contains("serial")) {
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }

                String[] columns = line.split(",", -1);

                if (columns.length < 5) {
                    log.warn("Validation error at line {}: Expected at least 5 columns but found {}", lineNumber, columns.length);
                    throw new InvalidCsvContentException(
                            "Line %d: Invalid number of columns. Expected at least 5.".formatted(lineNumber)
                    );
                }

                String serialNumber = columns[SERIAL_NUMBER_COLUMN].trim();
                String reference = columns[REFERENCE_COLUMN].trim();
                String size = columns[SIZE_COLUMN].trim();
                String type = columns[TYPE_COLUMN].trim();
                String dateStr = columns[PRODUCTION_DATE_COLUMN].trim();


                if (serialNumber.isBlank()) {
                    log.warn("Validation error at line {}: 'serial_number' is empty", lineNumber);
                    throw new InvalidCsvContentException("Line %d: 'serial_number' cannot be null or empty.".formatted(lineNumber));
                }
                if (reference.isBlank()) {
                    log.warn("Validation error at line {}: 'reference' is empty", lineNumber);
                    throw new InvalidCsvContentException("Line %d: 'reference' cannot be null or empty.".formatted(lineNumber));
                }
                if (type.isBlank()) {
                    log.warn("Validation error at line {}: 'type' is empty", lineNumber);
                    throw new InvalidCsvContentException("Line %d: 'type' cannot be null or empty.".formatted(lineNumber));
                }

                if (!ALLOWED_TYPES.contains(type.toLowerCase())) {
                    log.warn("Validation error at line {}: Invalid type '{}'. Allowed: {}", lineNumber, type, ALLOWED_TYPES);
                    throw new InvalidCsvContentException(
                            "Line %d: Invalid type '%s'. Allowed types are 'jewellery' or 'watch'.".formatted(lineNumber, type)
                    );
                }
                LocalDate productionDate;
                try {
                    productionDate = LocalDate.parse(dateStr);
                } catch (DateTimeParseException e) {
                    log.error("Validation error at line {}: Invalid date format '{}'", lineNumber, dateStr);
                    throw new InvalidCsvContentException(
                            "Line %d: Invalid production_date '%s'. Expected format YYYY-MM-DD.".formatted(lineNumber, dateStr)
                    );
                }

                ProductDto product = ProductDto.builder()
                        .serialNumber(serialNumber)
                        .reference(reference)
                        .size(size)
                        .type(type)
                        .productionDate(productionDate)
                        .build();

                products.add(product);
            }

        } catch (InvalidCsvContentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while processing CSV stream", e);
            throw new InvalidCsvContentException("Failed to process CSV file: " + e.getMessage());
        }
        log.info("CSV validation completed successfully. Total valid products parsed: {}", products.size());
        return products;
    }
}