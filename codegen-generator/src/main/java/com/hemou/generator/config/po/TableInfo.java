package com.hemou.generator.config.po;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@Accessors(chain = true)
public class TableInfo {


    /**
     * 策略配置
     */
    private final StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private final GlobalConfig globalConfig;

    /**
     * 包导入信息
     */
    private final Set<String> importPackages = new TreeSet<>();

    /**
     * 是否转换
     */
    private boolean convert;

    /**
     * 表名称
     */
    private String name;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * 表字段
     */
    private final List<TableField> fields = new ArrayList<>();

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

    /**
     * 公共字段
     */
    private final List<TableField> commonFields = new ArrayList<>();

    /**
     * 字段名称集
     */
    private String fieldNames;

    /**
     * 构造方法
     *
     * @param configBuilder 配置构建
     * @param name          表名
     * @since 3.5.0
     */
    public TableInfo(ConfigBuilder configBuilder, String name) {
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
        this.name = name;
    }


}
