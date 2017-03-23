/**
 * 
 */
package com.synovia.digital.utils;

import java.io.File;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 6 mars 2017
 */
public class EavUtils {

	public static final int C_MILLENARY = 2000;
	public static final int C_DECEMBER_INDEX = 12;

	public static final String LONG_DATE_FORMAT_PATTERN = "EEEEE dd MMMMM yyyy";

	public static final String MID_DATE_FORMAT_PATTERN = "dd MMMMM yyyy";

	public static final String DTO_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	public static final String PRD_MONTH_FORMAT_PATTERN = "MM";

	public static final String PRD_YEAR_FORMAT_PATTERN = "yy";

	public static final String PRD_MONTH_YEAR_FORMAT_PATTERN = "MM.yy";

	public static final String PRD_DAY_MONTH_YEAR_FORMAT_PATTERN = "dd.MM.yy";

	public static final String JPEG_EXTENSION = ".jpg";

	public static final String FILE_SEPARATOR = File.separator;

	public static final String TO_STRING_SEPARATOR = "; ";

	public static final String RESOURCES_DIR_NAME = "resources";

	public static final String STATIC_DIR_NAME = "static";

	public static final String IMAGE_DIR_NAME = "img";

	public static final String DEFAULT_PRODUCT_DIR_NAME = "prd";

	public static final String SRC_DIR_PATH = new StringBuilder(System.getProperty("user.dir")).append(FILE_SEPARATOR)
			.append("src").toString();

	/**
	 * Returns the image resources folder. Doesn't end with a file separator.
	 * 
	 * @return
	 */
	public static String imageResourcesDirectoryPath() {
		return new StringBuilder(staticResourcesDirectoryPath()).append(FILE_SEPARATOR).append(IMAGE_DIR_NAME)
				.toString();
	}

	public static String shortcutProductImageDirectoryPath(String isinPrdProduct) {
		return shortcutProductImageDirectoryPath(isinPrdProduct, DEFAULT_PRODUCT_DIR_NAME);
	}

	public static String shortcutProductImageDirectoryPath(String isinPrdProduct, String productDirectoryName) {
		if (isinPrdProduct == null)
			return null;

		String dirName = productDirectoryName != null ? productDirectoryName : DEFAULT_PRODUCT_DIR_NAME;

		return new StringBuilder(imageResourcesDirectoryPath()).append(FILE_SEPARATOR).append(dirName)
				.append(FILE_SEPARATOR).append(isinPrdProduct).toString();
	}

	public static File shortcutProductImageDirectory(String isinPrdProduct) {
		return shortcutProductImageDirectory(isinPrdProduct, DEFAULT_PRODUCT_DIR_NAME);
	}

	public static File shortcutProductImageDirectory(String isinPrdProduct, String productDirName) {
		String path = shortcutProductImageDirectoryPath(isinPrdProduct, productDirName);
		File result = null;
		if (path != null) {
			result = new File(path);
			if (!result.exists()) {
				// Create the directory
				result.mkdirs();
			}
		}
		return result;
	}

	/**
	 * Returns the relative path of an input absolute path from the resource directory.
	 * The relative path starts with a file separator.
	 * 
	 * @param path
	 *            The input path. Must be an existing path and must be written relatively
	 *            to the resource path.
	 * @return The relative path of an absolute path from the resource directory. Returns
	 *         {@code null} if the input path is null, does not exist or is not written
	 *         relatively to the resource path.
	 */
	public static String relativePathFromResource(String path) {
		return relativePathFrom(path, RESOURCES_DIR_NAME);
	}

	public static String relativePathFromStaticResource(String path) {
		return relativePathFrom(path, STATIC_DIR_NAME);
	}

	private static String relativePathFrom(String path, String dirNameDelimiter) {
		String result = null;
		if (path == null)
			return result;

		File f = new File(path);
		if (!f.exists())
			return result;

		String[] splits = path.split(dirNameDelimiter);
		if (path.contains(mainResourcesDirectoryPath()) && splits.length > 1) {
			result = splits[1];
		}
		return result;
	}

	/**
	 * Returns the main resources folder. Doesn't end with a file separator.
	 * 
	 * @return
	 */
	public static String mainResourcesDirectoryPath() {
		return new StringBuilder(SRC_DIR_PATH).append(FILE_SEPARATOR).append("main").append(FILE_SEPARATOR)
				.append(RESOURCES_DIR_NAME).toString();
	}

	/**
	 * Returns the static resources folder. Doesn't end with a file separator.
	 * 
	 * @return
	 */
	public static String staticResourcesDirectoryPath() {
		return new StringBuilder(mainResourcesDirectoryPath()).append(FILE_SEPARATOR).append(STATIC_DIR_NAME)
				.toString();
	}

}
