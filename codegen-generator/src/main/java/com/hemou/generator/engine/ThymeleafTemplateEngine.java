package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.Map;

public class ThymeleafTemplateEngine extends AbstractTemplateEngine {

    private TemplateEngine templateEngine;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        templateEngine = new TemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        templateEngine.setTemplateResolver(resolver);
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, TemplateConfig templateConfig) throws Exception {
        Context context = new Context();
        context.setVariables(objectMap);
        return templateEngine.process(templateConfig.getTemplateContent(), context);
    }
}
