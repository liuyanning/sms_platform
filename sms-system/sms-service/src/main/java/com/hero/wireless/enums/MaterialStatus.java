package com.hero.wireless.enums;

public enum MaterialStatus {

	NORMAL("0"),
	EXPIRATION("1"),
	DELETE("2");

	private String value;

	MaterialStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
