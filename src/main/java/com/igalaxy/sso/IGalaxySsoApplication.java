

package com.igalaxy.sso;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/***********************************************************************************************************
 * Java File - IGalaxySsoApplication.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/
@ComponentScan(basePackages = {"com.igalaxy.sso"})
@SpringBootApplication
public class IGalaxySsoApplication extends SpringBootServletInitializer {

	private static Logger logger = LogManager.getLogger(IGalaxySsoApplication.class);

	public static void main(String[] args) {


		final String dashLine = "-------------------------------------------------------------------------";
		logger.info(dashLine);
		logger.info("**************** IGALAXY SSO API STARTING, PLEASE WAIT. ***************** ");
		logger.info(dashLine);

		SpringApplication.run(IGalaxySsoApplication.class, args);

		logger.info(dashLine);
		logger.info("**************** IGALAXY SSO API STARTED SUCCESSFULLY. ****************** ");
		logger.info(dashLine);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IGalaxySsoApplication.class);
	}

}
