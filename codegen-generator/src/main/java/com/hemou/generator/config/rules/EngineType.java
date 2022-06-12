package com.hemou.generator.config.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EngineType {

    Beetl("beetl", "com.hemou.generator.engine.BeetlTemplateEngine"),
    FreeMarker("freemarker", "com.hemou.generator.engine.FreemarkerTemplateEngine"),
    Velocity("velocity", "com.hemou.generator.engine.VelocityTemplateEngine"),
    Thymeleaf("thymeleaf", "com.hemou.generator.engine.ThymeleafTemplateEngine");

    private final String engineName;

    private final String className;
}
