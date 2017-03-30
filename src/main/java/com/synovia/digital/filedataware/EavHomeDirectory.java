/**
 * 
 */
package com.synovia.digital.filedataware;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synovia.digital.exceptions.EavTechnicalException;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 8 mars 2017
 */
public class EavHomeDirectory {
	private static final Logger LOGGER = LoggerFactory.getLogger(EavHomeDirectory.class);
	public static final String PRODUCT_DIR_NAME = "PRD";
	public static final String IMAGE_DIR_NAME = "img";
	public static final String TERM_SHEET_DIR_NAME = "tsheet";
	public static final String FEASE_DIR_NAME = "fease";
	public static final String MARKETING_DIR_NAME = "makt";

	public static final Long IMAGE_MAX_SIZE_BYTE = 1000L * 1024;

	/** The path of the root directory of the file data warehouse. */
	private String path;
	/** The root directory of the file system that stores application's dynamic data. */
	private File root;
	/**
	 * The folder that contains all products and their document. Default name is "PRD".
	 */
	private File productDir;

	/**
	 * TODO Constructs ... based on ...
	 * 
	 * @throws EavTechnicalException
	 *
	 */
	public EavHomeDirectory(String path) throws EavTechnicalException {
		LOGGER.debug("Configuration property fdwh.home.path {} from config.properties file", path);

		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			if (!file.mkdirs())
				throw new EavTechnicalException("datawarehouse.creation.error");
		}

		if (!file.canRead() && !file.setReadable(true))
			throw new EavTechnicalException("datawarehouse.read.permission.error");

		if (!file.canWrite() && !file.setWritable(true))
			throw new EavTechnicalException("datawarehouse.write.permission.error");

		if (!file.canExecute() && !file.setExecutable(true))
			throw new EavTechnicalException("datawarehouse.exe.permission.error");

		this.root = file;
		this.path = file.getPath();
		// Create product directory if not exists
		File productDir = new File(root, PRODUCT_DIR_NAME);
		if (!productDir.exists()) {
			productDir.mkdirs();
		}

		this.productDir = productDir;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getRoot() {
		return this.root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public File getImageDir(Long idPrdProduct) {
		String relativePath = new StringBuilder(idPrdProduct.toString()).append(File.separator).append(IMAGE_DIR_NAME)
				.toString();
		File result = new File(productDir, relativePath);
		if (!result.exists()) {
			result.mkdirs();
		}

		return result;
	}

	public File getTermSheetDir(Long idPrdProduct) {
		String relativePath = new StringBuilder(idPrdProduct.toString()).append(File.separator)
				.append(TERM_SHEET_DIR_NAME).toString();
		File result = new File(productDir, relativePath);
		if (!result.exists()) {
			result.mkdirs();
		}

		return result;

	}

	public File getFeaseDir(Long idPrdProduct) {
		String relativePath = new StringBuilder(idPrdProduct.toString()).append(File.separator).append(FEASE_DIR_NAME)
				.toString();
		File result = new File(productDir, relativePath);
		if (!result.exists()) {
			result.mkdirs();
		}

		return result;

	}

	public File getMarketingDir(Long idPrdProduct) {
		String relativePath = new StringBuilder(idPrdProduct.toString()).append(File.separator)
				.append(MARKETING_DIR_NAME).toString();
		File result = new File(productDir, relativePath);
		if (!result.exists()) {
			result.mkdirs();
		}

		return result;

	}

	public boolean deleteDir(Long idPrdProduct) {
		boolean deleted = true;

		String relativePath = new StringBuilder(idPrdProduct.toString()).toString();
		File dir = new File(productDir, relativePath);

		try {
			FileUtils.deleteDirectory(dir);

		} catch (IOException e) {
			e.printStackTrace();
			deleted = false;
		}

		return deleted;
	}
}
