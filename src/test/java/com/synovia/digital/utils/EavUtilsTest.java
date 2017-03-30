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
public class EavUtilsTest {

	private final String resDir = new StringBuilder(System.getProperty("user.dir")).append(EavUtils.FILE_SEPARATOR)
			.append("src").append(EavUtils.FILE_SEPARATOR).append("main").append(EavUtils.FILE_SEPARATOR)
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
		File genDir = EavUtils.shortcutProductImageDirectory(isin);
		FileUtils.deleteDirectory(genDir);
		genDir = EavUtils.shortcutProductImageDirectory(isin, prdDirTest1).getParentFile();
		FileUtils.deleteDirectory(genDir);
		genDir = EavUtils.shortcutProductImageDirectory(isin, prdDirTest2).getParentFile();
		FileUtils.deleteDirectory(genDir);
		genDir = EavUtils.shortcutProductImageDirectory(isin, prdDirTest3).getParentFile();
		FileUtils.deleteDirectory(genDir);
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavUtils#imageResourcesDirectoryPath()}.
	 */
	@Test
	public void testImageResourcesDirectoryPath() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img";
		Assert.assertThat(EavUtils.imageResourcesDirectoryPath(), is(expectedPath));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavUtils#mainResourcesDirectoryPath()}.
	 */
	@Test
	public void testMainResourcesDirectoryPath() {
		Assert.assertThat(EavUtils.mainResourcesDirectoryPath(), is(resDir));
	}

	/**
	 * Test method for
	 * {@link com.synovia.digital.utils.EavUtils#staticResourcesDirectoryPath()}.
	 */
	@Test
	public void testStaticResourcesDirectoryPath() {
		String expectedPath = resDir + File.separator + "static";
		Assert.assertThat(EavUtils.staticResourcesDirectoryPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavUtils.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_NullEntry() {
		String isin = null;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin), is(nullValue()));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString() {
		String prdDir = prdDirTest1;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameContainsSpaces() {
		String prdDir = prdDirTest2;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameContainsSpecialChars() {
		String prdDir = "m@nRép€rt°ire/\\&é(-è_çà";
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectoryPath_StringString_DirNameNull() {
		String prdDir = null;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavUtils.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;

		Assert.assertThat(EavUtils.shortcutProductImageDirectoryPath(isin, prdDir), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory() {
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavUtils.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;
		File dir = EavUtils.shortcutProductImageDirectory(isin);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_NullEntry() {
		File dir = EavUtils.shortcutProductImageDirectory(null);

		Assert.assertNull(dir);
	}

	@Test
	public void testShortcutProductImageDirectory_StringString() {
		String prdDir = prdDirTest1;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavUtils.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameContainsSpaces() {
		String prdDir = prdDirTest2;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavUtils.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameContainsSpecialChars() {
		String prdDir = prdDirTest3;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator + prdDir
				+ File.separator + isin;
		File dir = EavUtils.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testShortcutProductImageDirectory_StringString_DirNameNull() {
		String prdDir = null;
		String expectedPath = resDir + File.separator + "static" + File.separator + "img" + File.separator
				+ EavUtils.DEFAULT_PRODUCT_DIR_NAME + File.separator + isin;
		File dir = EavUtils.shortcutProductImageDirectory(isin, prdDir);

		Assert.assertNotNull(dir);
		Assert.assertTrue(dir.exists());
		Assert.assertThat(dir.getPath(), is(expectedPath));
	}

	@Test
	public void testRelativePathFromResource() {
		String absoluteDirPath = resDir + File.separator + "static";

		String expected = File.separator + "static";

		Assert.assertThat(EavUtils.relativePathFromResource(absoluteDirPath), is(expected));
	}

	@Test
	public void testRelativePathFromResource_PathDoesNotReferToExistingFile() {
		String absoluteDirPath = resDir + File.separator + "static-test";

		Assert.assertThat(EavUtils.relativePathFromResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromResource_FileIsNotAChild() {
		String absoluteDirPath = "something" + File.separator + "weird";

		Assert.assertThat(EavUtils.relativePathFromResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromResource_NullEntry() {
		Assert.assertThat(EavUtils.relativePathFromResource(null), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource() {
		String absoluteDirPath = resDir + File.separator + EavUtils.STATIC_DIR_NAME + File.separator + "img";

		String expected = File.separator + "img";

		Assert.assertThat(EavUtils.relativePathFromStaticResource(absoluteDirPath), is(expected));
	}

	@Test
	public void testRelativePathFromStaticResource_PathDoesNotReferToExistingFile() {
		String absoluteDirPath = resDir + File.separator + EavUtils.STATIC_DIR_NAME + File.separator + "img-test";

		Assert.assertThat(EavUtils.relativePathFromStaticResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource_FileIsNotAChild() {
		String absoluteDirPath = "something" + File.separator + "weird";

		Assert.assertThat(EavUtils.relativePathFromStaticResource(absoluteDirPath), is(nullValue()));
	}

	@Test
	public void testRelativePathFromStaticResource_NullEntry() {
		Assert.assertThat(EavUtils.relativePathFromStaticResource(null), is(nullValue()));
	}

	//	@Test
	public void testPaginate() {
		// Test 1
		int currentPg = 1;
		int totalPageNb = 15;

		paginate(currentPg, totalPageNb);

		// Test 2
		currentPg = 4;
		paginate(currentPg, totalPageNb);

		// Test 3
		currentPg = 8;
		paginate(currentPg, totalPageNb);

		// Test 4
		totalPageNb = 25;
		currentPg = 17;
		paginate(currentPg, totalPageNb);

		// Test 5
		totalPageNb = 25;
		currentPg = 25;
		paginate(currentPg, totalPageNb);
	}

	private void paginate(int currentPageNb, int totalPageNb) {
		System.out.println("Pagination: current=" + currentPageNb + ", total=" + totalPageNb);
		int sliceSize = 10;
		int beginPageNb = Math.max(1, currentPageNb - sliceSize / 2);
		int endPageNb = Math.min(beginPageNb + sliceSize, totalPageNb);

		for (int i = 0; i < endPageNb - beginPageNb + 1; i++) {
			int displayedNb = i + beginPageNb;
			Puce puce = new Puce(i, displayedNb);

			puce.active = (displayedNb == currentPageNb);
			System.out.print(puce);
		}

		System.out.println(" | ");
	}

	private class Puce {
		private boolean active;
		private int displayedNumber;
		private int pos;

		/**
		 * TODO Constructs ... based on ...
		 *
		 */
		public Puce(int position, int dispalyedNumber) {
			this.pos = position;
			this.displayedNumber = dispalyedNumber;
			this.active = false;
		}

		@Override
		public String toString() {
			String strActiveStart = active ? "( " : "";
			String strActiveEnd = active ? " )" : "";
			return new StringBuilder(" | ").append(strActiveStart).append(displayedNumber).append(strActiveEnd)
					.toString();
		}
	}
}
