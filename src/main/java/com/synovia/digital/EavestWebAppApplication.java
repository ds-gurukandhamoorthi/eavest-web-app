package com.synovia.digital;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.EavParams;
import com.synovia.digital.model.EavRole;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdStatus;
import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.repository.EavParamsRepository;
import com.synovia.digital.repository.EavRoleRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;

@SpringBootApplication
public class EavestWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EavestWebAppApplication.class, args);
	}

	@Bean
	InitializingBean saveData(EavAccountRepository repo, PrdStatusRepository prdStatusRepo, EavRoleRepository roleRepo,
			PrdSousJacentRepository prdSsjctRepo, EavParamsRepository eavParamsRepo) {
		return () -> {
			// Initialize Eavest Parameters 
			EavParams eavParams = new EavParams();
			eavParams.setIsActive(true);
			eavParamsRepo.save(eavParams);
			eavParams.setLeftArticle(
					new URL("http://eavest.com/janvier-2017-performance-des-indices-de-marche-et-nouveaux-indices/"));
			eavParams.setRightArticle(new URL(
					"http://eavest.com/le-sous-jacent-un-possible-mecanisme-supplementaire-pour-un-produit-structure/"));
			// Create default users (administrators)
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

			// Create the stock market indices
			PrdSousJacent cac40 = new PrdSousJacent("CAC 40");
			cac40.setIsNew(false);
			cac40.setIsPerfReview(true);
			cac40.setBloombergCode("CAC");

			PrdSousJacent eurostoxx50 = new PrdSousJacent("Euro Stoxx 50");
			eurostoxx50.setIsNew(false);
			eurostoxx50.setIsPerfReview(true);
			eurostoxx50.setBloombergCode("SX5E");

			PrdSousJacent eurostoxxDividend30 = new PrdSousJacent("Euro Stoxx Select Dividend 30");
			eurostoxxDividend30.setIsNew(false);
			eurostoxxDividend30.setIsPerfReview(true);
			eurostoxxDividend30.setBloombergCode("SD3E");

			// Create the new stock market indices
			PrdSousJacent cac40Large = new PrdSousJacent("CAC Large 60 EWER");
			cac40Large.setIsNew(true);
			cac40Large.setIsPerfReview(true);
			cac40Large.setBloombergCode("CLEWE");

			PrdSousJacent euroistoxxEWC = new PrdSousJacent("Euro iStoxx EWC 50");
			euroistoxxEWC.setIsNew(true);
			euroistoxxEWC.setIsPerfReview(true);
			euroistoxxEWC.setBloombergCode("ISXEC50");

			PrdSousJacent iStoxx30 = new PrdSousJacent("iSTOXX Europe Centenary Select 30");
			iStoxx30.setIsNew(true);
			iStoxx30.setIsPerfReview(true);
			iStoxx30.setBloombergCode("SXECS3P");

			PrdSousJacent iStoxx50 = new PrdSousJacent("iSTOXX Europe Select High Beta 50");
			iStoxx50.setIsNew(true);
			iStoxx50.setIsPerfReview(true);
			iStoxx50.setBloombergCode("ISXEHBP");

			PrdSousJacent msciEuro50 = new PrdSousJacent("MSCI Euro 50 Select 4,75% Decrement");
			msciEuro50.setIsNew(true);
			msciEuro50.setIsPerfReview(true);
			msciEuro50.setBloombergCode("M7EUSDA");

			PrdSousJacent solactive = new PrdSousJacent("Solactive Eurozone 50 EW");
			solactive.setIsNew(true);
			solactive.setIsPerfReview(true);
			solactive.setBloombergCode("SOLEW");

			List<PrdSousJacent> indices = Arrays.asList(cac40, eurostoxx50, eurostoxxDividend30, cac40Large,
					euroistoxxEWC, iStoxx30, iStoxx50, msciEuro50, solactive);
			prdSsjctRepo.save(indices);
		};
	}
}
