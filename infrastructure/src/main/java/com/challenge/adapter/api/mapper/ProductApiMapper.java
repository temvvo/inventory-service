package com.challenge.adapter.api.mapper;

import com.challenge.adapter.api.model.PagedResponse;
import com.challenge.adapter.api.model.ProductDto;
import com.challenge.model.Product;
import com.challenge.model.PagedProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductApiMapper {

    ProductDto toDto(Product model);
    PagedResponse<ProductDto> toPagedResponse(PagedProduct<Product> pagedProduct);

    Product toModel(ProductDto dto);
    List<Product> toModel(List<ProductDto> dtoList);
}
