package com.hemou.generator.engine;

import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
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
    public ResultInfo writer(Map<String, Object> objectMap, TemplateInfo template) throws Exception {
        return new ResultInfo(
            writer(objectMap, template.getFilePath()),
            writer(objectMap, template.getFileName()),
            writer(objectMap, template.getContent())
        );
    }

    private String writer(Map<String, Object> objectMap, String templateContent) throws Exception {
        Template template = groupTemplate.getTemplate(templateContent);
        StringWriter writer = new StringWriter();
        template.binding(objectMap);
        template.renderTo(writer);
        return writer.toString();
    }
}
