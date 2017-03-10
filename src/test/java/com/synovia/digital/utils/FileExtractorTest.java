/**
 * 
 */
package com.synovia.digital.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.synovia.digital.TestUtil;
import com.synovia.digital.exceptions.EavTechnicalException;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 8 mars 2017
 */
public class FileExtractorTest {

	private static final String RESOURCES_DIR_NAME = "resources";
	private static final String TEST_DIR_NAME = "extractor";
	public static final String PRODUCT_SOURCE_DIR_NAME = "products";

	public static final String INPUT_SIMPLE_STRUCURE = "01_simple_structure";
	public static final String INPUT_MULTIPLE_FOLDER_STRUCURE = "02_structure_multiple_folders";
	public static final String INPUT_MULTIPLE_FOLDER_STRUCURE_EMPTY = "03_structure_multiple_folders_with_empty_ones";
	public static final String INPUT_EMBEDDED_FOLDER_STRUCURE = "04_structure_embedded_folders";
	public static final String INPUT_EMBEDDED_FOLDER_STRUCURE_EMPTY = "05_structure_embedded_folders_with_empty_ones";

	private static String testDir;
	private static String resDir;

	static {
		resDir = new StringBuilder(System.getProperty("user.dir")).append(File.separator).append("src")
				.append(File.separator).append("test").append(File.separator).append(RESOURCES_DIR_NAME).toString();
		System.out.print("Resources directory: ");
		System.out.println(resDir);

		testDir = new StringBuilder(resDir).append(File.separator).append(TEST_DIR_NAME).toString();
		System.out.print("Test directory: ");
		System.out.println(testDir);
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
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_SIMPLE_STRUCURE).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_NonExistentSource() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File("something//does//not//exist");

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertFalse(textDir.exists());
		Assert.assertFalse(jpegDir.exists());
		Assert.assertFalse(pdfDir.exists());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_NullSource() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = null;

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertFalse(textDir.exists());
		Assert.assertFalse(jpegDir.exists());
		Assert.assertFalse(pdfDir.exists());
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_NoMatchInSourceDir() throws EavTechnicalException {
		File exeDir = new File(new StringBuilder(testDir).append(File.separator).append("exe").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".exe", exeDir);

		File gifDir = new File(new StringBuilder(testDir).append(File.separator).append("gif").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".gif", gifDir);

		File docDir = new File(new StringBuilder(testDir).append(File.separator).append("doc").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".doc", docDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_SIMPLE_STRUCURE).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertFalse(exeDir.exists());
		Assert.assertFalse(gifDir.exists());
		Assert.assertFalse(docDir.exists());

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_ParamsContainOnlyNullValues() {
		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_SIMPLE_STRUCURE).toString());

		FileExtractor.Param p1 = null;
		FileExtractor.Param p2 = null;

		try {
			FileExtractor.copy(from, p1, p2);

		} catch (Exception e) {
			e.printStackTrace();
			fail(TestUtil.MSG_NO_EXCEPTION_EXPECTED);
		}

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_ParamsContainNullValues() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		FileExtractor.Param p4 = null;
		FileExtractor.Param p5 = null;

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_SIMPLE_STRUCURE).toString());

		FileExtractor.copy(from, p4, p1, p2, p5, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_NoParamsSet() throws EavTechnicalException {
		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_SIMPLE_STRUCURE).toString());

		try {
			FileExtractor.copy(from);
		} catch (Exception e) {
			fail(TestUtil.MSG_NO_EXCEPTION_EXPECTED);
		}

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_SourceWithMultipleFolders() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_MULTIPLE_FOLDER_STRUCURE).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_SourceWithSomeEmptyFolders() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_MULTIPLE_FOLDER_STRUCURE_EMPTY).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_SourceEmbeddedFolders() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_EMBEDDED_FOLDER_STRUCURE).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);

	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.FileExtractor#copy(java.io.File, com.synovia.digital.utils.FileExtractor.Param[])}.
	 * 
	 * @throws EavTechnicalException
	 */
	@Test
	public void testCopy_SourceEmbeddedEmptyFolders() throws EavTechnicalException {
		File textDir = new File(new StringBuilder(testDir).append(File.separator).append("text").toString());
		FileExtractor.Param p1 = new FileExtractor.Param(".txt", textDir);

		File jpegDir = new File(new StringBuilder(testDir).append(File.separator).append("jpeg").toString());
		FileExtractor.Param p2 = new FileExtractor.Param(".jpg", jpegDir);

		File pdfDir = new File(new StringBuilder(testDir).append(File.separator).append("pdf").toString());
		FileExtractor.Param p3 = new FileExtractor.Param(".pdf", pdfDir);

		File from = new File(new StringBuilder(resDir).append(File.separator).append(PRODUCT_SOURCE_DIR_NAME)
				.append(File.separator).append(INPUT_EMBEDDED_FOLDER_STRUCURE_EMPTY).toString());

		FileExtractor.copy(from, p1, p2, p3);

		Assert.assertTrue(textDir.exists());
		Assert.assertTrue(textDir.isDirectory());
		Assert.assertTrue(jpegDir.exists());
		Assert.assertTrue(jpegDir.isDirectory());
		Assert.assertTrue(pdfDir.exists());
		Assert.assertTrue(pdfDir.isDirectory());

		Assert.assertThat(textDir.listFiles().length, is(4));
		Assert.assertThat(jpegDir.listFiles().length, is(2));
		Assert.assertThat(pdfDir.listFiles().length, is(4));

		Assert.assertTrue(textDir.listFiles()[0].length() > 0);
		Assert.assertTrue(jpegDir.listFiles()[0].length() > 0);
		Assert.assertTrue(pdfDir.listFiles()[0].length() > 0);

	}

	@Test
	public void testJavaIoFileMkDirs() {
		File parent = new File(testDir);
		Assert.assertTrue(parent.mkdirs());

		Assert.assertFalse(parent.mkdirs());
	}

}
