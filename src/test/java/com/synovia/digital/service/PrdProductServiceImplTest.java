/**
 * 
 */
package com.synovia.digital.service;

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
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdStatus;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repoMock = mock(PrdProductRepository.class);
		sousJacentRepoMock = mock(PrdSousJacentRepository.class);
		statusRepoMock = mock(PrdStatusRepository.class);
		service = new PrdProductServiceImpl(repoMock, sousJacentRepoMock, statusRepoMock);
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
		String code = "IDLE";
		PrdStatus idleStatus = new PrdStatus(1, code);
		when(statusRepoMock.findByCode(code)).thenReturn(idleStatus);

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
		Assert.assertThat(saveEntity.getPrdStatus(), is(idleStatus));
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
	@Test
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
	@Test
	public void testAdd_IncompleteEntity() throws Exception {
		Long id = 1L;
		String isin = "P123456TCF";

		PrdProductDto productDto = new PrdProductDto();
		productDto.setId(id);
		productDto.setIsin(isin);

		// Test the creation of a product.
		service.add(productDto);

		ArgumentCaptor<PrdProduct> productArgument = ArgumentCaptor.forClass(PrdProduct.class);

		verify(repoMock, times(1)).findByIsin(isin);
		verify(repoMock, times(1)).save(productArgument.capture());
		verifyNoMoreInteractions(repoMock);

		PrdProduct saveEntity = productArgument.getValue();
		Assert.assertThat(saveEntity, is(notNullValue()));

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
		String status = "STARTED";
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
		code = "IDLE";
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(1, code));
		code = "STARTED";
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(2, code));
		code = "SUBSCRIBABLE";
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(3, code));
		code = "STOPPED";
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(4, code));
		code = "CLOSED";
		when(statusRepoMock.findByCode(code)).thenReturn(new PrdStatus(5, code));

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

}
