package com.hemou.generator.utils;

import cn.hutool.core.util.StrUtil;
import com.hemou.generator.config.ConstVal;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.TableField;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.rules.NamingStrategy;

import java.util.*;

import static com.hemou.generator.utils.DateUtils.*;

public class GenUtils {

    /**
     * 获取公共 Map 信息
     */
    public static Map<String, Object> getCommonObjectMap(ConfigBuilder config) {
        Map<String, Object> objectMap = new HashMap<>(30);
        // 全局配置
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("packageName", globalConfig.getBasePackage());
        objectMap.put("basePath", globalConfig.getBasePath());
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("email", globalConfig.getEmail());
        objectMap.put("mobile", globalConfig.getMobile());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger", globalConfig.isSwagger());
        objectMap.put("_project", getProjectInfoMap(globalConfig));
        objectMap.put("_time", getTimeInfo());
        // 策略配置
        objectMap.put("_strategy", config.getStrategyConfig().renderData());
        // 工具方法
        objectMap.put("_tools", new StringUtils());
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
     * 获取数据表信息
     */
    public static Map<String, Object> getTableInfo(ConfigBuilder config, TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>();
        StrategyConfig strategyConfig = config.getStrategyConfig();
        objectMap.put("type", tableInfo.getType());
        // 启用 schema 处理逻辑
        String schemaName = "";
        if (strategyConfig.isEnableSchema()) {
            // 存在 schemaName 设置拼接 . 组合表名
            schemaName = config.getDataSourceConfig().getSchemaName();
            if (StringUtils.isNotBlank(schemaName)) {
                schemaName += ".";
            }
        }
        objectMap.put("schemaName", schemaName);
        objectMap.put("_table", tableInfo);
        objectMap.put("ClassName", tableInfo.getClassName());
        objectMap.put("className", NamingStrategy.underlineToCamel(tableInfo.getName()));
        objectMap.put("InterfaceName", StrUtil.format(strategyConfig.getInterfaceFormat(), tableInfo.getName()));
        return objectMap;
    }

    /**
     * 获取时间信息
     */
    private static Map<String, Object> getTimeInfo() {
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

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(StrategyConfig strategyConfig, TableField column) {
        String dataType = getDbType(column.getType());
        String columnName = column.getColumnName();
        column.setQueryType(ConstVal.QUERY_EQ);
        if (arraysContains(ConstVal.COLUMNTYPE_STR, dataType) || arraysContains(ConstVal.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getType());
            String htmlType = columnLength >= 500 || arraysContains(ConstVal.COLUMNTYPE_TEXT, dataType) ? ConstVal.HTML_TEXTAREA : ConstVal.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(ConstVal.COLUMNTYPE_TIME, dataType)) {
            column.setHtmlType(ConstVal.HTML_DATETIME);
        } else if (arraysContains(ConstVal.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(ConstVal.HTML_INPUT);
        }
        // 插入字段（默认所有字段都需要插入）
        column.setIsInsert(ConstVal.REQUIRE);
        // 编辑字段
        if (!setsContains(strategyConfig.getColumnNameNotEdit(), columnName) && !column.isKeyFlag()) {
            column.setIsEdit(ConstVal.REQUIRE);
        }
        // 列表字段
        if (!setsContains(strategyConfig.getColumnNameNotList(), columnName) && !column.isKeyFlag()) {
            column.setIsList(ConstVal.REQUIRE);
        }
        // 查询字段
        if (!setsContains(strategyConfig.getColumnNameNotQuery(), columnName) && !column.isKeyFlag()) {
            column.setIsQuery(ConstVal.REQUIRE);
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(ConstVal.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StringUtils.endsWithIgnoreCase(columnName, "status")) {
            column.setHtmlType(ConstVal.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StringUtils.endsWithIgnoreCase(columnName, "type")
                || StringUtils.endsWithIgnoreCase(columnName, "sex")
                || StringUtils.endsWithIgnoreCase(columnName, "gender")) {
            column.setHtmlType(ConstVal.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "image")) {
            column.setHtmlType(ConstVal.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "file")) {
            column.setHtmlType(ConstVal.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StringUtils.endsWithIgnoreCase(columnName, "content")) {
            column.setHtmlType(ConstVal.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }


    /**
     * 校验集合是否包含指定值
     *
     * @param set         集合
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean setsContains(Set<String> set, String targetValue) {
        return set.contains(targetValue);
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }
}
