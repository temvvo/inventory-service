package com.challenge.adapter.db.mapper;

import com.challenge.adapter.db.entity.ProductEntity;
import com.challenge.model.Product;
import com.challenge.model.PagedProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {

    @Mapping(source = "id.serialNumber", target = "serialNumber")
    @Mapping(source = "id.reference", target = "reference")
    @Mapping(source = "id.size", target = "size")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "productionDate", target = "productionDate")
    Product toModel(ProductEntity entity);

    @Mapping(source = "content", target = "content")
    @Mapping(source = "number", target = "pageNumber")
    @Mapping(source = "size", target = "pageSize")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "totalPages", target = "totalPages")
    @Mapping(source = "last", target = "last")
    PagedProduct<Product> toPagedProduct(Page<ProductEntity> page);


    @Mapping(source = "product.serialNumber", target = "id.serialNumber")
    @Mapping(source = "product.reference", target = "id.reference")
    @Mapping(source = "product.size", target = "id.size")
    @Mapping(source = "product.type", target = "type")
    @Mapping(source = "product.productionDate", target = "productionDate")
    @Mapping(source = "clientCode", target = "clientCode")
    ProductEntity toProductEntity(Product product, String clientCode);

    default List<ProductEntity> toClientProductEntities(List<Product> products, String clientCode) {
        return products.stream()
                .map(product -> toProductEntity(product, clientCode))
                .collect(Collectors.toList());
    }
}