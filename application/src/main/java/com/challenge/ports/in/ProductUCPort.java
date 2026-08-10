package com.challenge.ports.in;

import com.challenge.model.Product;
import com.challenge.model.PagedProduct;

import java.util.List;

public interface ProductUCPort {
    void saveAllProducts( String clientCode, List<Product> products);
    PagedProduct<Product> getProductsByClient(String clientCode, int page, int size);
}
