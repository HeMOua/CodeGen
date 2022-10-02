package com.hemou.generator.engine;

import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AbstractTemplateEngineTest {

    @Test
    public void getTimeInfoMap() {
        Map<String, Object> objectMap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        objectMap.put("yyyy", year);
        objectMap.put("yy", year % 100);
        objectMap.put("MM", calendar.get(Calendar.MONTH) + 1);
        objectMap.put("dd", calendar.get(Calendar.DATE));
        objectMap.put("weekOfMonth", calendar.get(Calendar.WEEK_OF_MONTH));
        objectMap.put("weekOfYear", calendar.get(Calendar.WEEK_OF_YEAR));
        objectMap.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));
        objectMap.put("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
        objectMap.put("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        objectMap.put("HH", calendar.get(Calendar.HOUR_OF_DAY));
        objectMap.put("hh", calendar.get(Calendar.HOUR));
        objectMap.put("mm", calendar.get(Calendar.MINUTE));
        objectMap.put("ss", calendar.get(Calendar.SECOND));
        objectMap.put("sss", calendar.get(Calendar.MILLISECOND));
        System.out.println(objectMap);
        for (String k : objectMap.keySet()) {
            System.out.println(objectMap.get(k).getClass());
        }
    }
}