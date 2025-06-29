package com.eit.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eit.gateway.util.CommonUtils;

@Configuration
public class Config {

	@Bean
	public CommonUtils commonUtils() {
		return new CommonUtils();
	}
}
