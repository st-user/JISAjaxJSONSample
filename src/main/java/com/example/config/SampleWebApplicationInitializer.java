package com.example.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.filter.SetJSONCharsetFilter;

public class SampleWebApplicationInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
		ac.register(AppConfig.class);

		servletContext.addFilter("setJSONCharsetFilter", new SetJSONCharsetFilter())//
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/app/*");

		DispatcherServlet servlet = new DispatcherServlet(ac);
		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);
		registration.setLoadOnStartup(1);
		registration.addMapping("/app/*");
	}

}
