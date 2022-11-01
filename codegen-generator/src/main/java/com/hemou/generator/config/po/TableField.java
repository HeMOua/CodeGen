package com.hemou.generator.config.po;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.rules.IColumnType;
import com.hemou.generator.jdbc.DatabaseMetaDataWrapper;
import com.hemou.generator.jdbc.JdbcType;
import com.hemou.generator.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class TableField {

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

    /** 是否必填（1是） */
    private String isRequired;

    /** 是否为插入字段（1是） */
    private String isInsert;

    /** 是否编辑字段（1是） */
    private String isEdit;

    /** 是否列表字段（1是） */
    private String isList;

    /** 是否查询字段（1是） */
    private String isQuery;

    /** 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围） */
    private String queryType;

    /** 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件） */
    private String htmlType;

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
            this.propertyName = StringUtils.removePrefixAfterPrefixToLower(propertyName, 2);
        } else {
            this.propertyName = propertyName;
        }
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
