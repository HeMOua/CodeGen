package com.hemou.generator.config.po;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.rules.IColumnType;
import com.hemou.generator.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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


    /** 使用的模板（crud单表操作 tree树表操作 sub主子表操作） */
    private String type;

    /**
     * 包导入信息
     */
    private final Set<String> importPackages = new TreeSet<>();

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
    private String className;

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
     */
    public TableInfo(ConfigBuilder configBuilder, String name) {
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
        this.name = name;
    }

    /**
     * 添加字段
     *
     * @param field 字段
     */
    public void addField(TableField field) {
        if (strategyConfig.matchIgnoreColumns(field.getColumnName())) {
            // 忽略字段不在处理
            return;
        } else if (strategyConfig.matchSuperEntityColumns(field.getColumnName())) {
            this.commonFields.add(field);
        } else {
            this.fields.add(field);
        }
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        if (StringUtils.isBlank(fieldNames)) {
            this.fieldNames = this.fields.stream().map(TableField::getColumnName).collect(Collectors.joining(", "));
        }
        return this.fieldNames;
    }

    /**
     * 处理表信息(文件名与导包)
     */
    public void processTable() {
        String entityName = strategyConfig.getNameConvert().entityNameConvert(this);
        this.setClassName(entityName);
        this.importPackage();
    }

    /**
     * 导包处理
     */
    public void importPackage() {
        if (strategyConfig.isSerialVersionUID()) {
            this.importPackages.add(Serializable.class.getCanonicalName());
        }
        this.fields.forEach(field -> {
            IColumnType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getPkg()) {
                importPackages.add(columnType.getPkg());
            }
        });
    }
}
