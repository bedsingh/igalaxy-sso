
/***********************************************************************************************************
 * Module Name - igalaxy-sso
 * Version Control Block
 * 
 * Date			Version	   Author			Reviewer			Change Description
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * Jun 20, 2018  1.0		   Singh, Bed		XXXXXXXX			Created.
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * 
 ***********************************************************************************************************/

package com.igalaxy.sso.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import com.igalaxy.sso.security.AuthTokenService;
import com.igalaxy.sso.security.AuthenticationFilter;
import com.igalaxy.sso.security.TokenAuthenticationProvider;

/***********************************************************************************************************
 * Java File - SecurityConfig.java
 * Author - Bed Singh
 * Date   - Jun 20, 2018
 * Description - 
 ***********************************************************************************************************/

@Configuration
//@EnableCaching
@EnableWebSecurity
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger logger = LogManager.getLogger(SecurityConfig.class);

	@Bean
	public static ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers("/healthcheck", "/authentication").permitAll()
		.and().csrf().disable()
		.anonymous().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint())
		.and()
		.addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers(HttpMethod.OPTIONS)
		.antMatchers("/healthcheck", "/authentication");
	}

	private AuthenticationEntryPoint unauthorizedEntryPoint() {
		logger.info("unauthorizedEntryPoint");
		return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		AuthenticationManager authManager = null;
		List<AuthenticationProvider> list = new ArrayList<>();
		list.add(new TokenAuthenticationProvider(tokenService()));
		authManager = new ProviderManager(list);
		return authManager;
	}

	@Bean
	public AuthTokenService tokenService() {
		return new AuthTokenService();
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

}
