package com.challenge.adapter.db.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ProductId implements Serializable {

    @Column(name = "serial_number")
    private String serialNumber;

    private String reference;
    private String size;
}