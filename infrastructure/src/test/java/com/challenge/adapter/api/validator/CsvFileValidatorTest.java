package com.challenge.adapter.api.validator;

import com.challenge.adapter.api.exception.InvalidCsvContentException;
import com.challenge.adapter.api.model.ProductDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CsvFileValidatorTest {

    @InjectMocks
    private CsvFileValidator validator;

    private InputStream createInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("Should successfully parse valid CSV file with header and empty lines")
    void validate_ShouldReturnProductList_WhenCsvIsValid() {
        String csvContent = """
                serial_number,reference,size,type,production_date
                SN-100,REF-A,M,jewellery,2026-05-15
                
                SN-200,REF-B,L,watch,2026-08-10
                """;
        InputStream inputStream = createInputStream(csvContent);

        List<ProductDto> result = validator.validate(inputStream);

        assertThat(result)
                .hasSize(2)
                .extracting(ProductDto::getSerialNumber)
                .containsExactly("SN-100", "SN-200");

        ProductDto first = result.get(0);
        assertThat(first.getReference()).isEqualTo("REF-A");
        assertThat(first.getSize()).isEqualTo("M");
        assertThat(first.getType()).isEqualTo("jewellery");
        assertThat(first.getProductionDate()).isEqualTo(LocalDate.of(2026, 5, 15));
    }

    @ParameterizedTest
    @ValueSource(strings = {"jewellery", "watch", "JEWELLERY", "Watch"})
    @DisplayName("Should accept valid case-insensitive product types")
    void validate_ShouldAcceptAllowedTypes(String type) {
        String csvContent = "SN-100,REF-A,M,%s,2026-01-01".formatted(type);
        InputStream inputStream = createInputStream(csvContent);

        List<ProductDto> result = validator.validate(inputStream);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("Should throw exception when line has fewer than 5 columns")
    void validate_ShouldThrowException_WhenColumnsFewerThanFive() {
        String csvContent = "SN-100,REF-A,M,watch"; // Only 4 columns
        InputStream inputStream = createInputStream(csvContent);

        assertThatThrownBy(() -> validator.validate(inputStream))
                .isInstanceOf(InvalidCsvContentException.class)
                .hasMessageContaining("Line 1: Invalid number of columns. Expected at least 5.");
    }

    @Test
    @DisplayName("Should throw exception when serial number is blank")
    void validate_ShouldThrowException_WhenSerialNumberIsBlank() {
        String csvContent = "   ,REF-A,M,watch,2026-01-01";
        InputStream inputStream = createInputStream(csvContent);

        assertThatThrownBy(() -> validator.validate(inputStream))
                .isInstanceOf(InvalidCsvContentException.class)
                .hasMessageContaining("Line 1: 'serial_number' cannot be null or empty.");
    }

    @Test
    @DisplayName("Should throw exception when reference is blank")
    void validate_ShouldThrowException_WhenReferenceIsBlank() {
        String csvContent = "SN-100,  ,M,watch,2026-01-01";
        InputStream inputStream = createInputStream(csvContent);

        assertThatThrownBy(() -> validator.validate(inputStream))
                .isInstanceOf(InvalidCsvContentException.class)
                .hasMessageContaining("Line 1: 'reference' cannot be null or empty.");
    }

    @Test
    @DisplayName("Should throw exception when type is invalid")
    void validate_ShouldThrowException_WhenTypeIsInvalid() {
        String csvContent = "SN-100,REF-A,M,electronics,2026-01-01";
        InputStream inputStream = createInputStream(csvContent);

        assertThatThrownBy(() -> validator.validate(inputStream))
                .isInstanceOf(InvalidCsvContentException.class)
                .hasMessageContaining("Line 1: Invalid type 'electronics'. Allowed types are 'jewellery' or 'watch'.");
    }

    @Test
    @DisplayName("Should throw exception when production_date has an invalid format")
    void validate_ShouldThrowException_WhenDateFormatIsInvalid() {
        String csvContent = "SN-100,REF-A,M,watch,10-08-2026"; // Not YYYY-MM-DD
        InputStream inputStream = createInputStream(csvContent);

        assertThatThrownBy(() -> validator.validate(inputStream))
                .isInstanceOf(InvalidCsvContentException.class)
                .hasMessageContaining("Line 1: Invalid production_date '10-08-2026'. Expected format YYYY-MM-DD.");
    }
}