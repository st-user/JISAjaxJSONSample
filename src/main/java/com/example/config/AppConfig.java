package com.example.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.example.controller" })
public class AppConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.factory(new ArbitrarilyEncodingMappingJsonFactory());
		converters.add(new ArbitrarilyEncodingMappingJackson2HttpMessageConverter(builder.build()));
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("", ".html");
	}

}
