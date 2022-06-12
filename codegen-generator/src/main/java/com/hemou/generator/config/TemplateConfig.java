package com.hemou.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TemplateConfig {

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
