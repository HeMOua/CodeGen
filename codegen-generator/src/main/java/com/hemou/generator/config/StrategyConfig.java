package com.hemou.generator.config;

import com.hemou.generator.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 策略配置项
 */
public class StrategyConfig {

    private StrategyConfig() {}

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
     * 验证配置项
     */
    public void validate() {
        boolean isInclude = this.getInclude().size() > 0;
        boolean isExclude = this.getExclude().size() > 0;
        if (isInclude && isExclude) {
            throw new IllegalArgumentException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！");
        }
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
     * @since 3.5.0
     */
    public boolean matchIncludeTable(String tableName) {
        return matchTable(tableName, this.getInclude());
    }

    /**
     * 排除表名匹配
     *
     * @param tableName 表名
     * @return 是否匹配
     * @since 3.5.0
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
     * @since 3.5.0
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

    public boolean isCapitalMode() {
        return capitalMode;
    }

    public boolean isSkipView() {
        return skipView;
    }

    public Set<String> getTablePrefix() {
        return tablePrefix;
    }

    public Set<String> getTableSuffix() {
        return tableSuffix;
    }

    public Set<String> getFieldPrefix() {
        return fieldPrefix;
    }

    public Set<String> getFieldSuffix() {
        return fieldSuffix;
    }

    public Set<String> getInclude() {
        return include;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public boolean isEnableSqlFilter() {
        return enableSqlFilter;
    }

    public boolean isEnableSchema() {
        return enableSchema;
    }

    public boolean isCommentSupported() {
        return commentSupported;
    }

    public static class Builder implements IConfigBuilder<StrategyConfig> {

        private final StrategyConfig strategyConfig;

        public Builder() {
            this.strategyConfig = new StrategyConfig();
        }

        public Builder capitalMode() {
            this.strategyConfig.capitalMode = true;
            return this;
        }

        public Builder skipView() {
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

        public Builder commentSupported() {
            this.strategyConfig.commentSupported = true;
            return this;
        }


        @Override
        public StrategyConfig build() {
            this.strategyConfig.validate();
            return strategyConfig;
        }
    }
}
