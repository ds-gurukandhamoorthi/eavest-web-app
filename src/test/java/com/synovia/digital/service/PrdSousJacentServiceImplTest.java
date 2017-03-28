/**
 * 
 */
package com.synovia.digital.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.util.Assert;

import com.synovia.digital.dto.PrdSousJacentValueDto;
import com.synovia.digital.dto.PrdSousjacentDto;
import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.exceptions.EavException;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.model.PrdSousJacentValue;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdSousJacentValueRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 15 févr. 2017
 */
public class PrdSousJacentServiceImplTest {

	private PrdSousJacentServiceImpl sousJacentService;

	private PrdSousJacentRepository repositoryMock;
	private PrdSousJacentValueRepository sjvRepoMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repositoryMock = mock(PrdSousJacentRepository.class);
		sjvRepoMock = mock(PrdSousJacentValueRepository.class);

		PrdSousJacentValueServiceImpl sousJacentValueService = new PrdSousJacentValueServiceImpl(sjvRepoMock,
				repositoryMock);
		sousJacentService = new PrdSousJacentServiceImpl(repositoryMock, sousJacentValueService);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#add(com.synovia.digital.dto.PrdSousjacentDto)}.
	 */
	@Test
	public void testAdd() {
		PrdSousjacentDto sousJacentDto = new PrdSousjacentDto();
		sousJacentDto.setLabel("SS-JCT-TEST");
		sousJacentDto.setBloombergCode("BLOOMCODE");
		sousJacentDto.setIsinCode("IS4567IS");

		try {
			sousJacentService.add(sousJacentDto);
		} catch (EavException e) {
			fail("Should not have thrown an exception.");
		}

		ArgumentCaptor<PrdSousJacent> prdSousJacentArgument = ArgumentCaptor.forClass(PrdSousJacent.class);
		verify(repositoryMock, times(1)).findByLabelOrBloombergCode(sousJacentDto.getLabel(),
				sousJacentDto.getBloombergCode());
		verify(repositoryMock, times(1)).save(prdSousJacentArgument.capture());
		verifyNoMoreInteractions(repositoryMock);

		PrdSousJacent model = prdSousJacentArgument.getValue();

		Assert.isNull(model.getId());
		assertEquals(model.getLabel(), sousJacentDto.getLabel());
		assertEquals(model.getBloombergCode(), sousJacentDto.getBloombergCode());
		assertEquals(model.getIsinCode(), sousJacentDto.getIsinCode());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#add(com.synovia.digital.dto.PrdSousjacentDto)}.
	 */
	@Test
	public void testAdd_InvalidEntry() {
		PrdSousjacentDto sousJacentDto = new PrdSousjacentDto();

		try {
			ArgumentCaptor<PrdSousJacent> captor = ArgumentCaptor.forClass(PrdSousJacent.class);

			sousJacentService.add(sousJacentDto);

			verify(repositoryMock, times(1)).findByLabelOrBloombergCode(null, null);
			verify(repositoryMock, times(1)).save(captor.capture());
			verifyNoMoreInteractions(repositoryMock);

			PrdSousJacent result = captor.getValue();
			assertNotNull(result);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have throw an exception");

		}

		try {
			PrdSousJacent result = sousJacentService.add(null);
			verifyZeroInteractions(repositoryMock);
			assertNull(result);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have throw an exception");

		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#addValue(java.lang.Long, java.util.Date, java.lang.Double)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testAddValueLongDateDouble() throws ParseException {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(sousJacent);

		// Test the add of time-value
		String dateAsString = "2017-02-24";
		Date date = DtoDateFormat.getFormat().parse(dateAsString);
		Double value = new Double(4123.45);

		try {
			PrdSousJacent updated = sousJacentService.addValue(sousJacent.getId(), dateAsString, value);

			ArgumentCaptor<PrdSousJacentValue> prdSousJacentValueArgument = ArgumentCaptor
					.forClass(PrdSousJacentValue.class);
			verify(sjvRepoMock, times(1)).save(prdSousJacentValueArgument.capture());
			verifyNoMoreInteractions(sjvRepoMock);
			PrdSousJacentValue values = prdSousJacentValueArgument.getValue();

			verify(repositoryMock, times(1)).findOne(idSousJacent);
			//			verifyNoMoreInteractions(repositoryMock);

			assertEquals(idSousJacent, updated.getId());
			Assert.notEmpty(updated.getPrdSousJacentValues());
			assertEquals(date, values.getDate());
			assertEquals(value, values.getValue());

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#addValue(java.lang.Long, java.util.Date, java.lang.Double)}.
	 * 
	 * @throws ParseException
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testAddValue_ShouldThrowException() throws EavEntryNotFoundException, ParseException {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(null);

		// Test the add of time-value
		String dateAsString = "2017-02-24";
		Double value = new Double(4123.45);

		sousJacentService.addValue(sousJacent.getId(), dateAsString, value);

		verify(repositoryMock, times(1)).findOne(idSousJacent);
		verifyNoMoreInteractions(repositoryMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#addValues(java.lang.Long, java.util.Map)}.
	 */
	@Test
	public void testAddValues() {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(sousJacent);

		// Test the add of time-values
		Map<String, Double> valuesToAdd = new HashMap<>();
		Date date1 = new Date(10L);
		Double value1 = new Double(4123.45);
		Date date2 = new Date();
		Double value2 = new Double(723.45);
		valuesToAdd.put(DtoDateFormat.getFormat().format(date1), value1);
		valuesToAdd.put(DtoDateFormat.getFormat().format(date2), value2);

		try {
			PrdSousJacent updated = sousJacentService.addValues(sousJacent.getId(), valuesToAdd);

			ArgumentCaptor<PrdSousJacentValue> prdSousJacentValueArgument = ArgumentCaptor
					.forClass(PrdSousJacentValue.class);
			verify(sjvRepoMock, times(2)).save(prdSousJacentValueArgument.capture());

			verify(repositoryMock, times(2)).findOne(idSousJacent);
			//			verifyNoMoreInteractions(repositoryMock);

			assertEquals(idSousJacent, updated.getId());
			Assert.notEmpty(updated.getPrdSousJacentValues());
			int expectedSize = 2;
			assertEquals(expectedSize, updated.getPrdSousJacentValues().size());

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	@Test
	public void testAddValues_NoValuesToAdd() throws EavException {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(null);

		Map<String, Double> valuesToAdd = new HashMap<>();

		sousJacentService.addValues(sousJacent.getId(), valuesToAdd);

		verifyZeroInteractions(repositoryMock);
	}

	@Test(expected = EavEntryNotFoundException.class)
	public void testAddValues_ShouldThrowException() throws EavException {
		Long idSousJacent = 5L;

		when(repositoryMock.findOne(idSousJacent)).thenReturn(null);

		Map<String, Double> valuesToAdd = new HashMap<>();
		valuesToAdd.put("2012-06-25", 0d);

		sousJacentService.addValues(idSousJacent, valuesToAdd);

		verify(repositoryMock, times(1)).findOne(idSousJacent);
		verifyZeroInteractions(repositoryMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#removeValue(java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public void testRemoveValue() {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(sousJacent);

		// Test the remove of an underlying asset value
		PrdSousJacentValue psjValue = new PrdSousJacentValue();
		Long idPSJValue = 2L;
		psjValue.setId(idPSJValue);
		psjValue.setDate(new Date());
		psjValue.setValue(new Double(134.87));

		when(sjvRepoMock.findOne(idPSJValue)).thenReturn(psjValue);

		try {
			PrdSousJacent updated = sousJacentService.removeValue(sousJacent.getId(), idPSJValue);

			verify(sjvRepoMock, times(1)).delete(idPSJValue);

			verify(repositoryMock, times(1)).findOne(idSousJacent);
			verifyNoMoreInteractions(repositoryMock);

			assertEquals(idSousJacent, updated.getId());

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#removeValue(java.lang.Long, java.lang.Long)}.
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testRemoveValue_ShouldThrowException() throws EavEntryNotFoundException {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(null);

		// Test the remove of an underlying asset value
		PrdSousJacentValue psjValue = new PrdSousJacentValue();
		Long idPSJValue = 2L;
		psjValue.setId(idPSJValue);
		psjValue.setDate(new Date());
		psjValue.setValue(new Double(134.87));

		when(sjvRepoMock.findOne(idPSJValue)).thenReturn(psjValue);

		sousJacentService.removeValue(sousJacent.getId(), idPSJValue);

		verify(repositoryMock, times(1)).findOne(idSousJacent);
		verifyNoMoreInteractions(repositoryMock);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#removeValues(java.lang.Long, java.util.List)}.
	 */
	@Test
	public void testRemoveValues() {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(sousJacent);

		// Test the remove of an underlying asset value
		PrdSousJacentValue psjValue = new PrdSousJacentValue();
		Long idPSJValue = 2L;
		psjValue.setId(idPSJValue);
		psjValue.setDate(new Date());
		psjValue.setValue(new Double(134.87));

		when(sjvRepoMock.findOne(idPSJValue)).thenReturn(psjValue);

		PrdSousJacentValue psjValue2 = new PrdSousJacentValue();
		Long idPSJValue2 = 3L;
		psjValue2.setId(idPSJValue2);
		psjValue2.setDate(new Date(123456L));
		psjValue2.setValue(new Double(9876.34));

		when(sjvRepoMock.findOne(idPSJValue2)).thenReturn(psjValue2);

		List<Long> idValuesToRemove = new ArrayList<>();
		idValuesToRemove.add(idPSJValue);
		idValuesToRemove.add(idPSJValue2);

		try {
			PrdSousJacent updated = sousJacentService.removeValues(sousJacent.getId(), idValuesToRemove);

			verify(sjvRepoMock, times(1)).delete(idPSJValue);
			verify(sjvRepoMock, times(1)).delete(idPSJValue2);

			verify(repositoryMock, times(1)).findOne(idSousJacent);
			verifyNoMoreInteractions(repositoryMock);

			assertEquals(idSousJacent, updated.getId());

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#removeValues(java.lang.Long, java.util.List)}.
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testRemoveValues_ShouldThrowException() throws EavEntryNotFoundException {
		Long idSousJacent = 5L;
		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel("SS-JCT-TEST");
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(null);

		// Test the remove of an underlying asset value
		PrdSousJacentValue psjValue = new PrdSousJacentValue();
		Long idPSJValue = 2L;
		psjValue.setId(idPSJValue);
		psjValue.setDate(new Date());
		psjValue.setValue(new Double(134.87));

		when(sjvRepoMock.findOne(idPSJValue)).thenReturn(psjValue);

		PrdSousJacentValue psjValue2 = new PrdSousJacentValue();
		Long idPSJValue2 = 3L;
		psjValue2.setId(idPSJValue2);
		psjValue2.setDate(new Date(123456L));
		psjValue2.setValue(new Double(9876.34));

		when(sjvRepoMock.findOne(idPSJValue2)).thenReturn(psjValue2);

		List<Long> idValuesToRemove = new ArrayList<>();
		idValuesToRemove.add(idPSJValue);
		idValuesToRemove.add(idPSJValue2);

		sousJacentService.removeValues(sousJacent.getId(), idValuesToRemove);

		verify(repositoryMock, times(1)).findOne(idSousJacent);
		verifyNoMoreInteractions(repositoryMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#update(com.synovia.digital.dto.PrdSousjacentDto)}.
	 */
	@Test
	public void testUpdate() {
		Long idSousJacent = 5L;
		String label = "SS-JCT-TEST";
		String labelUpdated = "SS-JCT-TEST_UPDATE";

		PrdSousjacentDto sousJacentDto = new PrdSousjacentDto();
		sousJacentDto.setId(idSousJacent);
		sousJacentDto.setLabel(labelUpdated);

		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel(label);
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(sousJacentDto.getId())).thenReturn(sousJacent);

		// Test update
		try {
			sousJacentService.update(sousJacentDto);

			verify(repositoryMock, times(1)).findOne(sousJacentDto.getId());
			verifyNoMoreInteractions(repositoryMock);

			org.junit.Assert.assertThat(sousJacent.getId(), is(sousJacentDto.getId()));
			org.junit.Assert.assertThat(sousJacent.getLabel(), is(sousJacentDto.getLabel()));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#update(com.synovia.digital.dto.PrdSousjacentDto)}.
	 */
	@Test(expected = EavException.class)
	public void testUpdate_ShouldThrowException() throws EavException {
		Long idSousJacent = 5L;

		PrdSousjacentDto sousJacentDto = new PrdSousjacentDto();
		sousJacentDto.setId(idSousJacent);

		when(repositoryMock.findOne(sousJacentDto.getId())).thenReturn(null);

		// Test update
		sousJacentService.update(sousJacentDto);

		verify(repositoryMock, times(1)).findOne(sousJacentDto.getId());
		verifyNoMoreInteractions(repositoryMock);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#findAll()}.
	 */
	@Test
	public void testFindAll() {
		List<PrdSousJacent> sousJacents = new ArrayList<>();
		when(repositoryMock.findAll()).thenReturn(sousJacents);

		List<PrdSousJacent> actual = sousJacentService.findAll();

		verify(repositoryMock, times(1)).findAll();
		verifyNoMoreInteractions(repositoryMock);

		org.junit.Assert.assertThat(actual, is(sousJacents));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	public void testFindById() {
		Long id = 1L;
		PrdSousJacent model = new PrdSousJacent();
		model.setId(id);
		model.setLabel("SS-JCT-TEST");

		when(repositoryMock.findOne(id)).thenReturn(model);

		try {
			PrdSousJacent actual = sousJacentService.findById(id);

			verify(repositoryMock, times(1)).findOne(id);
			verifyNoMoreInteractions(repositoryMock);

			org.junit.Assert.assertThat(actual, is(model));

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#findById(java.lang.Long)}.
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testFindById_ShouldThrowException() throws Exception {
		Long id = 1L;

		when(repositoryMock.findOne(id)).thenReturn(null);

		sousJacentService.findById(id);

		verify(repositoryMock, times(1)).findOne(id);
		verifyNoMoreInteractions(repositoryMock);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdSousJacentServiceImpl#addValue(com.synovia.digital.dto.PrdSousJacentValueDto)}.
	 */
	@Test
	public void testAddValuePrdSousJacentValueDto() {
		Long idSousJacent = 5L;
		String label = "SS-JCT-TEST";
		PrdSousJacentValueDto sousjacentValueDto = new PrdSousJacentValueDto();
		String date = "2016-12-04";
		Double value = new Double(4123.45);
		sousjacentValueDto.setDate(date);
		sousjacentValueDto.setValue(value);
		sousjacentValueDto.setIdPrdSousJacent(idSousJacent);

		PrdSousJacent sousJacent = new PrdSousJacent();
		sousJacent.setLabel(label);
		sousJacent.setId(idSousJacent);

		when(repositoryMock.findOne(idSousJacent)).thenReturn(sousJacent);

		// Test the add of time-value
		try {
			PrdSousJacent updated = sousJacentService.addValue(idSousJacent, sousjacentValueDto);

			ArgumentCaptor<PrdSousJacentValue> prdSousJacentValueArgument = ArgumentCaptor
					.forClass(PrdSousJacentValue.class);
			verify(sjvRepoMock, times(1)).save(prdSousJacentValueArgument.capture());
			PrdSousJacentValue values = prdSousJacentValueArgument.getValue();

			verify(repositoryMock, times(1)).findOne(idSousJacent);
			//			verifyNoMoreInteractions(repositoryMock);

			assertEquals(idSousJacent, updated.getId());
			Assert.notEmpty(updated.getPrdSousJacentValues());
			assertEquals(DtoDateFormat.getFormat().parse(date), values.getDate());
			assertEquals(value, values.getValue());

		} catch (Exception e) {
			fail("Should not have thrown an exception");
		}
	}

	//	@Test
	public void testCurrentMonthValue() throws Exception {
		PrdSousJacent ssjct = new PrdSousJacent();
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Double expectedValue = 4980.54;
		PrdSousJacentValue value1 = new PrdSousJacentValue(ssjct, formatter.parse("28/02/2017"), expectedValue);
		PrdSousJacentValue value2 = new PrdSousJacentValue(ssjct, formatter.parse("15/02/2017"), 4923.09);
		PrdSousJacentValue value3 = new PrdSousJacentValue(ssjct, formatter.parse("28/01/2017"), 4900.59);
		PrdSousJacentValue value4 = new PrdSousJacentValue(ssjct, formatter.parse("31/01/2017"), 4965.29);

		sousJacentService.currentMonthValue(ssjct);
	}

}
