/**
 * 
 */
package com.synovia.digital.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.synovia.digital.dto.PrdProductDto;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.model.PrdSousJacent;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 17 févr. 2017
 */
public class PrdProductServiceImplTest {

	protected PrdProductRepository repoMock;
	protected PrdProductService service;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		repoMock = mock(PrdProductRepository.class);
		service = new PrdProductServiceImpl(repoMock);
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
	public void testAdd() {
		try {
			Long idSsJct = 5L;
			Long id = 1L;
			String isin = "P123456TCF";
			String label = "EUROSTOXX TEST";
			Date dueDate;
			dueDate = format.parse("20/04/2025");
			Date launchDate = format.parse("04/04/2016");
			PrdSousJacent sousJacent = new PrdSousJacent("SS-JCT");
			sousJacent.setId(idSsJct);

			PrdProductDto productDto = new PrdProductDto();
			productDto.setId(id);
			productDto.setIsin(isin);
			productDto.setLabel(label);
			productDto.setDueDate(dueDate);
			productDto.setLaunchDate(launchDate);
			productDto.setPrdSousJacent(sousJacent);

			// Mock the behavior of the underlying asset repository.
			PrdSousJacentRepository ssjctMock = mock(PrdSousJacentRepository.class);
			when(ssjctMock.findOne(idSsJct)).thenReturn(sousJacent);

			// Test the creation of a product.
			service.add(productDto);

			ArgumentCaptor<PrdProduct> productArgument = ArgumentCaptor.forClass(PrdProduct.class);

			verify(repoMock, times(1)).save(productArgument.capture());
			verifyNoMoreInteractions(repoMock);

			PrdProduct saveEntity = productArgument.getValue();

			Assert.assertNull(saveEntity.getId());
			Assert.assertEquals(saveEntity.getIsin(), isin);
			Assert.assertEquals(saveEntity.getLabel(), label);
			Assert.assertEquals(saveEntity.getDueDate(), dueDate);
			Assert.assertEquals(saveEntity.getLaunchDate(), launchDate);
			Assert.assertThat(saveEntity.getPrdSousJacent(), is(sousJacent));

		} catch (ParseException e) {
			e.printStackTrace();
			fail("Should have not thrown an exception");
		}
	}

}
