package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateInfo;
import com.hemou.generator.config.builder.ConfigBuilder;
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
    public String writer(Map<String, Object> objectMap, TemplateInfo templateInfo) throws Exception {
        Context context = new Context();
        context.setVariables(objectMap);
        return templateEngine.process(templateInfo.getTemplateContent(), context);
    }
}
