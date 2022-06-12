package com.hemou.generator.engine;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.InjectionConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

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
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 批量代码生成
     */
    public AbstractTemplateEngine batchOutput() {
        resultMap = new HashMap<>();
        try {
            List<Map<String, Object>> infoList = getConfigBuilder().getInfoList();
            Map<String, Object> commonMap = getCommonObjectMap();
            for (Map<String, Object> objectMap : infoList) {
                objectMap.putAll(commonMap);
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                }
                List<TemplateConfig> templateList = getConfigBuilder().getTemplateList();
                if (!CollectionUtils.isEmpty(templateList)) {
                    for (TemplateConfig tc : templateList) {
                        resultMap.put(tc.getFilePath(), writer(objectMap, tc.getTemplateContent()));
                    }
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
     * @param templateContent 模板文件
     */
    public abstract String writer(Map<String, Object> objectMap, String templateContent) throws Exception;

    /**
     * 获取公共 Map 信息
     */
    public Map<String, Object> getCommonObjectMap() {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        objectMap.put("config", config);

        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("project", getProjectInfoMap(globalConfig));
        objectMap.put("time", getTimeInfoMap());

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
        return objectMap;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }
}
