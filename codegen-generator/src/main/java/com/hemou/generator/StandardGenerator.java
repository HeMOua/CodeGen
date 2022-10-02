package com.hemou.generator;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.engine.AbstractTemplateEngine;
import com.hemou.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Data
@Accessors(chain = true)
public class StandardGenerator {

    private static final Logger logger = LoggerFactory.getLogger(StandardGenerator.class);

    /**
     * 配置信息
     */
    protected ConfigBuilder config;

    /**
     * 注入配置
     */
    protected TemplateConfig templateConfig;

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;

    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;

    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;

    /**
     * 模板引擎
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 生成代码
     */
    public Map<String, String> execute() {
        return this.execute(null);
    }

    /**
     * 生成代码
     */
    public Map<String, String> execute(AbstractTemplateEngine templateEngine) {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(globalConfig, dataSource, strategy);
        }
        if (null == templateEngine) {
            // 默认采用 Velocity 引擎
            templateEngine = new FreemarkerTemplateEngine();
        }
        templateEngine.setConfigBuilder(config);
        // 模板引擎初始化执行文件输出
        Map<String, String> resultMap = templateEngine.init(config).batchOutput().getResultMap();
        logger.debug("==========================文件生成完成！！！==========================");
        return resultMap;
    }
}
