package com.challenge.adapter.db;

import com.challenge.adapter.db.entity.ProductEntity;
import com.challenge.adapter.db.mapper.ProductEntityMapper;
import com.challenge.adapter.db.repository.ProductRepository;
import com.challenge.model.Product;
import com.challenge.model.PagedProduct;
import com.challenge.ports.out.ProductDBPort;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductDBPortAdapter implements ProductDBPort {
    private final ProductRepository productRepository;
    private final ProductEntityMapper mapper = Mappers.getMapper(ProductEntityMapper.class);

    public ProductDBPortAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveAll(String clientCode, List<Product> products) {
        List<ProductEntity> productEntities = mapper.toClientProductEntities(products,clientCode);
        productRepository.saveAll(productEntities);

    }

    @Override
    public PagedProduct<Product> findProductsByClient(String clientCode, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> entityPage = productRepository.findAllByClientCode(pageable, clientCode);
        return mapper.toPagedProduct(entityPage);
    }
}
