/**
 * 
 */
package com.synovia.digital.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdObservationDateRepository;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 26 févr. 2017
 */
public class PrdObservationDateServiceImplTest {

	private PrdObservationDateService service;

	private PrdProductService productServiceMock;
	private PrdObservationDateRepository repoMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		productServiceMock = mock(PrdProductService.class);
		repoMock = mock(PrdObservationDateRepository.class);

		service = new PrdObservationDateServiceImpl(repoMock, productServiceMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#add(com.synovia.digital.dto.PrdProductDateDto)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdd() throws Exception {
		Long id = 34L;
		String date = "2008-09-13";
		Long idPrdProduct = 765L;
		PrdProductDateDto dto = new PrdProductDateDto(id, date, idPrdProduct);

		PrdProduct product = new PrdProduct();
		product.setId(idPrdProduct);

		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);
		ArgumentCaptor<PrdObservationDate> captor = ArgumentCaptor.forClass(PrdObservationDate.class);

		service.add(dto);

		verify(repoMock, times(1)).save(captor.capture());
		verifyNoMoreInteractions(repoMock);

		PrdObservationDate added = captor.getValue();

		Assert.assertThat(added.getId(), is(id));
		Assert.assertThat(added.getDate(), is(DtoDateFormat.getFormat().parse(date)));
		Assert.assertThat(added.getPrdProduct(), is(product));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#add(com.synovia.digital.dto.PrdProductDateDto)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdd_InvalidDateFormat() throws Exception {
		Long id = 34L;
		String date = "2008/09/13";
		Long idPrdProduct = 765L;
		PrdProductDateDto dto = new PrdProductDateDto(id, date, idPrdProduct);

		PrdProduct product = new PrdProduct();
		product.setId(idPrdProduct);

		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);
		ArgumentCaptor<PrdObservationDate> captor = ArgumentCaptor.forClass(PrdObservationDate.class);

		service.add(dto);

		verify(repoMock, times(1)).save(captor.capture());
		verifyNoMoreInteractions(repoMock);

		PrdObservationDate added = captor.getValue();

		Assert.assertThat(added.getId(), is(id));
		Assert.assertThat(added.getDate(), is(nullValue()));
		Assert.assertThat(added.getPrdProduct(), is(product));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#delete(java.lang.Long)}.
	 */
	@Test
	public void testDelete() {
		Long id = 5L;
		PrdObservationDate date = new PrdObservationDate();
		date.setId(id);

		service.delete(id);

		verify(repoMock, times(1)).delete(id);
		verifyNoMoreInteractions(repoMock);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#findById(java.lang.Long)}.
	 */
	@Test
	public void testFindById() {
		Long id = 73L;
		PrdObservationDate obsDate = new PrdObservationDate();
		obsDate.setId(id);

		when(repoMock.findOne(id)).thenReturn(obsDate);

		PrdObservationDate found = service.findById(id);
		verify(repoMock, times(1)).findOne(id);
		verifyNoMoreInteractions(repoMock);

		Assert.assertThat(found, is(obsDate));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#update(java.lang.Long, com.synovia.digital.dto.PrdProductDateDto)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate_Date() throws Exception {
		String date = "2014-09-23";
		Long id = 678L;
		Date d = DtoDateFormat.getFormat().parse(date);

		PrdObservationDate obsDate = new PrdObservationDate();
		obsDate.setDate(d);
		obsDate.setId(id);
		PrdProduct prdProduct = new PrdProduct();
		Long idPrdProduct = 12345678L;
		prdProduct.setId(idPrdProduct);
		obsDate.setPrdProduct(prdProduct);

		PrdProductDateDto dto = new PrdProductDateDto();
		String updatedDate = "2009-04-06";
		Date newDate = DtoDateFormat.getFormat().parse(updatedDate);
		dto.setDate(updatedDate);

		when(repoMock.findOne(id)).thenReturn(obsDate);
		when(productServiceMock.findById(idPrdProduct)).thenReturn(prdProduct);

		service.update(id, dto);
		ArgumentCaptor<PrdObservationDate> captor = ArgumentCaptor.forClass(PrdObservationDate.class);

		verify(repoMock, times(1)).findOne(id);
		verify(repoMock, times(1)).save(captor.capture());

		PrdObservationDate updatedObsDate = captor.getValue();
		Assert.assertThat(updatedObsDate.getId(), is(id));
		Assert.assertThat(updatedObsDate.getDate(), is(newDate));
		Assert.assertThat(updatedObsDate.getPrdProduct(), is(prdProduct));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdObservationDateServiceImpl#update(java.lang.Long, com.synovia.digital.dto.PrdProductDateDto)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate_Product() throws Exception {
		String date = "2014-09-23";
		Long id = 678L;
		Date d = DtoDateFormat.getFormat().parse(date);

		PrdObservationDate obsDate = new PrdObservationDate();
		obsDate.setDate(d);
		obsDate.setId(id);
		PrdProduct prdProduct = new PrdProduct();
		Long idPrdProduct = 12345678L;
		prdProduct.setId(idPrdProduct);
		obsDate.setPrdProduct(prdProduct);

		PrdProductDateDto dto = new PrdProductDateDto();
		String updatedDate = "2009-04-06";
		Date newDate = DtoDateFormat.getFormat().parse(updatedDate);
		Long updatedIdProduct = 8L;
		PrdProduct updatedProduct = new PrdProduct();
		dto.setDate(updatedDate);
		dto.setIdPrdProduct(updatedIdProduct);

		when(repoMock.findOne(id)).thenReturn(obsDate);
		when(productServiceMock.findById(idPrdProduct)).thenReturn(prdProduct);
		when(productServiceMock.findById(updatedIdProduct)).thenReturn(updatedProduct);

		service.update(id, dto);
		ArgumentCaptor<PrdObservationDate> captor = ArgumentCaptor.forClass(PrdObservationDate.class);

		verify(repoMock, times(1)).findOne(id);
		verify(repoMock, times(1)).save(captor.capture());

		PrdObservationDate updatedObsDate = captor.getValue();
		Assert.assertThat(updatedObsDate.getId(), is(id));
		Assert.assertThat(updatedObsDate.getDate(), is(newDate));
		Assert.assertThat(updatedObsDate.getPrdProduct(), is(updatedProduct));
	}

}
