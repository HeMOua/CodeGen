package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class FreemarkerTemplateEngine extends AbstractTemplateEngine {

    private Configuration configuration;

    @Override
    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 默认编码
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 异常处理策略
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 全局模板加载器
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        // 加载字符串模板
        List<TemplateConfig> templateList = configBuilder.getInjectionConfig().getTemplateList();
        for (TemplateConfig tc : templateList) {
            templateLoader.putTemplate(tc.getIdentity(), tc.getTemplateContent());
        }
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, TemplateConfig templateConfig) throws Exception {
        Template template = configuration.getTemplate(templateConfig.getIdentity());
        StringWriter writer = new StringWriter();
        template.process(objectMap, writer);
        return writer.toString();
    }
}
