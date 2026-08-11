package com.challenge.adapter.db.repository;

import com.challenge.adapter.db.entity.ProductEntity;
import com.challenge.adapter.db.entity.ProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, ProductId> {
    Page<ProductEntity> findAllByClientCode(Pageable pageable, String clientCode);
}