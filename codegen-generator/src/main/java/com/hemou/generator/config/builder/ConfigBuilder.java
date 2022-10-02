package com.hemou.generator.config.builder;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.querys.IDatabaseQuery;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Data
@Accessors(chain = true)
public class ConfigBuilder {

    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()+\\\\\\[\\]|{};:'\",<.>?]+");

    /**
     * 数据库配置
     */

    private final DataSourceConfig dataSourceConfig;

    /**
     * 数据信息
     */
    private List<TableInfo> tableInfoList = new ArrayList<>();

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 模板配置信息
     */
    private TemplateConfig templateConfig;


    public ConfigBuilder(GlobalConfig globalConfig, DataSourceConfig dataSourceConfig,
                         StrategyConfig strategyConfig, TemplateConfig templateConfig) {
        // 全局配置
        this.globalConfig = Optional.ofNullable(globalConfig).orElseGet(GeneratorBuilder::globalConfig);
        // 策略配置
        this.strategyConfig = Optional.ofNullable(strategyConfig).orElseGet(GeneratorBuilder::strategyConfig);
        // 模板配置
        this.templateConfig = templateConfig;
        // 数据源配置
        this.dataSourceConfig = dataSourceConfig;
    }

    public List<TableInfo> getTableInfoList() {
        if (dataSourceConfig != null && tableInfoList.isEmpty()) {
            List<TableInfo> tableInfos = new IDatabaseQuery.DefaultDatabaseQuery(this).queryTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
        return tableInfoList;
    }

    /**
     * 判断表名是否为正则表名(这表名规范比较随意,只能尽量匹配上特殊符号)
     *
     * @param tableName 表名
     * @return 是否正则
     */
    public static boolean matcherRegTable(String tableName) {
        return REGX.matcher(tableName).find();
    }
}
