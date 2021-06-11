package com.hero.wireless.web.dao.redis;

import com.drondea.wireless.util.SuperLogger;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hero.wireless.web.entity.base.IEntity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyGenerator implements org.springframework.cache.interceptor.KeyGenerator {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	static {
		MAPPER.setSerializationInclusion(Include.NON_NULL);
	}

	public static String writeValueAsString(Object obj) {
		try {
			return MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			SuperLogger.error(e.getMessage());
		}
		return "{}";
	}

	public static Map<String, Object> readValue(String content) {
		try {
			return MAPPER.readValue(content, new TypeReference<LinkedHashMap<String, Object>>() {
			});
		} catch (Exception e) {
			SuperLogger.error(e.getMessage());
		}
		return new LinkedHashMap<String, Object>();
	}

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return writeValueAsString(generateMap(params));
	}

	public static Map<String, Object> generateMap(Object... params) {
		Map<String, Object> paramMap = Arrays.asList(params).stream().flatMap(item -> {
			if (item == null) {
				return new LinkedHashMap<String, Object>().entrySet().stream();
			}
			if (item instanceof Collection) {
				return generateMap(((Collection<?>) item).toArray()).entrySet().stream();
			}

			if (item instanceof Object[]) {
				return generateMap(((Object[]) item)).entrySet().stream();
			}
			if (!(item instanceof IEntity)) {
				return new LinkedHashMap<String, Object>().entrySet().stream();
			}
			return readValue(writeValueAsString(item)).entrySet().stream();
		}).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (f, s) -> f, LinkedHashMap::new));
		return paramMap;
	}
}
