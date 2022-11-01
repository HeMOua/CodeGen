package com.hemou.generator.engine;

import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private List<ResultInfo> resultList;

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init(ConfigBuilder configBuilder);

    /**
     * 将模板转化成为字符串
     *
     * @param objectMap       渲染对象 MAP 信息
     * @param templateInfo    模板信息
     */
    public abstract ResultInfo writer(Map<String, Object> objectMap, TemplateInfo templateInfo) throws Exception;

    public void setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public List<ResultInfo> getResultList() {
        return resultList;
    }
}
