package com.app.example.repository;

import com.app.example.model.FooObject;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface FooObjectRepository extends CrudRepository<FooObject, String> {

}