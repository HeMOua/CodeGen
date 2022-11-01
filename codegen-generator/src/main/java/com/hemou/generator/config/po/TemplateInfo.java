package com.hemou.generator.config.po;

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
     * 模板内容
     */
    private String content;

    /**
     * 模板文件路径
     */
    private String filePath;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 只生成一次
     */
    private boolean onlyOnce;
}
