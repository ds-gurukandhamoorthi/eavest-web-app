/**
 * 
 */
package com.synovia.digital.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.filedataware.EavHomeDirectory;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 8 mars 2017
 */
@Configuration
@ComponentScan("com.synovia.digital")
@PropertySource("classpath:config.properties")
public class EavConfigApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(EavConfigApp.class);
	public static final String CONFIG_EAVEST_HOME = "fdwh.home.path";

	@Autowired
	private Environment env;

	@Bean
	public EavHomeDirectory eavHomeDirectory() {
		EavHomeDirectory dir = null;
		try {
			dir = new EavHomeDirectory(env.getProperty(CONFIG_EAVEST_HOME));
		} catch (EavTechnicalException e) {
			e.printStackTrace();
			LOGGER.error(
					"Eavest web app configuration failed to load file data warehouse property. See config.properties");
		}
		return dir;
	}
}
