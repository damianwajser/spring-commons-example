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
    private void saveFooObject(@RequestBody FooObject fooObject){
        fooObjectService.saveFooObject(fooObject);
    }

    @GetMapping()
    private Iterable<FooObject> getFooObjects(){
        return fooObjectService.getAll();
    }

}
