package com.app.example.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collection;

public class FooObject {

	private String value;

	private Collection<FooObject> metadata;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equalsWhitoutMetadata(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "metadata");
	}

}
