package com.blog.bloggui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BlogGuiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BlogGuiApplication.class, args);
	}
	
	//servlet inizializer
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BlogGuiApplication.class);
	}

}
