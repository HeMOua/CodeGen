package com.hemou.generator.engine;

import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

public class ThymeleafTemplateEngine extends AbstractTemplateEngine {

    private TemplateEngine templateEngine;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        templateEngine.setTemplateResolver(resolver);
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
        Context context = new Context();
        context.setVariables(objectMap);
        return templateEngine.process(templateContent, context);
    }
}
