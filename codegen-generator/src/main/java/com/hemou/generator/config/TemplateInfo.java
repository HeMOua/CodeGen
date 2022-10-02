package com.hemou.generator.config;

import com.hemou.generator.config.rules.EngineType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TemplateInfo {

    /**
     * 模型引擎类型
     */
    private EngineType engineType;

    /**
     * 模板标识
     */
    private String identity;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板文件路径
     */
    private String filePath;
}
