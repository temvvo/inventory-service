package com.challenge.adapter.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@Data
public class ProductEntity {

    @EmbeddedId
    private ProductId id;

    private String type;

    @Column(name = "client_code")
    private String clientCode;

    @Column(name = "production_date")
    private LocalDate productionDate;
}