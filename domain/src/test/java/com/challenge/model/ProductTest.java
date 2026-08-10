package com.challenge.model;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    private static final String SERIAL_NUMBER = "SN-98765";
    private static final String REFERENCE = "REF-2026-X";
    private static final String SIZE = "XL";
    private static final String TYPE = "ELECTRONICS";
    private static final LocalDate PRODUCTION_DATE = LocalDate.of(2026, 8, 10);

    @Test
    @DisplayName("Should correctly instantiate Product using Builder")
    void shouldBuildProductSuccessfully() {
        // Act
        Product product = Product.builder()
                .serialNumber(SERIAL_NUMBER)
                .reference(REFERENCE)
                .size(SIZE)
                .type(TYPE)
                .productionDate(PRODUCTION_DATE)
                .build();

        // Assert
        assertThat(product).isNotNull();
        assertThat(product.getSerialNumber()).isEqualTo(SERIAL_NUMBER);
        assertThat(product.getReference()).isEqualTo(REFERENCE);
        assertThat(product.getSize()).isEqualTo(SIZE);
        assertThat(product.getType()).isEqualTo(TYPE);
        assertThat(product.getProductionDate()).isEqualTo(PRODUCTION_DATE);
    }

    @Test
    @DisplayName("Should correctly instantiate Product using AllArgsConstructor")
    void shouldCreateProductWithAllArgsConstructor() {
        // Act
        Product product = new Product(SERIAL_NUMBER, REFERENCE, SIZE, TYPE, PRODUCTION_DATE);

        // Assert
        assertThat(product.getSerialNumber()).isEqualTo(SERIAL_NUMBER);
        assertThat(product.getReference()).isEqualTo(REFERENCE);
        assertThat(product.getSize()).isEqualTo(SIZE);
        assertThat(product.getType()).isEqualTo(TYPE);
        assertThat(product.getProductionDate()).isEqualTo(PRODUCTION_DATE);
    }
}