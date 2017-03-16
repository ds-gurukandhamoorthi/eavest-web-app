/**
 * 
 */
package com.synovia.digital.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import java.io.File;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 16 mars 2017
 */
public class EavConstantsTest {

	private final String resDir = new StringBuilder(System.getProperty("user.dir")).append(EavConstants.FILE_SEPARATOR)
			.append("src").append(EavConstants.FILE_SEPARATOR).append("main").append(EavConstants.FILE_SEPARATOR)
			.append("resources").toString();

	private final String isin = "AF23456";
	private final String prdDirTest1 = "monRepertoire";
	private final String prdDirTest2 = "mon Repertoire";
	private final String prdDirTest3 = "m@nRép€rt°ire&é(-è_çà";

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void deleteOutputFiles() throws Exception {
		// Delete the generated directory
		File genDir = EavConstants.shortcutProductImageDirectory(isin);
		FileUtils.deleteDirectory(genDir);
		genDir = EavConstants.shortcutProductImageDirectory(isin, prdDirTest1).getParentFile();
		FileUtils.deleteDirectory(genDir);
		genDir = EavConstants.shortcutProductImageDirectory(isin, prdDirTest2).getParentFile();
		FileUtils.deleteDirectory(genDir);
		genDir = EavConstants.shortcutProductImageDirectory(isin, prdDirTest3).getParentFile();
		FileUtils.deleteDirectory(genDir);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavConstants#imageResourcesDirectoryPath()}.
	 */
	@Test
	public void testImageResourcesDirectoryPath() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img";
		Assert.assertThat(EavConstants.imageResourcesDirectoryPath(), is(expectedPath));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavConstants#mainResourcesDirectoryPath()}.
	 */
	@Test
	public void testMainResourcesDirectoryPath() {
		Assert.assertThat(EavConstants.mainResourcesDirectoryPath(), is(resDir));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavConstants#staticResourcesDirectoryPath()}.
	 */
	@Test
	public void testStaticResourcesDirectoryPath() {
		String expectedPath = resDir + File.separator + "static";
		Assert.assertThat(EavConstants.staticResourcesDirectoryPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavConstants.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_NullEntry() {
		String isin = null;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin), is(nullValue()));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString() {
		String prdDir = prdDirTest1;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameContainsSpaces() {
		String prdDir = prdDirTest2;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameContainsSpecialChars() {
		String prdDir = "m@nRép€rt°ire/\\&é(-è_çà";
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameNull() {
		String prdDir = null;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavConstants.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;

		Assert.assertThat(EavConstants.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavConstants.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;
		File dir = EavConstants.shortcutProductImageDirectory(isin);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_NullEntry() {
		File dir = EavConstants.shortcutProductImageDirectory(null);

		Assert.assertNull(dir);
	}

	@Test
	public void testShortcutProductImageDirectory_StringString() {
		String prdDir = prdDirTest1;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavConstants.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameContainsSpaces() {
		String prdDir = prdDirTest2;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavConstants.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameContainsSpecialChars() {
		String prdDir = prdDirTest3;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavConstants.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameNull() {
		String prdDir = null;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavConstants.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;
		File dir = EavConstants.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testRelativePathFromResource() {
		String absoluteDirPath = resDir + File.separator + "static";

		String expected = File.separator + "static";

		Assert.assertThat(EavConstants.relativePathFromResource(absoluteDirPath), is(expected));
	}

	@Test
	public void testRelativePathFromResource_PathDoesNotReferToExistingFile() {
		String absoluteDirPath = resDir + File.separator + "static-test";

		Assert.assertThat(EavConstants.relativePathFromResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromResource_FileIsNotAChild() {
		String absoluteDirPath = "something" + File.separator + "weird";

		Assert.assertThat(EavConstants.relativePathFromResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromResource_NullEntry() {
		Assert.assertThat(EavConstants.relativePathFromResource(null), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource() {
		String absoluteDirPath = resDir + File.separator + EavConstants.STATIC_DIR_NAME + File.separator + "img";

		String expected = File.separator + "img";

		Assert.assertThat(EavConstants.relativePathFromStaticResource(absoluteDirPath), is(expected));
	}

	@Test
	public void testRelativePathFromStaticResource_PathDoesNotReferToExistingFile() {
		String absoluteDirPath = resDir + File.separator + EavConstants.STATIC_DIR_NAME + File.separator + "img-test";

		Assert.assertThat(EavConstants.relativePathFromStaticResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource_FileIsNotAChild() {
		String absoluteDirPath = "something" + File.separator + "weird";

		Assert.assertThat(EavConstants.relativePathFromStaticResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource_NullEntry() {
		Assert.assertThat(EavConstants.relativePathFromStaticResource(null), is(nullValue()));
	}

}
