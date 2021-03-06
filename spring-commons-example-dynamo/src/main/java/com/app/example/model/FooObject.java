package com.app.example.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "FooObjectTable")
public class FooObject {

  @DynamoDBHashKey
  @DynamoDBAutoGeneratedKey
  private String id;
  private String value;

  public FooObject() {
  }

  public FooObject(String id, String value) {
    this.id = id;
    this.value = value;
  }

  @DynamoDBAttribute
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DynamoDBAttribute
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
