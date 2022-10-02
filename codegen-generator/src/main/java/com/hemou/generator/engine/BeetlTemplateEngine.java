package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateInfo;
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
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            groupTemplate = new GroupTemplate(new StringTemplateResourceLoader(), cfg);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, TemplateInfo templateInfo) throws Exception {
        Template template = groupTemplate.getTemplate(templateInfo.getTemplateContent());
        StringWriter writer = new StringWriter();
        template.binding(objectMap);
        template.renderTo(writer);
        return writer.toString();
    }
}
