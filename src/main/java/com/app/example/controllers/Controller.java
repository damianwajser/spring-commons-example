package com.app.example.controllers;

import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.app.example.model.FooObject;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ServiceCache service;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/echo")
	private Object test() {
		LOGGER.info("echo en servicio 0");
		//return restTemplate.getForObject("http://localhost:8081/echo", Object.class);
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}

	@GetMapping("/ex")
	private Object testex() throws BadRequestException {
		String t = messageSource.getMessage("message1", new Object[]{}, "message", Locale.US);
		throw new BadRequestException("", "${message}", Optional.empty());
	}

	@GetMapping("/echo1")
	private Object echo() {
		LOGGER.info("echo1");
		return new FooObject("asdasd");
	}
	@GetMapping("/cache")
	private Object echo1() {
		LOGGER.info("echo1");
		return service.getAlgo();
	}


	@PostMapping("/idem")
	private Object echo1(FooObject obj) {
		LOGGER.info("echo1");
		return new FooObject("asdasd");
	}

}
