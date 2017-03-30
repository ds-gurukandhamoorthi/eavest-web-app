package com.synovia.digital;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.model.EavAccount;
import com.synovia.digital.model.EavArticle;
import com.synovia.digital.model.EavParams;
import com.synovia.digital.model.EavRole;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdRule;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdSousJacentValue;
import com.synovia.digital.model.PrdStatus;
import com.synovia.digital.model.PrdUser;
import com.synovia.digital.repository.EavAccountRepository;
import com.synovia.digital.repository.EavParamsRepository;
import com.synovia.digital.repository.EavRoleRepository;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdSousJacentValueRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.repository.PrdUserRepository;
import com.synovia.digital.utils.EavUtils;
import com.synovia.digital.utils.PrdStatusEnum;

@SpringBootApplication
@EnableScheduling
public class EavestWebAppApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(EavestWebAppApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.FRENCH);
		return slr;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	InitializingBean saveData(EavAccountRepository repo, PrdStatusRepository prdStatusRepo, EavRoleRepository roleRepo,
			PrdSousJacentRepository prdSsjctRepo, EavParamsRepository eavParamsRepo, PrdUserRepository userRepo,
			PrdSousJacentValueRepository ssjctValueRepo, PrdProductRepository productRepo) {
		return () -> {
			// Initialize Eavest Parameters 
			EavParams eavParams = new EavParams();
			eavParams.setIsActive(true);
			EavArticle article = new EavArticle();
			article.setUrl(new URL("http://www.synovia.fr/synovia-digital"));
			article.setAuthor("Laurent BOCQUET");
			article.setTitle("Le pôle DIGITAL de SYNOVIA");
			article.setReleaseDate(DtoDateFormat.getFormat().parse("2017-03-17"));
			article.setContent("L’offre de développement logiciel, applicatif, web et mobile, tierce maintenance"
					+ " applicative répond aux besoins d’innovation numérique de nos clients en s’appuyant sur...");
			eavParams.setLeftArticle(article);

			EavArticle article2 = new EavArticle();
			article2.setUrl(new URL("http://www.synovia.fr/synovia-pmo"));
			article2.setAuthor("Jean-Marc GUILBAULT");
			article2.setTitle("Le pôle PMO de SYNOVIA");
			article2.setReleaseDate(DtoDateFormat.getFormat().parse("2017-02-10"));
			article2.setContent("L’offre de PMO (Project Management Office) destinée principalement à accompagner "
					+ "nos clients...");
			eavParams.setRightArticle(article2);
			eavParamsRepo.save(eavParams);
			// Create default accounts (administrators)
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
			EavAccount adminUser = new EavAccount("adm@mailinator.com", "admin", "Couriol", "Teddy", eavRoles);
			repo.save(adminUser);
			Set<EavRole> eavRolesAdmin = new HashSet<>();
			eavRolesAdmin.add(adminRole);
			repo.save(new EavAccount("eavadm@mailinator.com", "eav", "Marlot", "Pascal", eavRolesAdmin));
			// Create the corresponding PrdUser entity
			PrdUser user = new PrdUser(adminUser);
			userRepo.save(user);

			PrdStatus idle = new PrdStatus(PrdStatusEnum.IDLE.toString(), "prd.status.idle");
			PrdStatus subscribable = new PrdStatus(PrdStatusEnum.SUBSCRIBABLE.toString(), "prd.status.subscribable");
			PrdStatus onGoing = new PrdStatus(PrdStatusEnum.ON_GOING.toString(), "prd.status.ongoing");
			PrdStatus prepayed = new PrdStatus(PrdStatusEnum.PREPAYED.toString(), "prd.status.prepayed");
			PrdStatus refunded = new PrdStatus(PrdStatusEnum.REFUNDED.toString(), "prd.status.refunded");
			prdStatusRepo.save(Arrays.asList(new PrdStatus[] { idle, subscribable, onGoing, prepayed, refunded }));

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

			// Complete the underlying asset with their historic values
			DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			List<String[]> ssjctValues = readSousJacentValues(new StringBuilder(EavUtils.mainResourcesDirectoryPath())
					.append(EavUtils.FILE_SEPARATOR).append("valeurs_Indices.csv").toString());

			for (String[] value : ssjctValues) {
				// 0 - label sous-jacent
				// 1 - isin
				// 2 - bloomberg
				// 3 - date
				// 4 - value
				// Find the corresponding stock market
				PrdSousJacent ssjct = prdSsjctRepo.findByBloombergCode(value[2]);
				PrdSousJacentValue v = new PrdSousJacentValue(ssjct, f.parse(value[3]), Double.valueOf(value[4]));
				ssjctValueRepo.save(v);

			}

			// Create default products
			int nbCreatedProducts = 20;
			Date defaultLaunchDate = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2012");
			Date defaultDueDate = new Date();
			for (int i = 0; i < nbCreatedProducts; i++) {
				PrdProduct basic = new PrdProduct();
				basic.setLabel(new StringBuilder("Autocreated TCO default ").append(i).toString());
				basic.setIsin(new StringBuilder("TCO00132-").append(i).toString());
				basic.setPrdSousJacent(indices.get(i % 9));
				basic.setLaunchDate(DateUtils.addDays(defaultLaunchDate, -i));
				basic.setDueDate(DateUtils.addDays(defaultDueDate, 2 * i + 1));
				basic.setDeliver("Natixis");
				basic.setIsEavest(i % 3 == 0 ? true : false);
				basic.setPrdRule(new PrdRule(77d + i - nbCreatedProducts, 88d + i - nbCreatedProducts,
						100d + i - nbCreatedProducts));
				basic.setObservationFrequency("une fois par an les années 1 à 8");
				basic.setStrike(380.28 + i * 100);
				basic.setCouponValue(5d + i);
				basic.setPrdStatus(idle);

				productRepo.save(basic);
			}
		};
	}

	private static List<String[]> readSousJacentValues(String filepath) {
		List<String[]> ssJacentValues = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			// Skip first line
			scanner.nextLine();

			scanner.useDelimiter(";");

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				String[] ssJacentInfo = line.split(scanner.delimiter().pattern());
				ssJacentValues.add(ssJacentInfo);

			}

		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ssJacentValues;
	}
}
