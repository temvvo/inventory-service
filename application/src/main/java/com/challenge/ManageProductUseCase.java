package com.challenge;

import com.challenge.model.Product;
import com.challenge.model.PagedProduct;
import com.challenge.ports.in.ProductUCPort;
import com.challenge.ports.out.ProductDBPort;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ManageProductUseCase implements ProductUCPort {
    private final ProductDBPort databasePort;

    public ManageProductUseCase(ProductDBPort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public void saveAllProducts(String clientCode, List<Product> products) {
        databasePort.saveAll(clientCode, products);
    }

    @Override
    public PagedProduct<Product> getProductsByClient(String clientCode, int page, int size) {
        return databasePort.findProductsByClient(clientCode,page,size);
    }


}
