package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.TemplateInfo;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.utils.GenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            TemplateConfig templateConfig = configBuilder.getTemplateConfig();
            List<TemplateInfo> templateList = templateConfig.getTemplateList();
            List<TableInfo> infoList = configBuilder.getTableInfoList();

            Map<String, Object> commonMap = GenUtils.getCommonObjectMap(configBuilder);
            for (TemplateInfo template : templateList) {
                Map<String, Object> objectMap = new HashMap<>(commonMap);
                if (infoList != null && infoList.size() > 0) { // 数据源数据不为空
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
