package com.hemou.generator.config;

import com.hemou.generator.config.rules.IdType;
import com.hemou.generator.config.rules.NamingStrategy;
import com.hemou.generator.utils.StringUtils;
import lombok.Getter;

import java.util.*;

/**
 * 策略配置项
 */
@Getter
public class StrategyConfig {

    private StrategyConfig() {}

    /////////////////////////////////////////////
    //////////////////  公共策略  ////////////////
    /////////////////////////////////////////////
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

    /////////////////////////////////////////////
    /////////////////  Entity 策略  //////////////
    /////////////////////////////////////////////

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID;

    /**
     * 【实体】是否为链式模型（默认 false）<br>
     * -----------------------------------<br>
     * public User setName(String name) { this.name = name; return this; }
     */
    private boolean chain;

    /**
     * 【实体】是否为lombok模型（默认 false）
     */
    private boolean lombok;

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
     * 是否启用注释
     */
    private boolean commentSupported = true;

    /**
     * 自定义忽略字段
     */
    private final Set<String> ignoreColumns = new HashSet<>();

    /**
     * 自定义基础的Entity类，公共字段
     */
    private final Set<String> superEntityColumns = new HashSet<>();

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

    /////////////////////////////////////////////
    /////////////////  其他策略  //////////////
    /////////////////////////////////////////////

    /**
     * 接口前缀格式
     */
    private String interfaceFormat = "I{}";

    /** 页面不需要编辑字段 */
    public Set<String> columnNameNotEdit = new HashSet<>(Arrays.asList("id", "create_by", "create_time", "del_flag"));

    /** 页面不需要显示的列表字段 */
    public Set<String> columnNameNotList = new HashSet<>(Arrays.asList("id", "create_by", "create_time", "del_flag", "update_by", "update_time"));

    /** 页面不需要查询字段 */
    public Set<String> columnNameNotQuery = new HashSet<>(Arrays.asList("id", "create_by", "create_time", "del_flag", "update_by", "update_time", "remark"));

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

    public Map<String, Object> renderData() {
        Map<String, Object> data = new HashMap<>();
        data.put("idType", idType == null ? null : idType.toString());
        data.put("entitySerialVersionUID", this.serialVersionUID);
        data.put("entityBuilderModel", this.chain);
        data.put("chainModel", this.chain);
        data.put("entityLombokModel", this.lombok);
        data.put("entityBooleanColumnRemoveIsPrefix", this.booleanColumnRemoveIsPrefix);
        data.put("commentSupported", this.commentSupported);
        data.put("fieldComment", this.tableFieldAnnotationEnable);
        return data;
    }

    public static class Builder implements IConfigBuilder<StrategyConfig> {

        private final StrategyConfig strategyConfig;

        public Builder() {
            this.strategyConfig = new StrategyConfig();
            this.strategyConfig.nameConvert = new INameConvert.DefaultNameConvert(strategyConfig);
        }

        ///////////// 全局策略 /////////////

        public Builder enableCapitalMode() {
            this.strategyConfig.capitalMode = true;
            return this;
        }

        public Builder enableSkipView() {
            this.strategyConfig.skipView = true;
            return this;
        }

        /**
         * 增加过滤表前缀
         *
         * @param tablePrefix 过滤表前缀
         * @return this
         */
        public Builder addTablePrefix(String... tablePrefix) {
            return addTablePrefix(Arrays.asList(tablePrefix));
        }

        public Builder addTablePrefix(List<String> tablePrefixList) {
            this.strategyConfig.tablePrefix.addAll(tablePrefixList);
            return this;
        }

        /**
         * 增加过滤表后缀
         *
         * @param tableSuffix 过滤表后缀
         * @return this
         */
        public Builder addTableSuffix(String... tableSuffix) {
            return addTableSuffix(Arrays.asList(tableSuffix));
        }

        public Builder addTableSuffix(List<String> tableSuffixList) {
            this.strategyConfig.tableSuffix.addAll(tableSuffixList);
            return this;
        }

        /**
         * 增加过滤字段前缀
         *
         * @param fieldPrefix 过滤字段前缀
         * @return this
         */
        public Builder addFieldPrefix(String... fieldPrefix) {
            return addFieldPrefix(Arrays.asList(fieldPrefix));
        }

        public Builder addFieldPrefix(List<String> fieldPrefix) {
            this.strategyConfig.fieldPrefix.addAll(fieldPrefix);
            return this;
        }

        /**
         * 增加过滤字段后缀
         *
         * @param fieldSuffix 过滤字段后缀
         * @return this
         */
        public Builder addFieldSuffix(String... fieldSuffix) {
            return addFieldSuffix(Arrays.asList(fieldSuffix));
        }

        public Builder addFieldSuffix(List<String> fieldSuffixList) {
            this.strategyConfig.fieldSuffix.addAll(fieldSuffixList);
            return this;
        }

        /**
         * 增加包含的表名
         *
         * @param include 包含表
         * @return this
         */
        public Builder addInclude(String... include) {
            this.strategyConfig.include.addAll(Arrays.asList(include));
            return this;
        }

        public Builder addInclude(List<String> includes) {
            this.strategyConfig.include.addAll(includes);
            return this;
        }

        public Builder addInclude(String include) {
            this.strategyConfig.include.addAll(Arrays.asList(include.split(",")));
            return this;
        }

        /**
         * 增加排除表
         *
         * @param exclude 排除表
         * @return this
         */
        public Builder addExclude(String... exclude) {
            return addExclude(Arrays.asList(exclude));
        }

        public Builder addExclude(List<String> excludeList) {
            this.strategyConfig.exclude.addAll(excludeList);
            return this;
        }

        public Builder addExclude(String include) {
            this.strategyConfig.exclude.addAll(Arrays.asList(include.split(",")));
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

        ///////////// Entity 策略 /////////////

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

        public Builder enableRemoveIsPrefix() {
            this.strategyConfig.booleanColumnRemoveIsPrefix = true;
            return this;
        }

        public Builder enableTableFieldAnnotation() {
            this.strategyConfig.tableFieldAnnotationEnable = true;
            return this;
        }

        public Builder enableSerialVersionUID() {
            this.strategyConfig.serialVersionUID = true;
            return this;
        }

        public Builder idType(IdType idType) {
            this.strategyConfig.idType = idType;
            return this;
        }

        public Builder enableChain() {
            this.strategyConfig.chain = true;
            return this;
        }

        public Builder enableLombok() {
            this.strategyConfig.lombok = true;
            return this;
        }

        /**
         * 添加父类公共字段
         *
         * @param superEntityColumns 父类字段(数据库字段列名)
         * @return this
         */
        public Builder addSuperEntityColumns(String... superEntityColumns) {
            return addSuperEntityColumns(Arrays.asList(superEntityColumns));
        }

        public Builder addSuperEntityColumns(List<String> superEntityColumnList) {
            this.strategyConfig.superEntityColumns.addAll(superEntityColumnList);
            return this;
        }

        /**
         * 添加忽略字段
         *
         * @param ignoreColumns 需要忽略的字段(数据库字段列名)
         * @return this
         */
        public Builder addIgnoreColumns(String... ignoreColumns) {
            return addIgnoreColumns(Arrays.asList(ignoreColumns));
        }

        public Builder addIgnoreColumns(List<String> ignoreColumnList) {
            this.strategyConfig.ignoreColumns.addAll(ignoreColumnList);
            return this;
        }

        public Builder interfaceFormat(String format) {
            this.strategyConfig.interfaceFormat = format;
            return this;
        }

        /**
         * 增加不可编辑字段
         * @param params 不可编辑字段
         */
        public Builder addColumnNameNotEdit(String... params) {
            this.strategyConfig.columnNameNotEdit.addAll(Arrays.asList(params));
            return this;
        }

        public Builder addColumnNameNotEdit(List<String> params) {
            this.strategyConfig.columnNameNotEdit.addAll(params);
            return this;
        }

        public Builder addColumnNameNotEdit(String params) {
            this.strategyConfig.columnNameNotEdit.addAll(Arrays.asList(params.split(",")));
            return this;
        }

        /**
         * 增加不需要显示的列表字段
         * @param params 需要显示的列表字段
         */
        public Builder addColumnNameNotList(String... params) {
            this.strategyConfig.columnNameNotList.addAll(Arrays.asList(params));
            return this;
        }

        public Builder addColumnNameNotList(List<String> params) {
            this.strategyConfig.columnNameNotList.addAll(params);
            return this;
        }

        public Builder addColumnNameNotList(String params) {
            this.strategyConfig.columnNameNotList.addAll(Arrays.asList(params.split(",")));
            return this;
        }

        /**
         * 增加不需要查询的字段
         * @param params 不需要查询的字段
         */
        public Builder addColumnNameNotQuery(String... params) {
            this.strategyConfig.columnNameNotQuery.addAll(Arrays.asList(params));
            return this;
        }

        public Builder addColumnNameNotQuery(List<String> params) {
            this.strategyConfig.columnNameNotQuery.addAll(params);
            return this;
        }

        public Builder addColumnNameNotQuery(String params) {
            this.strategyConfig.columnNameNotQuery.addAll(Arrays.asList(params.split(",")));
            return this;
        }

        @Override
        public StrategyConfig build() {
            this.strategyConfig.validate();
            return strategyConfig;
        }
    }
}
