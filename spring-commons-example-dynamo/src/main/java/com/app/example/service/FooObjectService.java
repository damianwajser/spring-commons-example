package com.app.example.service;

import com.app.example.model.FooObject;

public interface FooObjectService {

	public FooObject saveFooObject(FooObject fooObject);

	public Iterable<FooObject> getAll();

}
