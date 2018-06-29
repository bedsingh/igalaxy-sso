
/***********************************************************************************************************
 * Module Name - igalaxy-sso
 * Version Control Block
 * 
 * Date			Version	   Author			Reviewer			Change Description
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * Jun 18, 2018  1.0		   Singh, Bed		XXXXXXXX			Created.
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * 
 ***********************************************************************************************************/

package com.igalaxy.sso.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/***********************************************************************************************************
 * Java File - ApplicationStartupConfig.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

@Component
public class ApplicationStartupConfig implements ApplicationListener<ApplicationReadyEvent>{

	private static Logger logger = LogManager.getLogger(ApplicationStartupConfig.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("Loading Startup Data...");
		
		logger.info("Loaded Startup Data.");
	}

}
