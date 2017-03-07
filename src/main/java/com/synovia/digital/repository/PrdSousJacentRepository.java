package com.synovia.digital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.model.PrdSousJacent;

public interface PrdSousJacentRepository extends JpaRepository<PrdSousJacent, Long> {

	public PrdSousJacent findByLabel(String label);

	public List<PrdSousJacent> findByIsNew(Boolean isNew);

	public List<PrdSousJacent> findByLabelOrBloombergCode(String label, String bloombergCode);
}
