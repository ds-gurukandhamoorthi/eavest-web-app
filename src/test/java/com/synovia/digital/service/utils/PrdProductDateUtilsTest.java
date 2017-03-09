/**
 * 
 */
package com.synovia.digital.service.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.synovia.digital.dto.PrdProductDateDto;
import com.synovia.digital.dto.utils.DtoDateFormat;
import com.synovia.digital.exceptions.EavEntryNotFoundException;
import com.synovia.digital.filedataware.EavHomeDirectory;
import com.synovia.digital.model.AbstractPrdProductDate;
import com.synovia.digital.model.PrdCouponDate;
import com.synovia.digital.model.PrdEarlierRepaymentDate;
import com.synovia.digital.model.PrdObservationDate;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.service.PrdProductService;
import com.synovia.digital.service.PrdProductServiceImpl;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 28 févr. 2017
 */
public class PrdProductDateUtilsTest {

	protected PrdProductService productServiceMock;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		productServiceMock = mock(PrdProductService.class);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_PrdObservationDate() throws EavEntryNotFoundException, ParseException {
		Long id = 78L;
		String date = "2001-06-21";
		Long idPrdProduct = 4L;
		PrdProductDateDto toConvertDto = new PrdProductDateDto();
		toConvertDto.setDate(date);
		toConvertDto.setId(id);
		toConvertDto.setIdPrdProduct(idPrdProduct);

		PrdProduct product = new PrdProduct();
		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);

		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(new PrdObservationDate(), toConvertDto, productServiceMock);

		Assert.assertTrue(converted instanceof PrdObservationDate);
		Assert.assertThat(converted.getId(), is(id));
		Assert.assertThat(converted.getPrdProduct(), is(product));
		Assert.assertThat(converted.getDate(), is(DtoDateFormat.getFormat().parse(date)));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_PrdEarlierRepaymentDate() throws EavEntryNotFoundException, ParseException {
		Long id = 78L;
		String date = "2001-06-21";
		Long idPrdProduct = 4L;
		PrdProductDateDto toConvertDto = new PrdProductDateDto();
		toConvertDto.setDate(date);
		toConvertDto.setId(id);
		toConvertDto.setIdPrdProduct(idPrdProduct);

		PrdProduct product = new PrdProduct();
		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);

		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(new PrdEarlierRepaymentDate(), toConvertDto,
				productServiceMock);

		Assert.assertTrue(converted instanceof PrdEarlierRepaymentDate);
		Assert.assertThat(converted.getId(), is(id));
		Assert.assertThat(converted.getPrdProduct(), is(product));
		Assert.assertThat(converted.getDate(), is(DtoDateFormat.getFormat().parse(date)));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_PrdCouponDate() throws EavEntryNotFoundException, ParseException {
		Long id = 78L;
		String date = "2001-06-21";
		Long idPrdProduct = 4L;
		PrdProductDateDto toConvertDto = new PrdProductDateDto();
		toConvertDto.setDate(date);
		toConvertDto.setId(id);
		toConvertDto.setIdPrdProduct(idPrdProduct);

		PrdProduct product = new PrdProduct();
		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);

		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(new PrdCouponDate(), toConvertDto, productServiceMock);

		Assert.assertTrue(converted instanceof PrdCouponDate);
		Assert.assertThat(converted.getId(), is(id));
		Assert.assertThat(converted.getPrdProduct(), is(product));
		Assert.assertThat(converted.getDate(), is(DtoDateFormat.getFormat().parse(date)));

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_NullEntity() throws EavEntryNotFoundException, ParseException {
		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(null, new PrdProductDateDto(), productServiceMock);

		Assert.assertNull(converted);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_NullDto() throws EavEntryNotFoundException, ParseException {
		PrdProductDateDto toConvertDto = null;

		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(new PrdObservationDate(), toConvertDto, productServiceMock);

		Assert.assertNull(converted);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test(expected = EavEntryNotFoundException.class)
	public void testConvertToEntity_PrdProductNotFound() throws EavEntryNotFoundException, ParseException {
		Long id = 78L;
		String date = "2001-06-21";
		Long idPrdProduct = 4L;
		PrdProductDateDto toConvertDto = new PrdProductDateDto();
		toConvertDto.setDate(date);
		toConvertDto.setId(id);
		toConvertDto.setIdPrdProduct(idPrdProduct);

		PrdProductRepository repoMock = mock(PrdProductRepository.class);
		PrdProductServiceImpl service = new PrdProductServiceImpl(repoMock, mock(PrdSousJacentRepository.class),
				mock(PrdStatusRepository.class), mock(EavHomeDirectory.class));

		when(repoMock.findOne(idPrdProduct)).thenReturn(null);
		PrdProductDateUtils.convertToEntity(new PrdEarlierRepaymentDate(), toConvertDto, service);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.utils.PrdProductDateUtils#convertToEntity(AbstractPrdProductDate, com.synovia.digital.dto.PrdProductDateDto, com.synovia.digital.service.PrdProductService)}.
	 * 
	 * @throws EavEntryNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testConvertToEntity_InvalidDateFormat() throws EavEntryNotFoundException, ParseException {
		Long id = 78L;
		String date = "2001/06/21";
		Long idPrdProduct = 4L;
		PrdProductDateDto toConvertDto = new PrdProductDateDto();
		toConvertDto.setDate(date);
		toConvertDto.setId(id);
		toConvertDto.setIdPrdProduct(idPrdProduct);

		PrdProduct product = new PrdProduct();
		when(productServiceMock.findById(idPrdProduct)).thenReturn(product);

		AbstractPrdProductDate converted = null;
		converted = PrdProductDateUtils.convertToEntity(new PrdObservationDate(), toConvertDto, productServiceMock);

		Assert.assertTrue(converted instanceof PrdObservationDate);
		Assert.assertThat(converted.getId(), is(id));
		Assert.assertThat(converted.getPrdProduct(), is(product));
		Assert.assertThat(converted.getDate(), is(nullValue()));

	}

	@Test
	public void testUpdateFromDto_PrdObservationDate() throws ParseException, EavEntryNotFoundException {
		Long id = 56L;
		String dateAsString = "2022-12-22";
		Date date = DtoDateFormat.getFormat().parse(dateAsString);
		Long idPrdProduct = 6L;
		PrdProduct prdProduct = new PrdProduct();

		AbstractPrdProductDate toUpdate = new AbstractPrdProductDate() {
		};
		toUpdate.setId(id);
		toUpdate.setDate(date);
		toUpdate.setPrdProduct(prdProduct);

		Long updatedId = 34567L;
		String updatedDateAsString = "2009-12-28";
		Date updatedDate = DtoDateFormat.getFormat().parse(updatedDateAsString);
		Long updatedIdPrdProduct = 23L;
		PrdProduct updatedPrdProduct = new PrdProduct();

		PrdProductDateDto dto = new PrdProductDateDto(updatedId, updatedDateAsString, updatedIdPrdProduct);

		PrdProductRepository repoMock = mock(PrdProductRepository.class);
		PrdProductServiceImpl service = new PrdProductServiceImpl(repoMock, mock(PrdSousJacentRepository.class),
				mock(PrdStatusRepository.class), mock(EavHomeDirectory.class));

		when(repoMock.findOne(idPrdProduct)).thenReturn(prdProduct);
		when(repoMock.findOne(updatedIdPrdProduct)).thenReturn(updatedPrdProduct);

		PrdProductDateUtils.updateFromDto(toUpdate, dto, service);

		Assert.assertThat(toUpdate.getId(), is(id));
		Assert.assertThat(toUpdate.getDate(), is(updatedDate));
		Assert.assertThat(toUpdate.getPrdProduct(), is(updatedPrdProduct));
	}

	@Test
	public void testUpdateFromDto_NullDto() throws ParseException, EavEntryNotFoundException {
		Long id = 56L;
		String dateAsString = "2022-12-22";
		Date date = DtoDateFormat.getFormat().parse(dateAsString);
		Long idPrdProduct = 6L;
		PrdProduct prdProduct = new PrdProduct();

		AbstractPrdProductDate toUpdate = new AbstractPrdProductDate() {
		};
		toUpdate.setId(id);
		toUpdate.setDate(date);
		toUpdate.setPrdProduct(prdProduct);

		PrdProductDateDto dto = null;

		PrdProductRepository repoMock = mock(PrdProductRepository.class);
		PrdProductServiceImpl service = new PrdProductServiceImpl(repoMock, mock(PrdSousJacentRepository.class),
				mock(PrdStatusRepository.class), mock(EavHomeDirectory.class));

		when(repoMock.findOne(idPrdProduct)).thenReturn(prdProduct);

		PrdProductDateUtils.updateFromDto(toUpdate, dto, service);

		Assert.assertThat(toUpdate.getId(), is(id));
		Assert.assertThat(toUpdate.getDate(), is(date));
		Assert.assertThat(toUpdate.getPrdProduct(), is(prdProduct));
	}

	@Test
	public void testUpdateFromDto_NullEntity() throws ParseException, EavEntryNotFoundException {
		AbstractPrdProductDate toUpdate = null;

		PrdProductDateDto dto = new PrdProductDateDto();

		PrdProductDateUtils.updateFromDto(toUpdate, dto, productServiceMock);

		Assert.assertThat(toUpdate, is(nullValue()));
	}

	@Test(expected = EavEntryNotFoundException.class)
	public void testUpdateFromDto_PrdProductNotFound() throws ParseException, EavEntryNotFoundException {
		Long id = 56L;
		String dateAsString = "2022-12-22";
		Date date = DtoDateFormat.getFormat().parse(dateAsString);
		Long idPrdProduct = 6L;
		PrdProduct prdProduct = new PrdProduct();

		AbstractPrdProductDate toUpdate = new AbstractPrdProductDate() {
		};
		toUpdate.setId(id);
		toUpdate.setDate(date);
		toUpdate.setPrdProduct(prdProduct);

		Long updatedId = 34567L;
		String updatedDateAsString = "2009-12-28";
		Long updatedIdPrdProduct = 23L;

		PrdProductDateDto dto = new PrdProductDateDto(updatedId, updatedDateAsString, updatedIdPrdProduct);

		PrdProductRepository repoMock = mock(PrdProductRepository.class);
		PrdProductServiceImpl service = new PrdProductServiceImpl(repoMock, mock(PrdSousJacentRepository.class),
				mock(PrdStatusRepository.class), mock(EavHomeDirectory.class));

		when(repoMock.findOne(idPrdProduct)).thenReturn(prdProduct);
		when(repoMock.findOne(updatedIdPrdProduct)).thenReturn(null);

		PrdProductDateUtils.updateFromDto(toUpdate, dto, service);

	}

}
