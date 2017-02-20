/**
 * 
 */
package com.synovia.digital.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synovia.digital.model.PrdCurrency;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 3 févr. 2017
 */
public interface PrdCurrencyRepository extends CrudRepository<PrdCurrency, Integer> {

	public PrdCurrency findByCode(String code);

	public List<PrdCurrency> findByLabel(String label);
}
