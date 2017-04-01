/**
 * 
 */
package com.synovia.digital.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.dto.utils.DtoUtils;
import com.synovia.digital.exceptions.EavDuplicateEntryException;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavException;
import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.exceptions.utils.EavErrorCode;
import com.synovia.digital.filedataware.EavHomeDirectory;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdStatus;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.utils.PrdStatusEnum;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
public class PrdProductServiceImplTest {
	protected PrdProductRepository repoMock;
	protected PrdSousJacentRepository sousJacentRepoMock;
	protected PrdStatusRepository statusRepoMock;
	protected PrdProductService service;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	protected EavHomeDirectory homeMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repoMock = mock(PrdProductRepository.class);
		sousJacentRepoMock = mock(PrdSousJacentRepository.class);
		statusRepoMock = mock(PrdStatusRepository.class);
		homeMock = mock(EavHomeDirectory.class);
		service = new PrdProductServiceImpl(repoMock, sousJacentRepoMock, statusRepoMock, homeMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	public void testFindById() {
		Long idSsJct = 5L;
		try {
			Long id = 1L;
			String isin = "P123456TCF";
			String label = "EUROSTOXX TEST";
			Date dueDate = format.parse("20/04/2025");
			Date launchDate = format.parse("04/04/2016");
			PrdSousJacent sousJacent = new PrdSousJacent("SS-JCT");
			sousJacent.setId(idSsJct);

			PrdProduct product = new PrdProduct();
			product.setId(id);
			product.setIsin(isin);
			product.setLabel(label);
			product.setDueDate(dueDate);
			product.setLaunchDate(launchDate);
			product.setPrdSousJacent(sousJacent);

			when(repoMock.findOne(id)).thenReturn(product);

			PrdProduct toFind = service.findById(id);

			verify(repoMock, times(1)).findOne(id);
			verifyNoMoreInteractions(repoMock);

			Assert.assertThat(toFind, is(product));

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#findById(java.lang.Long)}.
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testFindById_ShouldThrowException() throws EavEntryNotFoundException {
		Long id = 1L;

		when(repoMock.findOne(id)).thenReturn(null);

		service.findById(id);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test
	public void testAdd_EntityWithMinimialSetOfParam() throws Exception {
		Long idSsJct = 5L;
		Long id = 1L;
		String isin = "P123456TCF";
		String label = "EUROSTOXX TEST";
		String dueDate = "2025-04-20";
		String launchDate = "2016-04-04";
		PrdSousJacent sousJacent = new PrdSousJacent("SS-JCT");
		sousJacent.setId(idSsJct);

		PrdProductDto productDto = new PrdProductDto();
		productDto.setId(id);
		productDto.setIsin(isin);
		productDto.setLabel(label);
		productDto.setDueDate(dueDate);
		productDto.setLaunchDate(launchDate);
		productDto.setIdPrdSousJacent(idSsJct);

		when(sousJacentRepoMock.findOne(idSsJct)).thenReturn(sousJacent);
		String code = PrdStatusEnum.IDLE.name();
		PrdStatus idleStatus = new PrdStatus(1, code, "Undefined status");
		when(statusRepoMock.findByCode(code)).thenReturn(idleStatus);
		code = PrdStatusEnum.ON_GOING.name();
		PrdStatus ongoingStatus = new PrdStatus(2, code, "On going");
		when(statusRepoMock.findByCode(code)).thenReturn(ongoingStatus);
		code = PrdStatusEnum.PREPAYED.name();
		PrdStatus prepayedStatus = new PrdStatus(3, code, "Prepayed");
		when(statusRepoMock.findByCode(code)).thenReturn(prepayedStatus);
		code = PrdStatusEnum.REFUNDED.name();
		PrdStatus payedStatus = new PrdStatus(4, code, "Payed");
		when(statusRepoMock.findByCode(code)).thenReturn(payedStatus);
		code = PrdStatusEnum.SUBSCRIBABLE.name();
		PrdStatus subscribeStatus = new PrdStatus(5, code, "Subscribable");
		when(statusRepoMock.findByCode(code)).thenReturn(subscribeStatus);

		// Test the creation of a product.
		service.add(productDto);

		ArgumentCaptor<PrdProduct> productArgument = ArgumentCaptor.forClass(PrdProduct.class);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(productArgument.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct saveEntity = productArgument.getValue();

		Assert.assertThat(saveEntity.getId(), is(id));
		Assert.assertEquals(saveEntity.getIsin(), isin);
		Assert.assertEquals(saveEntity.getLabel(), label);
		Assert.assertEquals(saveEntity.getDueDate(), productDto.getDueDateObject());
		Assert.assertEquals(saveEntity.getLaunchDate(), productDto.getLaunchDateObject());
		Assert.assertThat(saveEntity.getPrdSousJacent(), is(sousJacent));
		// Parameters with default value
		Assert.assertFalse(saveEntity.getIsEavest());
		Assert.assertFalse(saveEntity.getIsBestSeller());
		Assert.assertThat(saveEntity.getPrdStatus(), is(ongoingStatus));
		// All other parameters must be null
		Assert.assertThat(saveEntity.getSubscriptionEndDate(), is(nullValue()));
		Assert.assertThat(saveEntity.getSubscriptionStartDate(), is(nullValue()));
		Assert.assertThat(saveEntity.getCouponValue(), is(nullValue()));
		Assert.assertThat(saveEntity.getNominalValue(), is(nullValue()));
		Assert.assertThat(saveEntity.getCapitalGuaranteed(), is(nullValue()));
		Assert.assertThat(saveEntity.getStartPrice(), is(nullValue()));
		Assert.assertThat(saveEntity.getDeliver(), is(nullValue()));
		Assert.assertThat(saveEntity.getGuarantor(), is(nullValue()));
		Assert.assertThat(saveEntity.getEndDate(), is(nullValue()));
		Assert.assertThat(saveEntity.getPath(), is(nullValue()));
		Assert.assertThat(saveEntity.getStrike(), is(nullValue()));
		Assert.assertThat(saveEntity.getObservationFrequency(), is(nullValue()));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test(expected = EavTechnicalException.class)
	public void testAdd_EntityWithoutISIN() throws Exception {
		PrdProductDto productDto = new PrdProductDto();

		// Test the creation of a product without ISIN.
		service.add(productDto);
		ArgumentCaptor<PrdProduct> productArgument = ArgumentCaptor.forClass(PrdProduct.class);

		verify(repoMock, times(1)).findByIsin(null);
		verify(repoMock, times(1)).save(productArgument.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct result = productArgument.getValue();
		Assert.assertThat(result, is(notNullValue()));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test
	public void testAdd_NullEntry() throws Exception {
		// Test the creation with a null entry.
		PrdProduct result = service.add(null);

		verifyZeroInteractions(repoMock);

		Assert.assertThat(result, is(nullValue()));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test(expected = EavTechnicalException.class)
	public void testAdd_IncompleteEntity() throws Exception {
		Long id = 1L;
		String isin = "P123456TCF";

		PrdProductDto productDto = new PrdProductDto();
		productDto.setId(id);
		productDto.setIsin(isin);

		// Test the creation of a product.
		service.add(productDto);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test(expected = EavDuplicateEntryException.class)
	public void testAdd_DuplicateEntity() throws Exception {
		String isin = "Something";
		PrdProductDto productDto = new PrdProductDto();
		productDto.setIsin(isin);
		PrdProduct product = new PrdProduct();

		when(repoMock.findByIsin(isin)).thenReturn(product);

		// Create an existing product.
		service.add(productDto);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#add(com.synovia.digital.dto.PrdProductDto)}.
	 */
	@Test
	public void testAdd_EntityWithMaximalSetOfParam() throws Exception {
		Long idSsJct = 5L;
		Long id = 6L;
		String isin = "P123456TCF";
		String label = "EUROSTOXX TEST";
		String launchDate = "2016-04-04";
		String dueDate = "2025-04-20";
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setId(idSsJct);
		Double couponBarrier = 76d;
		Double protectionBarrier = 53d;
		String[] observationDates = new String[] { "2016-04-04", "2017-04-03", "2018-04-02", "2019-04-01" };
		String[] earlyRepayDates = new String[] { "2017-05-03", "2018-05-02", "2019-05-01" };
		String[] couponPayDates = new String[] { "2017-05-03", "2018-05-02", "2019-05-01" };
		String subscribeStartDate = "2016-03-15";
		String subscribeEndDate = "2016-04-02";
		Double coupon = 98d;
		Double nominal = 10000d;
		Boolean capitalGuaranted = true;
		Double startPrice = 100d;
		String deliver = "CIC";
		String guarantor = deliver;
		String status = PrdStatusEnum.ON_GOING.name();
		String effectiveEndDate = "2017-04-05";
		Boolean isEavest = true;
		Boolean isBestSeller = false;
		String path = "C:\\Workspace\\Projets\\EAVEST\\fdwh_example\\produits\\ABN_AMRO-CLN_Rallye";
		Double strike = 2456.45;
		String observationFrequency = "year";

		PrdProductDto productDto = new PrdProductDto();
		productDto.setId(id);
		productDto.setIsin(isin);
		productDto.setLabel(label);
		productDto.setDueDate(dueDate);
		productDto.setLaunchDate(launchDate);
		productDto.setIdPrdSousJacent(idSsJct);
		productDto.setCouponBarrier(couponBarrier);
		productDto.setProtectionBarrier(protectionBarrier);
		productDto.setObservationDates(observationDates);
		productDto.setEarlyRepaymentDates(earlyRepayDates);
		productDto.setCouponPaymentDates(couponPayDates);
		productDto.setSubscriptionStartDate(subscribeStartDate);
		productDto.setSubscriptionEndDate(subscribeEndDate);
		productDto.setCouponValue(coupon);
		productDto.setNominalValue(nominal);
		productDto.setCapitalGuaranteed(capitalGuaranted);
		productDto.setStartPrice(startPrice);
		productDto.setDeliver(deliver);
		productDto.setGuarantor(guarantor);
		productDto.setStatusCode(status);
		productDto.setEndDate(effectiveEndDate);
		productDto.setIsEavest(isEavest);
		productDto.setPath(path);
		productDto.setStrike(strike);
		productDto.setObservationFrequency(observationFrequency);

		// Mock the behavior of the underlying asset repository.
		when(sousJacentRepoMock.findOne(idSsJct)).thenReturn(sousJacent);
		String code;
		code = PrdStatusEnum.IDLE.name();
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(1, code, "Undefined"));
		code = PrdStatusEnum.ON_GOING.name();
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(2, code, "On going"));
		code = PrdStatusEnum.PREPAYED.name();
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(3, code, "Prepayed"));
		code = PrdStatusEnum.REFUNDED.name();
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(4, code, "Payed"));
		code = PrdStatusEnum.SUBSCRIBABLE.name();
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(5, code, "Subscribable"));

		// Test the creation of a product.
		service.add(productDto);

		ArgumentCaptor<PrdProduct> productArgument = ArgumentCaptor.forClass(PrdProduct.class);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(productArgument.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct saveEntity = productArgument.getValue();

		Assert.assertThat(saveEntity.getId(), is(id));
		Assert.assertEquals(saveEntity.getIsin(), isin);
		Assert.assertEquals(saveEntity.getLabel(), label);
		Assert.assertEquals(saveEntity.getDueDate(), productDto.getDueDateObject());
		Assert.assertEquals(saveEntity.getLaunchDate(), productDto.getLaunchDateObject());
		Assert.assertThat(saveEntity.getPrdSousJacent(), is(sousJacent));

		Assert.assertThat(saveEntity.getPrdRule().getCouponBarrier(), is(couponBarrier));
		Assert.assertThat(saveEntity.getPrdRule().getProtectionBarrier(), is(protectionBarrier));
		List<Date> dates = DtoUtils.convertAsList(productDto.getObservationDates());
		Assert.assertEquals(dates.size(), saveEntity.getObservationDates().size());
		for (PrdObservationDate d : saveEntity.getObservationDates()) {
			Assert.assertTrue(dates.contains(d.getDate()));
		}
		dates = DtoUtils.convertAsList(productDto.getEarlyRepaymentDates());
		Assert.assertEquals(dates.size(), saveEntity.getEarlyRepaymentDates().size());
		for (PrdEarlierRepaymentDate d : saveEntity.getEarlyRepaymentDates()) {
			Assert.assertTrue(dates.contains(d.getDate()));
		}
		dates = DtoUtils.convertAsList(productDto.getCouponPaymentDates());
		Assert.assertEquals(dates.size(), saveEntity.getCouponPaymentDates().size());
		for (PrdCouponDate d : saveEntity.getCouponPaymentDates()) {
			Assert.assertTrue(dates.contains(d.getDate()));
		}

		Assert.assertThat(saveEntity.getSubscriptionEndDate(), is(productDto.getSubscriptionEndDateObject()));
		Assert.assertThat(saveEntity.getSubscriptionStartDate(), is(productDto.getSubscriptionStartDateObject()));
		Assert.assertThat(saveEntity.getCouponValue(), is(coupon));
		Assert.assertThat(saveEntity.getNominalValue(), is(nominal));
		Assert.assertThat(saveEntity.getCapitalGuaranteed(), is(capitalGuaranted));
		Assert.assertThat(saveEntity.getStartPrice(), is(startPrice));
		Assert.assertThat(saveEntity.getDeliver(), is(deliver));
		Assert.assertThat(saveEntity.getGuarantor(), is(guarantor));
		Assert.assertThat(saveEntity.getPrdStatus().getCode(), is(status));
		Assert.assertThat(saveEntity.getEndDate(), is(productDto.getEndDateObject()));
		Assert.assertThat(saveEntity.getIsEavest(), is(isEavest));
		Assert.assertThat(saveEntity.getPath(), is(path));
		Assert.assertThat(saveEntity.getStrike(), is(strike));
		Assert.assertThat(saveEntity.getObservationFrequency(), is(observationFrequency));
		Assert.assertThat(saveEntity.getIsBestSeller(), is(isBestSeller));
	}

	@Test
	public void testSetBestSeller() throws EavEntryNotFoundException {
		String isin = "ISIN-PRD";
		PrdProductDto bestSellerDto = new PrdProductDto();
		bestSellerDto.setIsBestSeller(true);
		bestSellerDto.setIsin(isin);

		PrdProduct p = new PrdProduct();

		when(repoMock.findByIsin(isin)).thenReturn(p);
		ArgumentCaptor<PrdProduct> captor = ArgumentCaptor.forClass(PrdProduct.class);

		service.setBestSeller(bestSellerDto);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(captor.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct bestSeller = captor.getValue();

		Assert.assertThat(bestSeller.getIsBestSeller(), is(true));
	}

	@Test
	public void testSetBestSeller_DTOIsNotBestSeller() throws EavEntryNotFoundException {
		String isin = "ISIN-PRD";
		PrdProductDto bestSellerDto = new PrdProductDto();
		bestSellerDto.setIsBestSeller(false);
		bestSellerDto.setIsin(isin);

		PrdProduct p = new PrdProduct();

		when(repoMock.findByIsin(isin)).thenReturn(p);
		ArgumentCaptor<PrdProduct> captor = ArgumentCaptor.forClass(PrdProduct.class);

		service.setBestSeller(bestSellerDto);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(captor.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct bestSeller = captor.getValue();

		Assert.assertThat(bestSeller.getIsBestSeller(), is(true));
	}

	@Test
	public void testSetBestSeller_BestSellerNotFilled() throws EavEntryNotFoundException {
		String isin = "ISIN-PRD";
		PrdProductDto bestSellerDto = new PrdProductDto();
		bestSellerDto.setIsin(isin);

		PrdProduct p = new PrdProduct();

		when(repoMock.findByIsin(isin)).thenReturn(p);
		ArgumentCaptor<PrdProduct> captor = ArgumentCaptor.forClass(PrdProduct.class);

		service.setBestSeller(bestSellerDto);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(captor.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct bestSeller = captor.getValue();

		Assert.assertThat(bestSeller.getIsBestSeller(), is(true));
	}

	@Test(expected = EavEntryNotFoundException.class)
	public void testSetBestSeller_ProductNotFound() throws EavEntryNotFoundException {
		String isin = "ISIN-PRD";
		PrdProductDto bestSellerDto = new PrdProductDto();
		bestSellerDto.setIsBestSeller(true);
		bestSellerDto.setIsin(isin);

		when(repoMock.findByIsin(isin)).thenReturn(null);

		verifyNoMoreInteractions(repoMock);

		service.setBestSeller(bestSellerDto);
	}

	@Test
	public void testSetBestSeller_DTONull() throws EavEntryNotFoundException {
		String isin = "ISIN-PRD";
		PrdProductDto bestSellerDto = null;

		PrdProduct p = new PrdProduct();

		when(repoMock.findByIsin(isin)).thenReturn(p);

		PrdProduct bestSeller = service.setBestSeller(bestSellerDto);
		verifyNoMoreInteractions(repoMock);

		Assert.assertThat(bestSeller, is(nullValue()));
	}

	@Test
	public void testFindBestSeller_OneMatch() throws EavTechnicalException {
		List<PrdProduct> bestSellers = new ArrayList<>();
		PrdProduct bestSeller = new PrdProduct();
		bestSellers.add(bestSeller);

		when(repoMock.findByIsBestSeller(true)).thenReturn(bestSellers);

		PrdProduct result = service.findBestSeller();

		verify(repoMock, times(1)).findByIsBestSeller(true);
		verifyNoMoreInteractions(repoMock);

		Assert.assertThat(result, is(bestSeller));
	}

	@Test
	public void testFindBestSeller_MultipleMatch() throws EavTechnicalException {
		List<PrdProduct> bestSellers = new ArrayList<>();
		bestSellers.addAll(Arrays.asList(new PrdProduct(), new PrdProduct(), new PrdProduct()));

		when(repoMock.findByIsBestSeller(true)).thenReturn(bestSellers);

		try {
			service.findBestSeller();
			Assert.fail("Should have thrown an exception.");

		} catch (Exception e) {
			verify(repoMock, times(1)).findByIsBestSeller(true);
			verifyNoMoreInteractions(repoMock);

			Assert.assertThat(e, is(instanceOf(EavTechnicalException.class)));
			Assert.assertThat(((EavException) e).getCode(), is(EavErrorCode.MULTIPLE_BESTSELLER));
		}
	}

	@Test
	public void testFindBestSeller_NoMatch() throws EavTechnicalException {
		List<PrdProduct> bestSellers = new ArrayList<>();

		when(repoMock.findByIsBestSeller(true)).thenReturn(bestSellers);

		PrdProduct result = service.findBestSeller();

		verify(repoMock, times(1)).findByIsBestSeller(true);
		verifyNoMoreInteractions(repoMock);

		Assert.assertThat(result, is(nullValue()));
	}

	@Test(expected = EavTechnicalException.class)
	public void testFindBestSeller_NullListBestSellers() throws EavTechnicalException {
		List<PrdProduct> bestSellers = null;

		when(repoMock.findByIsBestSeller(true)).thenReturn(bestSellers);

		service.findBestSeller();
	}
}
