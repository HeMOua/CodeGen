package com.hemou.generator.config;

import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.po.TemplateInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class TemplateConfig {

    private TemplateConfig() {}

    /**
     * 渲染前消费者
     */
    private BiConsumer<TableInfo, Map<String, Object>> beforeRenderConsumer;

    /**
     * 自定义返回配置 Map 对象
     */
    private Map<String, Object> customMap = new HashMap<>();

    /**
     * 模板列表
     */
    private List<TemplateInfo> templateList = new ArrayList<>();

    public void beforeRender(TableInfo tableInfo, Map<String, Object> objectMap) {
        if (!customMap.isEmpty()) {
            objectMap.putAll(customMap);
        }
        if (null != beforeRenderConsumer) {
            beforeRenderConsumer.accept(tableInfo, objectMap);
        }
    }

    public Map<String, Object> getCustomMap() {
        return customMap;
    }

    public List<TemplateInfo> getTemplateList() {
        return templateList;
    }

    public static class Builder implements IConfigBuilder<TemplateConfig> {

        private final TemplateConfig templateConfig;

        public Builder() {
            this.templateConfig = new TemplateConfig();
        }

        public Builder beforeRenderConsumer(BiConsumer<TableInfo, Map<String, Object>> beforeRenderConsumer) {
            this.templateConfig.beforeRenderConsumer = beforeRenderConsumer;
            return this;
        }

        public Builder customMap(Map<String, Object> customMap) {
            this.templateConfig.customMap = customMap;
            return this;
        }

        public Builder templateList(List<TemplateInfo> templateList) {
            this.templateConfig.templateList = templateList;
            return this;
        }

        @Override
        public TemplateConfig build() {
            return this.templateConfig;
        }
    }
}
