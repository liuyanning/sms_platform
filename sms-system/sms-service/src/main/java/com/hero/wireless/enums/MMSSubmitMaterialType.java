package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;


public enum MMSSubmitMaterialType {
    PICTURE, VIDEO, AUDIO,TXT;
	private String value;

	private MMSSubmitMaterialType() {
		this.value = this.name().toLowerCase();
	}

	private MMSSubmitMaterialType(String value) {
		this.value = value.toLowerCase();
	}

	public String toString() {
		return value;
	}

	public boolean equals(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		return this.value.equalsIgnoreCase(value);
	}
}
