package com.hemou.generator.config;

import com.hemou.generator.config.builder.ConfigBuilder;

import java.util.Map;

public abstract class InjectionConfig {

    /**
     * 全局配置
     */
    private ConfigBuilder config;

    /**
     * 自定义返回配置 Map 对象
     */
    private Map<String, Object> map;

    /**
     * 注入自定义 Map 对象
     */
    public abstract void initMap();

    /**
     * 模板待渲染 Object Map 预处理<br>
     * 方法： getObjectMap 结果处理
     */
    public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
        return objectMap;
    }

    public ConfigBuilder getConfig() {
        return config;
    }

    public void setConfig(ConfigBuilder config) {
        this.config = config;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
