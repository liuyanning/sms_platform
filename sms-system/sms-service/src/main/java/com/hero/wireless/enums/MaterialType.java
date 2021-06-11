package com.hero.wireless.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 素材类型》》material_type_code
 * 
 * @author gengjinbiao
 * @date 2019年12月27日
 * @version V1.0
 */
public enum MaterialType {
	PICTURE, VIDEO, AUDIO;
	private String value;

	private MaterialType() {
		this.value = this.name().toLowerCase();
	}

	private MaterialType(String value) {
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
