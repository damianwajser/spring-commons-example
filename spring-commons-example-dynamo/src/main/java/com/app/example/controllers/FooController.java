package com.app.example.controllers;

import com.app.example.model.FooObject;
import com.app.example.service.FooObjectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fooObjects")
public class FooController {

	private final FooObjectService fooObjectService;

	public FooController(FooObjectService fooObjectService) {
		this.fooObjectService = fooObjectService;
	}

	@PostMapping()
	public FooObject saveFooObject(@RequestBody FooObject fooObject) {
		return fooObjectService.saveFooObject(fooObject);
	}

	@GetMapping()
	public Iterable<FooObject> getFooObjects() {
		return fooObjectService.getAll();
	}

}
