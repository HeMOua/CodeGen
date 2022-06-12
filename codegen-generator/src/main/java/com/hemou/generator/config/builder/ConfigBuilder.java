package com.hemou.generator.config.builder;

import com.hemou.generator.config.*;
import com.hemou.generator.config.rules.DbType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ConfigBuilder {

    /**
     * 数据库配置
     */

    private final DataSourceConfig dataSourceConfig;
    /**
     * SQL连接
     */
    private Connection connection;

    /**
     * SQL语句类型
     */
    private IDbQuery dbQuery;

    /**
     * 数据信息
     */
    private List<Map<String, Object>> infoList;

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 注入配置信息
     */
    private InjectionConfig injectionConfig;

    /**
     * 是否支持注释
     */
    private boolean commentSupported;


    public ConfigBuilder(GlobalConfig globalConfig, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig) {
        // 全局配置
        if (null == globalConfig) {
            this.globalConfig = new GlobalConfig();
        } else {
            this.globalConfig = globalConfig;
        }
        // 策略配置
        if (null == strategyConfig) {
            this.strategyConfig = new StrategyConfig();
        } else {
            this.strategyConfig = strategyConfig;
        }
        // 数据源配置
        this.dataSourceConfig = dataSourceConfig;
        if (null != dataSourceConfig) {
            handlerDataSource(dataSourceConfig);
            handlerStrategy(this.strategyConfig);
        }
    }

    /**
     * 处理数据源配置
     *
     * @param config DataSourceConfig
     */
    private void handlerDataSource(DataSourceConfig config) {
        connection = config.getConn();
        dbQuery = config.getDbQuery();
    }

    /**
     * 处理数据库表 加载数据库表、列、注释相关数据集
     *
     * @param config StrategyConfig
     */
    private void handlerStrategy(StrategyConfig config) {
        commentSupported = config.isCommentSupported();
        if (commentSupported) {
            //SQLITE 数据库不支持注释获取
            commentSupported = !dataSourceConfig.getDbType().equals(DbType.SQLITE);
        }
        infoList = getInfo(config);
    }

    private List<Map<String, Object>> getInfo(StrategyConfig config) {
        List<Map<String, Object>> infoList = new ArrayList<>();
        return infoList;
    }
}
