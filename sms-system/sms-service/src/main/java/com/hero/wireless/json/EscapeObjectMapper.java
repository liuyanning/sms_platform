package com.hero.wireless.json;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.text.StringEscapeUtils;

import java.text.SimpleDateFormat;

public class EscapeObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 675737850826738923L;
	private static final String DEFAULT_DATE_FMT = "yyyy-MM-dd HH:mm:ss";

	public EscapeObjectMapper() {
		super();
		this.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FMT));
		// 序列换成json时,将所有的long变成string,因为js中得数字类型不能包含所有的java long值
		SimpleModule simpleModule = new SimpleModule("LongModule", new Version(1, 0, 0, ""));
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		registerModule(simpleModule);
		int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
		esc['<'] = CharacterEscapes.ESCAPE_CUSTOM;
		esc['>'] = CharacterEscapes.ESCAPE_CUSTOM;
		getFactory().setCharacterEscapes(new CharacterEscapes() {
			private static final long serialVersionUID = 1L;

			@Override
			public SerializableString getEscapeSequence(int ch) {
				if (ch == '<' || ch == '>') {
					return new SerializedString(StringEscapeUtils.escapeHtml4(String.valueOf((char) ch)));
				}
				return null;
			}

			@Override
			public int[] getEscapeCodesForAscii() {
				return esc;
			}
		});
	}

	public String asString(Object value) {
		try {
			return this.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> T asObject(String content, Class<T> valueType) {
		try {
			return this.readValue(content, valueType);
		} catch (Exception e) {
			SuperLogger.error(e.getMessage(), e);
			return null;
		}
	}
}