package com.synovia.digital.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdUser;

public interface PrdProductRepository extends JpaRepository<PrdProduct, Long> {

	public PrdProduct findByIsin(String isin);

	public List<PrdProduct> findByEndDateAfter(Date from);

	public List<PrdProduct> findByDueDateBetween(Date from, Date until);

	public List<PrdProduct> findByIsBestSeller(Boolean isBestSeller);

	public List<PrdProduct> findByPrdUsers(Set<PrdUser> users);

	public List<PrdProduct> findByEndDateAfterAndPrdUsers(Date from, Set<PrdUser> users);

	public List<PrdProduct> findByLabelAndDeliverAndPrdSousJacentAndIsEavest(String label, String deliver,
			PrdSousJacent sousJacent, Boolean isEavest);

	public List<PrdProduct> findByLabel(String label);

	public List<PrdProduct> findByLabelAndIsEavest(String label, Boolean isEavest);

	public List<PrdProduct> findByLabelAndPrdSousJacent(String label, PrdSousJacent sousJacent);

	public List<PrdProduct> findByLabelAndPrdSousJacentAndIsEavest(String label, PrdSousJacent sousJacent,
			Boolean isEavest);

	public List<PrdProduct> findByLabelAndDeliver(String label, String deliver);

	public List<PrdProduct> findByLabelAndDeliverAndIsEavest(String label, String deliver, Boolean isEavest);

	public List<PrdProduct> findByLabelAndDeliverAndPrdSousJacent(String label, String deliver,
			PrdSousJacent sousJacent);

	public List<PrdProduct> findByIsEavest(Boolean isEavest);

	public List<PrdProduct> findByPrdSousJacent(PrdSousJacent sousJacent);

	public List<PrdProduct> findByPrdSousJacentAndIsEavest(PrdSousJacent sousJacent, Boolean isEavest);

	public List<PrdProduct> findByDeliver(String deliver);

	public List<PrdProduct> findByDeliverAndIsEavest(String deliver, Boolean isEavest);

	public List<PrdProduct> findByDeliverAndPrdSousJacent(String deliver, PrdSousJacent sousJacent);

	public List<PrdProduct> findByDeliverAndPrdSousJacentAndIsEavest(String deliver, PrdSousJacent sousJacent,
			Boolean isEavest);

	public Page<PrdProduct> findByIsin(String isin, Pageable pageRequest);

	public Page<PrdProduct> findByIsBestSeller(Boolean isBestSeller, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndDeliverAndPrdSousJacentAndIsEavest(String label, String deliver,
			PrdSousJacent sousJacent, Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByLabel(String label, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndIsEavest(String label, Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndPrdSousJacent(String label, PrdSousJacent sousJacent, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndPrdSousJacentAndIsEavest(String label, PrdSousJacent sousJacent,
			Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndDeliver(String label, String deliver, Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndDeliverAndIsEavest(String label, String deliver, Boolean isEavest,
			Pageable pageRequest);

	public Page<PrdProduct> findByLabelAndDeliverAndPrdSousJacent(String label, String deliver,
			PrdSousJacent sousJacent, Pageable pageRequest);

	public Page<PrdProduct> findByIsEavest(Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByPrdSousJacent(PrdSousJacent sousJacent, Pageable pageRequest);

	public Page<PrdProduct> findByPrdSousJacentAndIsEavest(PrdSousJacent sousJacent, Boolean isEavest,
			Pageable pageRequest);

	public Page<PrdProduct> findByDeliver(String deliver, Pageable pageRequest);

	public Page<PrdProduct> findByDeliverAndIsEavest(String deliver, Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByDeliverAndPrdSousJacent(String deliver, PrdSousJacent sousJacent,
			Pageable pageRequest);

	public Page<PrdProduct> findByDeliverAndPrdSousJacentAndIsEavest(String deliver, PrdSousJacent sousJacent,
			Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByDeliverContainsIgnoreCase(String deliver, Pageable pageRequest);

	public Page<PrdProduct> findByLabelContainsIgnoreCase(String label, Pageable pageRequest);

	public Page<PrdProduct> findByLabelContainsIgnoreCaseAndDeliverContainsIgnoreCase(String label, String deliver,
			Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCase(String isin, Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndDeliverContainsIgnoreCase(String isin, String deliver,
			Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndLabelContainsIgnoreCase(String isin, String label,
			Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndLabelContainsIgnoreCaseAndDeliverContainsIgnoreCase(
			String isin, String label, String deliver, Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndLabelContainsIgnoreCaseAndDeliverContainsIgnoreCaseAndPrdSousJacent(
			String isin, String label, String deliver, PrdSousJacent sousJacent, Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndLabelContainsIgnoreCaseAndDeliverContainsIgnoreCaseAndIsEavest(
			String isin, String label, String deliver, Boolean isEavest, Pageable pageRequest);

	public Page<PrdProduct> findByIsinContainsIgnoreCaseAndLabelContainsIgnoreCaseAndDeliverContainsIgnoreCaseAndPrdSousJacentAndIsEavest(
			String isin, String label, String deliver, PrdSousJacent sousJacent, Boolean isEavest,
			Pageable pageRequest);

}
