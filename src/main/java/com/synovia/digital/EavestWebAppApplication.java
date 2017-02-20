package com.synovia.digital;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.EavRole;
import com.synovia.digital.model.PrdStatus;
import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.repository.EavRoleRepository;
import com.synovia.digital.repository.PrdStatusRepository;

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
	InitializingBean saveData(EavAccountRepository repo, PrdStatusRepository prdStatusRepo,
			EavRoleRepository roleRepo) {
		return () -> {
			EavRole admin = new EavRole(1, "ROLE_ADMIN", "EAVEST Administrator");
			roleRepo.save(admin);
			roleRepo.save(new EavRole(2, "ROLE_USER", "EAVEST User"));
			roleRepo.save(new EavRole(3, "ROLE_TESTER", "Development tester"));

			EavRole adminRole = roleRepo.findByLabel("ROLE_ADMIN");
			EavRole userRole = roleRepo.findByLabel("ROLE_USER");
			EavRole testerRole = roleRepo.findByLabel("ROLE_TESTER");
			Set<EavRole> eavRoles = new HashSet<>();
			eavRoles.add(adminRole);
			eavRoles.add(userRole);
			eavRoles.add(testerRole);
			repo.save(new EavAccount("adm@mailinator.com", "admin", "Couriol", "Teddy", eavRoles));
			Set<EavRole> eavRolesAdmin = new HashSet<>();
			eavRolesAdmin.add(adminRole);
			repo.save(new EavAccount("eavadm@mailinator.com", "eav", "Marlot", "Pascal", eavRolesAdmin));

			prdStatusRepo.save(new PrdStatus(1, "IDLE", "Initial state"));
			prdStatusRepo.save(new PrdStatus(2, "SUBSCRIBABLE", "Users can subscribe to the product"));
			prdStatusRepo.save(new PrdStatus(3, "STARTED", "The product evolution has started"));
			prdStatusRepo.save(new PrdStatus(4, "STOPPED", "The product evolution has ended"));
			prdStatusRepo
					.save(new PrdStatus(5, "CLOSED", "The product is stopped, no action can be done on the product"));
		};
	}
}
