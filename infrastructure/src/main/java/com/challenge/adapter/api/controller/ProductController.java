package com.challenge.adapter.api.controller;
import com.challenge.adapter.api.exception.InvalidCsvContentException;
import com.challenge.adapter.api.mapper.ProductApiMapper;
import com.challenge.adapter.api.model.PagedResponse;
import com.challenge.adapter.api.model.ProductDto;
import com.challenge.ports.in.ProductUCPort;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUCPort productUCPort;
    private final ProductApiMapper mapper  = Mappers.getMapper(ProductApiMapper .class);;

    @GetMapping("/{client_code}")
    public ResponseEntity<PagedResponse<ProductDto>> getProducts(
            @PathVariable("client_code") String clientCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        return null;
    }


    @PostMapping(value = "/upload/{client_code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadProducts(
            @PathVariable("client_code") String clientCode,
            @RequestParam("file") @NotNull MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            log.error("Uploaded file is empty.");
            throw new InvalidCsvContentException("Uploaded file is empty.");
        }
        //TODO: validate csv

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
