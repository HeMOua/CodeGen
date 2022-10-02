package com.hemou.generator.engine;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.TemplateInfo;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hemou.generator.utils.DateUtils.*;

public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 生成结果
     */
    private Map<String, String> resultMap;

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init(ConfigBuilder configBuilder);

    /**
     * 批量代码生成
     */
    public AbstractTemplateEngine batchOutput() {
        resultMap = new HashMap<>();
        try {
            ConfigBuilder config = this.getConfigBuilder();
            TemplateConfig templateConfig = config.getTemplateConfig();
            List<TemplateInfo> templateList = templateConfig.getTemplateList();
            List<TableInfo> infoList = config.getTableInfoList();

            Map<String, Object> commonMap = getCommonObjectMap();
            for (TemplateInfo template : templateList) {
                Map<String, Object> objectMap = new HashMap<>(commonMap);
                if (!CollectionUtils.isEmpty(infoList)) { // 数据源数据不为空
                    for (TableInfo infoMap : infoList) {
                        templateConfig.beforeRender(infoMap, objectMap);
                        resultMap.put(template.getFilePath(), writer(objectMap, template));
                    }
                } else { // 若数据源数据为空
                    templateConfig.beforeRender(null, objectMap);
                    resultMap.put(template.getFilePath(), writer(objectMap, template));
                }
            }
        } catch (Exception e) {
            logger.error("无法渲染模板，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 将模板转化成为字符串
     *
     * @param objectMap       渲染对象 MAP 信息
     * @param templateInfo  模板文件
     */
    public abstract String writer(Map<String, Object> objectMap, TemplateInfo templateInfo) throws Exception;

    /**
     * 获取公共 Map 信息
     */
    public Map<String, Object> getCommonObjectMap() {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
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
    private Map<String, Object> getProjectInfoMap(GlobalConfig globalConfig) {
        Map<String, Object> objectMap = new HashMap<>(3);
        objectMap.put("zhName", globalConfig.getProjectZhName());
        objectMap.put("enName", globalConfig.getProjectEnName());
        objectMap.put("desc", globalConfig.getProjectDesc());
        return objectMap;
    }

    /**
     * 获取时间信息
     */
    private Map<String, Object> getTimeInfoMap() {
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
        objectMap.put("time", formatTime(calendar));
        objectMap.put("time_12", formatSimpleTime(calendar));
        return objectMap;
    }

    public void setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }
}
