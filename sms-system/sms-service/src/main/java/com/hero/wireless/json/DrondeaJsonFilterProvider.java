package com.hero.wireless.json;

import com.drondea.wireless.util.SuperLogger;
import com.drondea.wireless.util.BlurUtil;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hero.wireless.web.config.DatabaseCache;
import com.hero.wireless.web.entity.business.Code;

/**
 * 过滤器
 *
 * @author Lenovo
 *
 */
@JsonFilter("drondeaJsonFilterProvider")
public class DrondeaJsonFilterProvider extends SimpleFilterProvider {

    /**
     *
     */
    private static final long serialVersionUID = -958634263300009387L;
    // 需要翻译的属性
    private Map<Class<?>, Set<String>> translationMap = new HashMap<>();
    // 排除属性
    private Map<Class<?>, Set<String>> excludeMap = new HashMap<>();
    // 包含属性
    private Map<Class<?>, Set<String>> includeMap = new HashMap<>();
    // 脱敏属性
    private Map<Class<?>, Set<String>> blurMap = new HashMap<>();

    // 需要扩展信息的属性
    private Map<Class<?>, Set<KV>> extInfoMap = new HashMap<>();


    private ObjectMapper objectMapper;

    public DrondeaJsonFilterProvider(ObjectMapper objectMapper) {
        super();
        this.objectMapper = objectMapper;
    }

    public void addTranslation(Class<?> clazz, String[] translations) {
        addToMap(translationMap, clazz, translations);
    }

    public void addExclude(Class<?> type, String[] fields) {
        addToMap(excludeMap, type, fields);
    }

    public void addInclude(Class<?> type, String[] fields) {
        addToMap(includeMap, type, fields);
    }

    public void addBlur(Class<?> type, String[] fields) {
        addToMap(blurMap, type, fields);
    }

    // TODO:后续优化------------------------------------------------
    public void addExtInfo(Class<?> type, Set<KV> extInfo) {
        Set<KV> fieldSet = extInfoMap.getOrDefault(type, new HashSet<>());
        if (objectMapper.findMixInClassFor(type) == null) {
            objectMapper.addMixIn(type, DrondeaJsonFilterProvider.class);
        }
        fieldSet.addAll(extInfo);
        extInfoMap.put(type, fieldSet);
    }

    // TODO:后续优化------------------------------------------------
    private void addToMap(Map<Class<?>, Set<String>> map, Class<?> type, String[] fields) {
        Set<String> fieldSet = map.getOrDefault(type, new HashSet<>());
        if (objectMapper.findMixInClassFor(type) == null) {
            objectMapper.addMixIn(type, DrondeaJsonFilterProvider.class);
        }
        fieldSet.addAll(Arrays.asList(fields));
        map.put(type, fieldSet);
    }

    @Override
    public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
        return new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider prov,
                PropertyWriter writer) throws Exception {
                // 是否序列化字段
                if (isSerialize(pojo.getClass(), writer.getName())) {
                    writer.serializeAsField(pojo, jgen, prov);
                } else if (!jgen.canOmitFields()) {
                    writer.serializeAsOmittedField(pojo, jgen, prov);
                    return;
                }
                doTranslation(pojo, jgen, writer);
                doExtInfo(pojo, jgen, writer);
                doBlur(pojo, jgen, writer);
            }
        };
    }

    private Method getter(Object obj, String attrName) throws Exception {
        String attrMethodName = attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
        Method met = obj.getClass().getMethod("get" + attrMethodName);
        return met;
    }

    /**
     * 过滤敏感信息
     * 例如手机号中间4位隐藏
     */
    private void doBlur(Object pojo, JsonGenerator jgen, PropertyWriter writer) throws Exception {
        Set<String> blurs = blurMap.get(pojo.getClass());
        if (blurs == null || blurs.isEmpty()) {
            return;
        }
        blurs.forEach(property -> {
            String[] propertyArray = property.split(":");
            if (propertyArray.length < 2) {
                return;
            }
            if (!propertyArray[0].equals(writer.getName())) {
                return;
            }
            try {
                String value = String.valueOf(getter(pojo, propertyArray[0]).invoke(pojo));
                if (StringUtils.isBlank(value)) {
                    return;
                }
                jgen.writeStringField(writer.getName(), BlurUtil.blur(value, propertyArray[1]));
            } catch (Exception e) {
                SuperLogger.error("脱敏出错", e);
            }
        });

    }

    /**
     * 属性扩展信息
     *
     * @param pojo
     * @param jgen
     * @param writer
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IOException
     */
    private void doExtInfo(Object pojo, JsonGenerator jgen, PropertyWriter writer) throws Exception {
        // 扩展属性信息
        Set<KV> extInfos = extInfoMap.get(pojo.getClass());
        if (extInfos == null || extInfos.isEmpty()) {
            return;
        }
        Optional<KV> extOption = extInfos.stream().filter((p) ->
            p.getKey().split(",")[0].equals(writer.getName()))
            .findFirst();
        if (!extOption.isPresent()) {
            return;
        }
        KV extInfo = extOption.get();
        Object value = getter(pojo, extInfo.getKey().split(",")[0]).invoke(pojo);
        if (value == null) {
            jgen.writeStringField(writer.getName() + "_ext", "");
            return;
        }
        Object ext = null;
        if (extInfo.getKey().split(",")[1].endsWith("ById")) {
            ext = extInfo.getValue().getClass().getMethod(extInfo.getKey().split(",")[1], Integer.class)
                .invoke(extInfo.getValue(), value);
        } else if (extInfo.getKey().split(",")[1].endsWith("ByNo")) {
            ext = extInfo.getValue().getClass().getMethod(extInfo.getKey().split(",")[1], String.class)
                .invoke(extInfo.getValue(), value);
        } else {
            ext = extInfo.getValue().getClass().getMethod(extInfo.getKey().split(",")[1], Object.class)
                .invoke(extInfo.getValue(), value);
        }
        if (ext == null) {
            ext = "";
        }
        jgen.writeObjectField(writer.getName() + "_ext", ext);
    }

    /**
     * 翻译字典代码
     *
     * @param pojo
     * @param jgen
     * @param writer
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws Exception
     * @throws IOException
     */
    private void doTranslation(Object pojo, JsonGenerator jgen, PropertyWriter writer) throws Exception {
        Set<String> translations = translationMap.get(pojo.getClass());
        if (translations == null || translations.isEmpty()) {
            return;
        }
        Optional<String> optional = translations.stream().filter((p) -> writer.getName().equals(p.split(",")[1]))
            .findFirst();
        if (!optional.isPresent()) {
            return;
        }
        String[] codeTranslation = optional.get().split(",");
        String value = String.valueOf(getter(pojo, codeTranslation[1]).invoke(pojo));
        if (StringUtils.isBlank(value)) {
            jgen.writeStringField(writer.getName() + "_name", "");
            return;
        }
        Code codeObject = DatabaseCache.getCodeBySortCodeAndCode(codeTranslation[0], value);
        String codeName = codeObject == null ? value : codeObject.getName();
        jgen.writeStringField(writer.getName() + "_name", codeName);
    }

    /**
     * 是否序列化
     *
     * @param type
     * @param name
     * @return
     */
    private boolean isSerialize(Class<?> type, String name) {
        Set<String> includeFields = includeMap.get(type);
        Set<String> excludeFields = excludeMap.get(type);
        if (includeFields != null && includeFields.contains(name)) {
            return true;
        } else if (excludeFields != null && !excludeFields.contains(name)) {
            return true;
        } else if (includeFields == null && excludeFields == null) {
            return true;
        }
        return false;
    }
}