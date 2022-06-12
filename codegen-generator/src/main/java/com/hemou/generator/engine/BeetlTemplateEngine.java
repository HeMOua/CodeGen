package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class BeetlTemplateEngine extends AbstractTemplateEngine {

    private GroupTemplate groupTemplate;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            groupTemplate = new GroupTemplate(new StringTemplateResourceLoader(), cfg);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, TemplateConfig templateConfig) throws Exception {
        Template template = groupTemplate.getTemplate(templateConfig.getTemplateContent());
        StringWriter writer = new StringWriter();
        template.binding(objectMap);
        template.renderTo(writer);
        return writer.toString();
    }
}
