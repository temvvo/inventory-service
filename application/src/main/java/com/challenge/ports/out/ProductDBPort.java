package com.challenge.ports.out;

import com.challenge.model.Product;
import com.challenge.model.PagedProduct;

import java.util.List;

public interface ProductDBPort {
    void saveAll(String clientCode, List<Product> products);
    PagedProduct<Product> findProductsByClient(String clientCode, int page, int size);
}
