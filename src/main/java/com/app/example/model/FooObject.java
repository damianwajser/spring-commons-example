package com.app.example.model;

import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Objects;

public class FooObject {

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String value;

	public SerializationFeature getEnumExample() {
		return enumExample;
	}

	public void setEnumExample(SerializationFeature enumExample) {
		this.enumExample = enumExample;
	}

	private SerializationFeature enumExample;

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	private int value2;

	public FooObject() {
	}

	public FooObject(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof FooObject)) return false;
		FooObject fooObject = (FooObject) o;
		return Objects.equals(getValue(), fooObject.getValue());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValue());
	}
}
