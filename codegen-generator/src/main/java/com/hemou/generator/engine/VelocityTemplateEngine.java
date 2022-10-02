package com.hemou.generator.engine;

import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private VelocityEngine velocityEngine;

    @Override
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty("resource.loaders", "string");
            p.setProperty("resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "");
            p.setProperty(Velocity.ENCODING_DEFAULT, StandardCharsets.UTF_8.name());
            p.setProperty(Velocity.INPUT_ENCODING, StandardCharsets.UTF_8.name());
            p.setProperty("resource.loader.unicode", "true");
            velocityEngine = new VelocityEngine(p);
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
        VelocityContext context = new VelocityContext();
        if (objectMap != null) {
            objectMap.forEach(context::put);
        }
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "VelocityCodeGenerate", templateContent);
        return writer.toString();
    }
}
