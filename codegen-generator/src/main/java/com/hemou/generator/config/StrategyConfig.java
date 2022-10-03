package com.hemou.generator.config;

import com.hemou.generator.config.function.ConverterFileName;
import com.hemou.generator.config.rules.IdType;
import com.hemou.generator.config.rules.NamingStrategy;
import com.hemou.generator.utils.StringUtils;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 策略配置项
 */
@Getter
public class StrategyConfig {

    private StrategyConfig() {}

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType;

    /**
     * 是否大写命名
     */
    private boolean capitalMode;

    /**
     * 是否跳过视图（默认 false）
     */
    private boolean skipView;

    /**
     * 过滤表前缀
     * example: addTablePrefix("t_")
     * result: t_simple -> Simple
     */
    private final Set<String> tablePrefix = new HashSet<>();

    /**
     * 过滤表后缀
     * example: addTableSuffix("_0")
     * result: t_simple_0 -> Simple
     */
    private final Set<String> tableSuffix = new HashSet<>();

    /**
     * 过滤字段前缀
     * example: addFieldPrefix("is_")
     * result: is_deleted -> deleted
     */
    private final Set<String> fieldPrefix = new HashSet<>();

    /**
     * 过滤字段后缀
     * example: addFieldSuffix("_flag")
     * result: deleted_flag -> deleted
     */
    private final Set<String> fieldSuffix = new HashSet<>();

    /**
     * 需要包含的表名，允许正则表达式（与exclude二选一配置）<br/>
     */
    private final Set<String> include = new HashSet<>();

    /**
     * 需要排除的表名，允许正则表达式<br/>
     */
    private final Set<String> exclude = new HashSet<>();

    /**
     * 自定义基础的Entity类，公共字段
     */
    private final Set<String> superEntityColumns = new HashSet<>();

    /**
     * 自定义忽略字段
     */
    private final Set<String> ignoreColumns = new HashSet<>();

    /**
     * 启用sql过滤，语法不能支持使用sql过滤表的话，可以考虑关闭此开关.
     */
    private boolean enableSqlFilter = true;

    /**
     * 启用 schema 默认 false
     */
    private boolean enableSchema;

    /**
     * 是否启用注释
     */
    private boolean commentSupported = true;

    /**
     * 名称转换
     */
    private INameConvert nameConvert;

    /**
     * 数据库表映射到实体的命名策略，默认下划线转驼峰命名
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定则按照 naming 执行</p>
     */
    private NamingStrategy columnNaming = null;

    /**
     * Boolean类型字段是否移除is前缀（默认 false）<br>
     * 比如 : 数据库字段名称 : 'is_xxx',类型为 : tinyint. 在映射实体的时候则会去掉is,在实体类中映射最终结果为 xxx
     */
    private boolean booleanColumnRemoveIsPrefix;

    /**
     * 是否生成实体时，生成字段注解（默认 false）
     */
    private boolean tableFieldAnnotationEnable;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID;

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    /**
     * 验证配置项
     */
    public void validate() {
        boolean isInclude = this.getInclude().size() > 0;
        boolean isExclude = this.getExclude().size() > 0;
        if (isInclude && isExclude) {
            throw new IllegalArgumentException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        }
    }

    public NamingStrategy getColumnNaming() {
        // 未指定以 naming 策略为准
        return Optional.ofNullable(columnNaming).orElse(naming);
    }

    /**
     * 大写命名、字段符合大写字母数字下划线命名
     *
     * @param word 待判断字符串
     */
    public boolean isCapitalModeNaming(String word) {
        return capitalMode && StringUtils.isCapitalMode(word);
    }

    /**
     * 表名称匹配过滤表前缀
     *
     * @param tableName 表名称
     */
    public boolean startsWithTablePrefix(String tableName) {
        return this.tablePrefix.stream().anyMatch(tableName::startsWith);
    }

    /**
     * 包含表名匹配
     *
     * @param tableName 表名
     * @return 是否匹配
     */
    public boolean matchIncludeTable(String tableName) {
        return matchTable(tableName, this.getInclude());
    }

    /**
     * 排除表名匹配
     *
     * @param tableName 表名
     * @return 是否匹配
     */
    public boolean matchExcludeTable(String tableName) {
        return matchTable(tableName, this.getExclude());
    }

    /**
     * 表名匹配
     *
     * @param tableName   表名
     * @param matchTables 匹配集合
     * @return 是否匹配
     */
    private boolean matchTable(String tableName, Set<String> matchTables) {
        return matchTables.stream().anyMatch(t -> tableNameMatches(t, tableName));
    }

    /**
     * 表名匹配
     *
     * @param matchTableName 匹配表名
     * @param dbTableName    数据库表名
     * @return 是否匹配
     */
    private boolean tableNameMatches(String matchTableName, String dbTableName) {
        return matchTableName.equalsIgnoreCase(dbTableName) || StringUtils.matches(matchTableName, dbTableName);
    }

    /**
     * 匹配父类字段(忽略大小写)
     *
     * @param fieldName 字段名
     * @return 是否匹配
     */
    public boolean matchSuperEntityColumns(String fieldName) {
        // 公共字段判断忽略大小写【 部分数据库大小写不敏感 】
        return superEntityColumns.stream().anyMatch(e -> e.equalsIgnoreCase(fieldName));
    }

    /**
     * 匹配忽略字段(忽略大小写)
     *
     * @param fieldName 字段名
     * @return 是否匹配
     */
    public boolean matchIgnoreColumns(String fieldName) {
        return ignoreColumns.stream().anyMatch(e -> e.equalsIgnoreCase(fieldName));
    }

    public static class Builder implements IConfigBuilder<StrategyConfig> {

        private final StrategyConfig strategyConfig;

        public Builder() {
            this.strategyConfig = new StrategyConfig();
            this.strategyConfig.nameConvert = new INameConvert.DefaultNameConvert(strategyConfig);
        }

        public Builder idType(IdType idType) {
            this.strategyConfig.idType = idType;
            return this;
        }

        public Builder enableCapitalMode() {
            this.strategyConfig.capitalMode = true;
            return this;
        }

        public Builder enableSkipView() {
            this.strategyConfig.skipView = true;
            return this;
        }

        public Builder enableSqlFilter() {
            this.strategyConfig.enableSqlFilter = true;
            return this;
        }

        public Builder enableSchema() {
            this.strategyConfig.enableSchema = true;
            return this;
        }

        public Builder enableCommentSupported() {
            this.strategyConfig.commentSupported = true;
            return this;
        }

        /**
         * 数据库表映射到实体的命名策略
         */
        public Builder naming(NamingStrategy namingStrategy) {
            this.strategyConfig.naming = namingStrategy;
            return this;
        }

        /**
         * 数据库表字段映射到实体的命名策略
         */
        public Builder columnNaming(NamingStrategy namingStrategy) {
            this.strategyConfig.columnNaming = namingStrategy;
            return this;
        }

        public Builder enableBooleanColumnRemoveIsPrefix() {
            this.strategyConfig.booleanColumnRemoveIsPrefix = true;
            return this;
        }

        public Builder enableTableFieldAnnotationEnable() {
            this.strategyConfig.tableFieldAnnotationEnable = true;
            return this;
        }

        public Builder enableSerialVersionUID() {
            this.strategyConfig.serialVersionUID = true;
            return this;
        }

        public Builder convertFileName(ConverterFileName converter) {
            this.strategyConfig.converterFileName = converter;
            return this;
        }

        @Override
        public StrategyConfig build() {
            this.strategyConfig.validate();
            return strategyConfig;
        }
    }
}
