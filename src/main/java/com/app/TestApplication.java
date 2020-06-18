package com.app;

import com.app.example.model.FooObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.github.damianwajser", "com.app"})
public class TestApplication implements WebMvcConfigurer {
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(TestApplication.class, args);
		/*ObjectMapper mapper = new ObjectMapper();
		//There are other configuration options you can set.  This is the one I needed.
		mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);

		JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
		JsonSchema schema = schemaGen.generateSchema(FooObject.class);

		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));*/
	}
}


