/**
 * 
 */
package com.synovia.digital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.synovia.digital.exceptions.EavTechnicalException;
import com.synovia.digital.service.PrdSousJacentService;

/**
 * This class defines TODO
 * 
 * @author TeddyCouriol
 * @since 27 mars 2017
 */
@Component
public class ScheduledTasks {

	@Autowired
	private PrdSousJacentService ssjcService;

	// TODO Uncomment next line
	//	@Scheduled(cron = "0 0 0 1 * *")
	@Scheduled(fixedRate = 3600000, initialDelay = 10000)
	public void updateMonthlyData() {
		// Update underlying asset
		try {
			ssjcService.updateAll();

		} catch (EavTechnicalException e) {
			e.printStackTrace();
		}
	}
}
