package com.app.example.controllers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.github.damianwajser.exceptions.impl.badrequest.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	private final RestTemplate restTemplate;

	private final AmazonDynamoDB amazonDynamoDBClient;

	public Controller(RestTemplate restTemplate, AmazonDynamoDB amazonDynamoDBClient) {
		this.restTemplate = restTemplate;
		this.amazonDynamoDBClient = amazonDynamoDBClient;
	}

	@GetMapping("/dynamo-tables")
	public Object getDynamoTables() {
		//using amazon client
		return amazonDynamoDBClient.listTables();
	}

	@GetMapping("/echo")
	public Object test() {
		LOGGER.info("echo in service 0");
		amazonDynamoDBClient.listTables();
		return restTemplate.getForObject("https://httpbin.org/get", Object.class);
	}

	@GetMapping("/ex")
	public Object testex() throws BadRequestException {
		throw new BadRequestException("", "{message}", Optional.empty());
	}
}
