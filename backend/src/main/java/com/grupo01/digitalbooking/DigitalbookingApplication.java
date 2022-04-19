package com.grupo01.digitalbooking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Slf4j
@SpringBootApplication
public class DigitalbookingApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(DigitalbookingApplication.class, args);
		}catch (Exception ex){
			log.error("Something happened: {}:{} \nCause:{}",ex.getClass(),ex.getMessage(),ex.getCause());
		}
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Digital Booking Turma 01")
				.description("Api do projeto integador do curso CTD da Digital House")
				.version("0.5")
				.build();
	}

}
