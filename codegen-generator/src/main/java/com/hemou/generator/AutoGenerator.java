package com.hemou.generator;

import com.hemou.generator.config.*;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.po.TemplateInfo;
import com.hemou.generator.config.rules.EngineType;
import com.hemou.generator.engine.AbstractTemplateEngine;
import com.hemou.generator.utils.GenUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class AutoGenerator {

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
     * 全局相关配置
     */
    private GlobalConfig globalConfig;

    /**
     * 引擎集
     */
    private Map<String, AbstractTemplateEngine> engineMap = new HashMap<>();

    /**
     * 生成代码
     */
    public List<ResultInfo> execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ConfigBuilder(globalConfig, dataSource, strategy, templateConfig);
        }
        // 模板引擎初始化执行文件输出
        List<ResultInfo> resultList = batchOutput();
        logger.debug("==========================文件生成完成！！！==========================");
        return resultList;
    }

    private List<ResultInfo> batchOutput() {
        List<ResultInfo> resultList = new ArrayList<>();
        try {
            TemplateConfig templateConfig = config.getTemplateConfig();
            List<TemplateInfo> templateList = templateConfig.getTemplateList();
            List<TableInfo> infoList = config.getTableInfoList();

            Map<String, Object> commonMap = GenUtils.getCommonObjectMap(config);
            for (TemplateInfo template : templateList) {
                AbstractTemplateEngine engine = getEngine(template);
                Map<String, Object> objectMap = new HashMap<>(commonMap);
                if (infoList != null && infoList.size() > 0) { // 数据源数据不为空
                    for (TableInfo tableInfo : infoList) {
                        templateConfig.beforeRender(tableInfo, objectMap);
                        resultList.add(engine.writer(objectMap, template));
                    }
                } else { // 若数据源数据为空
                    templateConfig.beforeRender(null, objectMap);
                    resultList.add(engine.writer(objectMap, template));
                }
            }
        } catch (Exception e) {
            logger.error("无法渲染模板，请检查配置信息！", e);
        }
        return resultList;
    }

    private AbstractTemplateEngine getEngine(TemplateInfo templateInfo) {
        EngineType engineType = templateInfo.getEngineType();
        AbstractTemplateEngine engine = engineMap.get(engineType.getEngineName());
        if (ObjectUtils.isEmpty(engine)) {
            try {
                Class<?> clazz = Class.forName(engineType.getClassName());
                engine = (AbstractTemplateEngine) clazz.getConstructor().newInstance();
                engine.init(config);
                engineMap.put(engineType.getEngineName(), engine);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return engine;
    }
}
