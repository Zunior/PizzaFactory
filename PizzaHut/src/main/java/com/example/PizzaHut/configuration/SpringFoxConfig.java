package com.example.PizzaHut.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.PizzaHut.util.SecurityConstants;
import com.google.common.base.Predicate;
//import static com.google.common.base.Predicates.or;
//import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.and;
import static springfox.documentation.builders.PathSelectors.regex;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @Import(SpringDataRestConfiguration.class)
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

//  @Bean
//  public Docket api() {
//    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//  }

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//        .pathMapping("/")
//        .apiInfo(ApiInfo.DEFAULT)
				.securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select()
				.apis(RequestHandlerSelectors.any()).paths(paths2())
//        .paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("My REST API", "Some custom description of API.", "1.0", "Terms of service",
				new Contact("Sasa Popovic", "www.example.com", "sasa.popovic.prog@gmail.com"), "License of API",
				"API license URL", Collections.emptyList());
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", SecurityConstants.HEADER_NAME, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.ant("/pizzaFactory/**")).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

	// Select only a few
	private Predicate<String> paths2() {
		return and((regex("/pizzaFactory/*"))
//                ,(regex("/api/*"))
		);
	}
	// Exclude these
//    private Predicate<String> paths() {
//        return and(
//                not(regex("/error.*")),
//                not(regex("/metrics.*")),
//                not(regex("/jolokia.*")),
//                not(regex("/health.*")),
//                not(regex("/env.*")),
//    }

}
