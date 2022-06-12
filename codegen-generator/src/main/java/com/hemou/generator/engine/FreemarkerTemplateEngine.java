package com.hemou.generator.engine;

import com.hemou.generator.config.builder.ConfigBuilder;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.nio.charset.StandardCharsets;
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
        configuration.setTemplateLoader(new StringTemplateLoader());
        return this;
    }

    @Override
    public String writer(Map<String, Object> objectMap, String templateContent) throws Exception {
        return null;
    }
}
