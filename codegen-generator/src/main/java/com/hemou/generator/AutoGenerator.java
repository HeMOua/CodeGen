package com.hemou.generator;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.InjectionConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.engine.AbstractTemplateEngine;
import com.hemou.generator.engine.FreemarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AutoGenerator.class);

    /**
     * 配置信息
     */
    protected ConfigBuilder config;

    /**
     * 注入配置
     */
    protected InjectionConfig injectionConfig;

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
    public void execute() {
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // 默认采用 Velocity 引擎
            templateEngine = new FreemarkerTemplateEngine();
        }
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(config)).batchOutput();
    }

    /**
     * 预处理配置
     *
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder config) {
        /*
         * 注入自定义配置
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        return config;
    }

    public ConfigBuilder getConfig() {
        return config;
    }

    public void setConfig(ConfigBuilder config) {
        this.config = config;
    }

    public InjectionConfig getInjectionConfig() {
        return injectionConfig;
    }

    public void setInjectionConfig(InjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    public AbstractTemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
}
