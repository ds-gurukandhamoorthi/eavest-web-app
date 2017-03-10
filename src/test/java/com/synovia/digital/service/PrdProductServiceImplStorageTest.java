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
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.synovia.digital.filedataware.EavHomeDirectory;
import com.synovia.digital.model.PrdProduct;
import com.synovia.digital.repository.PrdProductRepository;
import com.synovia.digital.repository.PrdSousJacentRepository;
import com.synovia.digital.repository.PrdStatusRepository;
import com.synovia.digital.utils.FileExtractorTest;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 9 mars 2017
 */
public class PrdProductServiceImplStorageTest {
	private static final String RESOURCES_DIR_NAME = "resources";
	private static final String TEST_DIR_NAME = "product_storage";
	private static final String EMPTY_TEST_DIR_NAME = "empty_dir";

	private static String testDir;
	private static String resDir;

	protected PrdProductRepository repoMock;
	protected PrdSousJacentRepository sousJacentRepoMock;
	protected PrdStatusRepository statusRepoMock;
	protected EavHomeDirectory homeMock;
	protected PrdProductService service;

	static {
		resDir = new StringBuilder(System.getProperty("user.dir")).append(File.separator).append("src")
				.append(File.separator).append("test").append(File.separator).append(RESOURCES_DIR_NAME).toString();

		testDir = new StringBuilder(resDir).append(File.separator).append(TEST_DIR_NAME).toString();
	}

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
	 * @throws java.lang.Exception
	 */
	@After
	public void deleteOutputFiles() throws Exception {
		File extracted = new File(testDir);
		FileUtils.deleteDirectory(extracted);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#storeDocuments(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testStoreDocuments() {
		Long id = 5L;
		String isin = "TYJFV3456";
		String path = new StringBuilder(resDir).append(File.separator).append(FileExtractorTest.PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(FileExtractorTest.INPUT_SIMPLE_STRUCURE).toString();

		PrdProduct product = new PrdProduct();
		product.setId(id);
		product.setIsin(isin);
		product.setPath(path);

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);

		service.storeDocuments(product);

		verify(homeMock, times(1)).getImageDir(id);
		verifyNoMoreInteractions(homeMock);

		Assert.assertTrue(storeDir.exists());
		Assert.assertThat(storeDir.listFiles().length, is(2));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#storeDocuments(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testStoreDocuments_SourcePathNotSet() {
		Long id = 5L;
		String isin = "TYJFV3456";

		PrdProduct product = new PrdProduct();
		product.setId(id);
		product.setIsin(isin);
		product.setPath(null);

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);

		service.storeDocuments(product);

		verifyZeroInteractions(homeMock);

		Assert.assertFalse(storeDir.exists());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#storeDocuments(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testStoreDocuments_NullEntry() {
		PrdProduct product = null;

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());

		service.storeDocuments(product);

		verifyZeroInteractions(homeMock);

		Assert.assertFalse(storeDir.exists());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetImage() {
		Long id = 5L;
		String isin = "TYJFV3456";
		String path = new StringBuilder(resDir).append(File.separator).append(FileExtractorTest.PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(FileExtractorTest.INPUT_SIMPLE_STRUCURE).toString();

		PrdProduct product = new PrdProduct();
		product.setId(id);
		product.setIsin(isin);
		product.setPath(path);
		File imageLow = new File(new StringBuilder(product.getPath()).append(File.separator)
				.append("1-graphique-PROTECTION-125x179").append(".jpg").toString());
		System.out.println("Total size of the lower image: " + imageLow.length());
		File imageHigh = new File(new StringBuilder(product.getPath()).append(File.separator)
				.append("1-graphique-PROTECTION").append(".jpg").toString());
		System.out.println("Total size of the higher image: " + imageHigh.length());

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);

		service.storeDocuments(product);

		// The test starts here
		File result = service.getImage(product);

		// getImageDir is called twice: in storeDocument() and getImage()
		verify(homeMock, times(2)).getImageDir(id);
		verifyNoMoreInteractions(homeMock);

		Assert.assertNotNull(result);
		Assert.assertThat(result.length(), is(imageHigh.length()));
		Assert.assertTrue(result.length() >= imageLow.length());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetImage_NullEntry() {
		PrdProduct product = null;

		File result = service.getImage(product);

		Assert.assertThat(result, is(nullValue()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetImage_NoImageExist() {
		Long id = 5L;
		String isin = "TYJFV3456";

		PrdProduct product = new PrdProduct();
		product.setId(id);
		product.setIsin(isin);

		String emptyTestDir = new StringBuilder(resDir).append(File.separator).append(EMPTY_TEST_DIR_NAME).toString();
		File storeDir = new File(emptyTestDir);
		when(homeMock.getImageDir(id)).thenReturn(storeDir);

		// The test starts here
		File result = service.getImage(product);

		verify(homeMock, times(1)).getImageDir(id);
		verifyNoMoreInteractions(homeMock);

		Assert.assertThat(result, is(nullValue()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getBestSellerImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetBestSellerImage() {
		Long id = 9876L;
		String isin = "ERTYUJHVD34567";
		String path = new StringBuilder(resDir).append(File.separator).append(FileExtractorTest.PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(FileExtractorTest.INPUT_SIMPLE_STRUCURE).toString();

		List<PrdProduct> bestsellers = new ArrayList<>();
		PrdProduct bestSeller = new PrdProduct();
		bestSeller.setId(id);
		bestSeller.setIsin(isin);
		bestSeller.setPath(path);
		bestsellers.add(bestSeller);

		File imageLow = new File(new StringBuilder(path).append(File.separator).append("1-graphique-PROTECTION-125x179")
				.append(".jpg").toString());
		System.out.println("Total size of the lower image: " + imageLow.length());
		File imageHigh = new File(new StringBuilder(path).append(File.separator).append("1-graphique-PROTECTION")
				.append(".jpg").toString());
		System.out.println("Total size of the higher image: " + imageHigh.length());

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);
		when(repoMock.findByIsBestSeller(true)).thenReturn(bestsellers);
		// Store the documents
		service.storeDocuments(bestSeller);

		// Start the test
		File result = service.getBestSellerImage();

		verify(homeMock, times(2)).getImageDir(id);
		verify(repoMock, times(1)).findByIsBestSeller(true);
		verifyNoMoreInteractions(homeMock, repoMock);

		Assert.assertNotNull(result);
		Assert.assertThat(result.length(), is(imageHigh.length()));
		Assert.assertTrue(result.length() >= imageLow.length());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getBestSellerImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetBestSellerImage_NoBestSeller() {
		Long id = 9876L;
		String isin = "ERTYUJHVD34567";
		String path = new StringBuilder(resDir).append(File.separator).append(FileExtractorTest.PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(FileExtractorTest.INPUT_SIMPLE_STRUCURE).toString();

		List<PrdProduct> bestsellers = new ArrayList<>();
		PrdProduct bestSeller = new PrdProduct();
		bestSeller.setId(id);
		bestSeller.setIsin(isin);
		bestSeller.setPath(path);

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);
		when(repoMock.findByIsBestSeller(true)).thenReturn(bestsellers);
		// Store the documents
		service.storeDocuments(bestSeller);

		// Start the test
		File result = service.getBestSellerImage();

		verify(homeMock, times(1)).getImageDir(id);
		verify(repoMock, times(1)).findByIsBestSeller(true);
		verifyNoMoreInteractions(repoMock, homeMock);

		Assert.assertThat(result, is(nullValue()));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.service.PrdProductServiceImpl#getBestSellerImage(com.synovia.digital.model.PrdProduct)}.
	 */
	@Test
	public void testGetBestSellerImage_NullListBestSellers() {
		Long id = 9876L;
		String isin = "ERTYUJHVD34567";
		String path = new StringBuilder(resDir).append(File.separator).append(FileExtractorTest.PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(FileExtractorTest.INPUT_SIMPLE_STRUCURE).toString();

		List<PrdProduct> bestsellers = null;
		PrdProduct bestSeller = new PrdProduct();
		bestSeller.setId(id);
		bestSeller.setIsin(isin);
		bestSeller.setPath(path);

		File storeDir = new File(new StringBuilder(testDir).append(File.separator).append("img").toString());
		when(homeMock.getImageDir(id)).thenReturn(storeDir);
		when(repoMock.findByIsBestSeller(true)).thenReturn(bestsellers);
		// Store the documents
		service.storeDocuments(bestSeller);

		// Start the test
		File result = service.getBestSellerImage();

		verify(homeMock, times(1)).getImageDir(id);
		verify(repoMock, times(1)).findByIsBestSeller(true);
		verifyNoMoreInteractions(repoMock, homeMock);

		Assert.assertThat(result, is(nullValue()));
	}
}
