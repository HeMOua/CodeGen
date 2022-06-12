package com.hemou.generator.engine;

import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
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
        super.init(configBuilder);
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
    public String writer(Map<String, Object> objectMap, TemplateConfig templateConfig) throws Exception {
        VelocityContext context = new VelocityContext();
        if (objectMap != null) {
            objectMap.forEach(context::put);
        }
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, "Velocity Code Generate", templateConfig.getTemplateContent());
        return writer.toString();
    }
}
