package com.synovia.digital.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.synovia.digital.domain.PrdProduct;

public interface PrdProductRepository extends PagingAndSortingRepository<PrdProduct, Long> {

}
