package com.app.example.controllers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceCache {
	@Cacheable("some-cache")
	public String getAlgo() {
		return UUID.randomUUID().toString();
	}
}
