package com.synovia.digital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synovia.digital.model.PrdSousJacent;

public interface PrdSousJacentRepository extends JpaRepository<PrdSousJacent, Long> {

	public PrdSousJacent findByLabel(String label);
}
