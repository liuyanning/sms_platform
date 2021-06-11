package com.hero.wireless.sharding.algorithm;

import com.google.common.base.Preconditions;
import org.apache.shardingsphere.infra.config.exception.ShardingSphereConfigurationException;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version V3.0.0
 * @description: 按天分片规则
 * @author: 刘彦宁
 * @date: 2021年01月19日10:24
 **/
public class DateShardingAlgorithm implements StandardShardingAlgorithm<Date> {

    private static final String ALGORITHM_EXPRESSION_KEY = "table-prefix";
    private static final String DATE_TIME_PATTERN_KEY = "datetime-pattern";
    private static final String SHARDING_SUFFIX_FORMAT_KEY = "sharding-suffix-pattern";

    private Properties props = new Properties();

    private String prefix;

    private DateTimeFormatter dateTimeFormatter;

    private int dateTimePatternLength;

    private DateTimeFormatter tableSuffixPattern;

    private Map<String, Boolean> existTable = new ConcurrentHashMap<>();


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        String shard = preciseShardingValue.getValue().toString();
        LocalDateTime localDateTime = parseDateTime(shard);
        return getMatchedTables(localDateTime, collection);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        boolean hasStartTime = rangeShardingValue.getValueRange().hasLowerBound();
        boolean hasEndTime = rangeShardingValue.getValueRange().hasUpperBound();
        if (!hasStartTime || !hasEndTime) {
            return collection;
        }
        LocalDateTime startTime = parseDateTime(rangeShardingValue.getValueRange().lowerEndpoint().toString());
        LocalDateTime endTime = parseDateTime(rangeShardingValue.getValueRange().upperEndpoint().toString());
        LocalDateTime calculateTime = startTime;
        Set<String> result = new HashSet<>();
        while (!calculateTime.isAfter(endTime)) {
            result.add(getMatchedTables(calculateTime, collection));
            calculateTime = calculateTime.plus(1, ChronoUnit.DAYS);
        }
        result.add(getMatchedTables(endTime, collection));

        return result;
    }

    private LocalDateTime parseDateTime(final String value) {
        return LocalDateTime.parse(value.substring(0, dateTimePatternLength), dateTimeFormatter);
    }

    @Override
    public void init() {
        Preconditions.checkArgument(props.containsKey(ALGORITHM_EXPRESSION_KEY), "% can not be null.", ALGORITHM_EXPRESSION_KEY);
        this.prefix = props.getProperty(ALGORITHM_EXPRESSION_KEY);

        String dateTimePattern = getDateTimePattern();
        dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        dateTimePatternLength = dateTimePattern.length();
        tableSuffixPattern = getTableSuffixPattern();
    }

    @Override
    public String getType() {
        return "CLASS_BASED";
    }

    @Override
    public Properties getProps() {
        return props;
    }

    @Override
    public void setProps(Properties properties) {
        this.props = properties;
    }

    private String getDateTimePattern() {
        Preconditions.checkArgument(props.containsKey(DATE_TIME_PATTERN_KEY), "% can not be null.", DATE_TIME_PATTERN_KEY);
        return props.getProperty(DATE_TIME_PATTERN_KEY);
    }

    private DateTimeFormatter getTableSuffixPattern() {
        Preconditions.checkArgument(props.containsKey(SHARDING_SUFFIX_FORMAT_KEY), "% can not be null.", SHARDING_SUFFIX_FORMAT_KEY);
        return DateTimeFormatter.ofPattern(props.getProperty(SHARDING_SUFFIX_FORMAT_KEY));
    }

    private LocalDateTime getDateTime(final String dateTimeKey, final String dateTimeValue, final String dateTimePattern) {
        try {
            return LocalDateTime.parse(dateTimeValue, dateTimeFormatter);
        } catch (final DateTimeParseException ex) {
            throw new ShardingSphereConfigurationException("Invalid %s, datetime pattern should be `%s`, value is `%s`", dateTimeKey, dateTimePattern, dateTimeValue);
        }
    }

    private String getMatchedTables(final LocalDateTime dateTime, Collection<String> collection) {
        String tableSuffix = dateTime.format(tableSuffixPattern);
        String actualTable = prefix + tableSuffix;
        Boolean result = existTable.putIfAbsent(actualTable, true);
        if (result == null) {
            collection.add(actualTable);
        }
//        System.out.println("表名称：" + actualTable);
        return actualTable;
    }

//    public static void main(String[] args) {
//        String value = "2021-01-19 00:00:00.000";
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime parse = LocalDateTime.parse(value.substring(0, 19), dateTimeFormatter);
//        System.out.println(parse.format(DateTimeFormatter.ofPattern("MMdd")));
//    }
}
