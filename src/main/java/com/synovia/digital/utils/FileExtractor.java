/**
 * 
 */
package com.synovia.digital.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.synovia.digital.exceptions.EavTechnicalException;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 8 mars 2017
 */
public class FileExtractor {

	public static Logger LOGGER = LoggerFactory.getLogger(FileExtractor.class);

	public static void copy(File from, Param... params) throws EavTechnicalException {
		if (from == null || !from.exists()) {
			LOGGER.error("Input directory {} does not exist", from);
			return;

		}
		if (params == null || params.length == 0) {
			LOGGER.error(
					"The parameters of the copy method has not been set or is empty. The method doesn't know what to copy and where.");
			return;
		}

		if (from.isDirectory()) {
			for (File child : from.listFiles()) {
				copy(child, params);
			}
		} else {
			for (Param p : params) {
				if (p != null && from.getName().endsWith(p.what)) {
					File copy = new File(p.where, from.getName());
					LOGGER.debug("Target directory for the extraction is {}", copy.getPath());
					if (!copy.mkdirs()) {
						LOGGER.warn("Input file {} has not been copied", from.getName());
					} else {
						try {
							// Create the output file
							if (!copy.createNewFile()) {
								LOGGER.info("Destination file {} already exists", copy);
								copy.delete();
								copy.createNewFile();
							}
							// Sets the rights
							if (!copy.setWritable(true) || !copy.setReadable(true)) {
								LOGGER.warn("No permission to change the access permissions of output file {}",
										from.getName());

							}
						} catch (IOException e1) {
							e1.printStackTrace();
							LOGGER.error("Unable to create output file {}", from);
							throw new EavTechnicalException("close.file.error");
						}
						// Transfer the data
						FileInputStream fisFrom = null;
						FileOutputStream fisTo = null;
						try {
							fisFrom = new FileInputStream(from);
							fisTo = new FileOutputStream(copy);
							FileChannel src = fisFrom.getChannel();
							FileChannel dst = fisTo.getChannel();
							dst.transferFrom(src, 0, src.size());

						} catch (IOException e) {
							e.printStackTrace();
							LOGGER.warn("Input file {} cannot be correctly read or copied.", from.getName());

						} finally {
							if (fisFrom != null) {
								try {
									fisFrom.close();
								} catch (IOException e) {
									LOGGER.error("Unable to close input file {}", from);
									throw new EavTechnicalException("close.file.error");
								}
							}
							if (fisTo != null) {
								try {
									fisTo.close();
								} catch (IOException e) {
									LOGGER.error("Unable to close output file {}", from);
									throw new EavTechnicalException("close.file.error");
								}
							}
						}
					}
				}
			}
		}

	}

	public static class Param {
		private String what;
		private File where;

		/**
		 * 
		 * Constructs a series of parameters used for the {@code FileExtractor} class.
		 * These parameters are
		 * <ul>
		 * <li>the suffix of files to be extracted</li>
		 * <li>the directory of files to be extracted</li>
		 * <ul>
		 *
		 * @param suffix
		 *            T
		 * @param where
		 * @throws EavTechnicalException
		 */
		public Param(String suffix, File where) throws EavTechnicalException {
			this.what = suffix;
			this.where = where;
		}

	}
}
