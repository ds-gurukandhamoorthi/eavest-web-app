package com.synovia.digital.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdUser;

public interface PrdProductRepository extends JpaRepository<PrdProduct, Long> {

	public PrdProduct findByIsin(String isin);

	public List<PrdProduct> findByEndDateAfter(Date from);

	public List<PrdProduct> findByDueDateBetween(Date from, Date until);

	public List<PrdProduct> findByIsBestSeller(Boolean isBestSeller);

	public List<PrdProduct> findByPrdUsers(Set<PrdUser> users);
}
