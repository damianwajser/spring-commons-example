package com.app.example.controllers;

import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	@Autowired
	private RestTemplate restTemplate;


	@GetMapping("/echo")
	private Object test() {
		LOGGER.info("echo en servicio 0");
		//return restTemplate.getForObject("http://localhost:8081/echo", Object.class);
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}

	@GetMapping("/ex")
	private Object testex() throws BadRequestException {
		throw new BadRequestException("", "${message}", Optional.empty());
	}

}
