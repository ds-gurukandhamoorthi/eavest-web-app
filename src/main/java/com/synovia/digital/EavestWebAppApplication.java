package com.synovia.digital;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.synovia.digital.domain.EavAccount;
import com.synovia.digital.domain.PrdCurrency;
import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.repository.PrdCurrencyRepository;

@SpringBootApplication
public class EavestWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EavestWebAppApplication.class, args);
	}

	//	@Bean
	//	InitializingBean saveData(PrdProductRepository prdRepo, PrdSousJacentRepository ssjRepo) {
	//		return () -> {
	//			PrdSousJacent prdSousJacent = new PrdSousJacent("FR0013210317", "Indice CAC 40", "EUR");
	//			ssjRepo.save(prdSousJacent);
	//
	//			String label = "MFM RENDEMENT Janvier 2017";
	//			String dueDate = "19/02/2027";
	//			String emissionDate = "18/10/2016";
	//
	//			/*
	//			 * Parameters: label dueDate dateOfCreation idSousJacent idRule
	//			 * subscriptionStart subscriptionEnd coupon nominalValue isGuaranteed deliver
	//			 * guarantor startPrice
	//			 * 
	//			 */
	//			prdRepo.save(new PrdProduct(label, dueDate, emissionDate, prdSousJacent, null, "18/10/2016", "30/01/2017",
	//					null, null, false, null, null, null));
	//
	//		};
	//	}

	@Bean
	InitializingBean saveData(EavAccountRepository repo, PrdCurrencyRepository currencyRepo) {
		return () -> {
			repo.save(new EavAccount("admin@mailinator.com", "admin", "Couriol", "Teddy", "ROLE_ADMIN", "ROLE_USER"));
			currencyRepo.save(new PrdCurrency("EUR", "Euro"));
			currencyRepo.save(new PrdCurrency("DOL", "Dollars"));
			currencyRepo.save(new PrdCurrency("PND", "Pounds"));
		};
	}
}
