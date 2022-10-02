package com.hemou.generator.engine;

import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
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

    private StringTemplateLoader templateLoader;

    @Override
    public FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        // 默认编码
        configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
        // 异常处理策略
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 全局模板加载器
        templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        // 加载字符串模板
        List<TemplateInfo> templateList = configBuilder.getTemplateConfig().getTemplateList();
        for (TemplateInfo tc : templateList) {
            templateLoader.putTemplate(tc.getIdentity(), tc.getContent());
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
        // 更新字符串模板
        templateLoader.putTemplate("stringTemplate", templateContent);
        // 获取模板并生成
        Template template = configuration.getTemplate("stringTemplate");
        StringWriter writer = new StringWriter();
        template.process(objectMap, writer);
        return writer.toString();
    }
}
