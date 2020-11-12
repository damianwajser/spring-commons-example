package com.app.example.service.impl;

import com.app.example.model.FooObject;
import com.app.example.repository.FooObjectRepository;
import com.app.example.service.FooObjectService;
import org.springframework.stereotype.Service;

@Service
public class FooObjectServiceImpl implements FooObjectService {

  private final FooObjectRepository fooObjectRepository;

  public FooObjectServiceImpl(FooObjectRepository fooObjectRepository) {
    this.fooObjectRepository = fooObjectRepository;
  }

  @Override
  public void saveFooObject(FooObject fooObject) {
    fooObjectRepository.save(fooObject);
  }

  @Override
  public Iterable<FooObject> getAll() {
    return fooObjectRepository.findAll();
  }
}
