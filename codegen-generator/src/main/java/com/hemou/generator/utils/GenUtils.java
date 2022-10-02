package com.hemou.generator.utils;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.builder.ConfigBuilder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.hemou.generator.utils.DateUtils.*;
import static com.hemou.generator.utils.DateUtils.formatSimpleTime;

public class GenUtils {

    /**
     * 获取公共 Map 信息
     */
    public static Map<String, Object> getCommonObjectMap(ConfigBuilder config) {
        Map<String, Object> objectMap = new HashMap<>(30);
        objectMap.put("_config", config);

        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("_author", globalConfig.getAuthor());
        objectMap.put("_email", globalConfig.getEmail());
        objectMap.put("_mobile", globalConfig.getMobile());
        objectMap.put("_project", getProjectInfoMap(globalConfig));
        objectMap.put("_time", getTimeInfoMap());

        return objectMap;
    }

    /**
     * 获取项目信息
     */
    private static Map<String, Object> getProjectInfoMap(GlobalConfig globalConfig) {
        Map<String, Object> objectMap = new HashMap<>(3);
        objectMap.put("zhName", globalConfig.getProjectZhName());
        objectMap.put("enName", globalConfig.getProjectEnName());
        objectMap.put("desc", globalConfig.getProjectDesc());
        return objectMap;
    }

    /**
     * 获取时间信息
     */
    private static Map<String, Object> getTimeInfoMap() {
        Map<String, Object> objectMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        objectMap.put("yyyy", formatDateNumber(year, 4));
        objectMap.put("yy", formatDateNumber(year % 100, 2));
        objectMap.put("MM", formatDateNumber(calendar.get(Calendar.MONTH) + 1, 2));
        objectMap.put("dd", formatDateNumber(calendar, Calendar.DATE, 2));
        objectMap.put("weekOfMonth", calendar.get(Calendar.WEEK_OF_MONTH));
        objectMap.put("weekOfYear", calendar.get(Calendar.WEEK_OF_YEAR));
        objectMap.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));
        objectMap.put("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
        objectMap.put("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        objectMap.put("HH", formatDateNumber(calendar, Calendar.HOUR_OF_DAY, 2));
        objectMap.put("hh", formatDateNumber(calendar, Calendar.HOUR, 2));
        objectMap.put("mm", formatDateNumber(calendar, Calendar.MINUTE, 2));
        objectMap.put("ss", formatDateNumber(calendar, Calendar.SECOND, 2));
        objectMap.put("sss", formatDateNumber(calendar, Calendar.MILLISECOND, 3));
        objectMap.put("date", formatDate(calendar));
        objectMap.put("Time", formatTime(calendar));
        objectMap.put("time", formatSimpleTime(calendar));
        return objectMap;
    }
}
