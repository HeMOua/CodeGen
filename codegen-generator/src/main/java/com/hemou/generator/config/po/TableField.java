package com.hemou.generator.config.po;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.rules.IColumnType;
import com.hemou.generator.config.rules.NamingStrategy;
import com.hemou.generator.jdbc.DatabaseMetaDataWrapper;
import com.hemou.generator.jdbc.JdbcType;
import com.hemou.generator.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class TableField {

    private boolean convert;
    private boolean keyFlag;
    /**
     * 主键是否为自增类型
     */
    private boolean keyIdentityFlag;
    private String name;
    private String type;
    private String propertyName;
    private IColumnType columnType;
    private String comment;
    private String fill;

    /**
     * 自定义查询字段列表
     */
    private Map<String, Object> customMap;

    /**
     * 字段元数据信息
     */
    private MetaInfo metaInfo;

    /**
     * 数据库字段（关键字含转义符号）
     */
    private String columnName;

    private final StrategyConfig strategyConfig;

    private final DataSourceConfig dataSourceConfig;

    private final GlobalConfig globalConfig;

    public TableField(ConfigBuilder configBuilder, String name) {
        this.name = name;
        this.columnName = name;
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
    }

    /**
     * 设置主键
     *
     * @param autoIncrement 自增标识
     * @return this
     */
    public TableField primaryKey(boolean autoIncrement) {
        this.keyFlag = true;
        this.keyIdentityFlag = autoIncrement;
        return this;
    }

    /**
     * 设置属性名称
     *
     * @param propertyName 属性名
     * @param columnType   字段类型
     * @return this
     */
    public TableField setPropertyName(String propertyName, IColumnType columnType) {
        this.columnType = columnType;
        if (strategyConfig.isBooleanColumnRemoveIsPrefix()
                && "boolean".equalsIgnoreCase(this.getPropertyType()) && propertyName.startsWith("is")) {
            this.convert = true;
            this.propertyName = StringUtils.removePrefixAfterPrefixToLower(propertyName, 2);
            return this;
        }
        // 下划线转驼峰策略
        if (NamingStrategy.underline_to_camel.equals(this.strategyConfig.getColumnNaming())) {
            this.convert = !propertyName.equalsIgnoreCase(NamingStrategy.underlineToCamel(this.columnName));
        }
        // 原样输出策略
        if (NamingStrategy.no_change.equals(this.strategyConfig.getColumnNaming())) {
            this.convert = !propertyName.equalsIgnoreCase(this.columnName);
        }
        if (strategyConfig.isTableFieldAnnotationEnable()) {
            this.convert = true;
        }
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyType() {
        if (null != columnType) {
            return columnType.getType();
        }
        return null;
    }

    /**
     * 元数据信息
     */
    public static class MetaInfo {

        private int length;

        private boolean nullable;

        private String remarks;

        private String defaultValue;

        private int scale;

        private JdbcType jdbcType;

        public MetaInfo(DatabaseMetaDataWrapper.ColumnsInfo columnsInfo) {
            if (columnsInfo != null) {
                this.length = columnsInfo.getLength();
                this.nullable = columnsInfo.isNullable();
                this.remarks = columnsInfo.getRemarks();
                this.defaultValue = columnsInfo.getDefaultValue();
                this.scale = columnsInfo.getScale();
                this.jdbcType = columnsInfo.getJdbcType();
            }
        }

        public int getLength() {
            return length;
        }

        public boolean isNullable() {
            return nullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public int getScale() {
            return scale;
        }

        public JdbcType getJdbcType() {
            return jdbcType;
        }

        @Override
        public String toString() {
            return "MetaInfo{" +
                    "length=" + length +
                    ", nullable=" + nullable +
                    ", remarks='" + remarks + '\'' +
                    ", defaultValue='" + defaultValue + '\'' +
                    ", scale=" + scale +
                    ", jdbcType=" + jdbcType +
                    '}';
        }
    }
}
