package com.hemou.generator.config.builder;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.InjectionConfig;
import com.hemou.generator.config.TemplateConfig;

import java.util.List;
import java.util.Map;

public class ConfigBuilder {

    /**
     * 数据源
     */
    private List<Map<String, Object>> infoList;

    /**
     * 模板列表
     */
    private List<TemplateConfig> templateList;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 注入配置信息
     */
    private InjectionConfig injectionConfig;

    public ConfigBuilder(GlobalConfig globalConfig) {
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }
    }

    public List<Map<String, Object>> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Map<String, Object>> infoList) {
        this.infoList = infoList;
    }

    public List<TemplateConfig> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<TemplateConfig> templateList) {
        this.templateList = templateList;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public void setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
    }
}
