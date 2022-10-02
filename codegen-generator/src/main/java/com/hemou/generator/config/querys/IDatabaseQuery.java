package com.hemou.generator.config.querys;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.builder.ConfigBuilder;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.rules.DbType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class IDatabaseQuery {

    protected final ConfigBuilder configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    public IDatabaseQuery(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    /**
     * 获取表信息
     *
     * @return 表信息
     */
    public abstract List<TableInfo> queryTables();

    public static class DefaultDatabaseQuery extends IDatabaseQuery {

        private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDatabaseQuery.class);

        private final StrategyConfig strategyConfig;

        private final GlobalConfig globalConfig;

        private final DbQueryDecorator dbQuery;

        public DefaultDatabaseQuery(ConfigBuilder configBuilder) {
            super(configBuilder);
            this.globalConfig = configBuilder.getGlobalConfig();
            this.strategyConfig = configBuilder.getStrategyConfig();
            this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
        }

        @Override
        public List<TableInfo> queryTables() {
            boolean isInclude = strategyConfig.getInclude().size() > 0;
            boolean isExclude = strategyConfig.getExclude().size() > 0;
            //所有的表信息
            List<TableInfo> tableList = new ArrayList<>();

            //需要反向生成或排除的表信息
            List<TableInfo> includeTableList = new ArrayList<>();
            List<TableInfo> excludeTableList = new ArrayList<>();
            try {
                dbQuery.execute(dbQuery.tablesSql(), result -> {
                    String tableName = result.getStringResult(dbQuery.tableName());
                    if (StringUtils.isNotBlank(tableName)) {
                        TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
                        String tableComment = result.getTableComment();
                        // 跳过视图
                        if (!(strategyConfig.isSkipView() && "VIEW".equals(tableComment))) {
                            tableInfo.setComment(tableComment);
                            if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                                includeTableList.add(tableInfo);
                            } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                                excludeTableList.add(tableInfo);
                            }
                            tableList.add(tableInfo);
                        }
                    }
                });
                if (isExclude || isInclude) {
                    Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                            .stream()
                            .filter(s -> !ConfigBuilder.matcherRegTable(s))
                            .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
                    // 将已经存在的表移除，获取配置中数据库不存在的表
                    for (TableInfo tabInfo : tableList) {
                        if (notExistTables.isEmpty()) {
                            break;
                        }
                        //解决可能大小写不敏感的情况导致无法移除掉
                        notExistTables.remove(tabInfo.getName().toLowerCase());
                    }
                    if (notExistTables.size() > 0) {
                        LOGGER.warn("表[{}]在数据库中不存在！！！", String.join(",", notExistTables.values()));
                    }
                    // 需要反向生成的表信息
                    if (isExclude) {
                        tableList.removeAll(excludeTableList);
                    } else {
                        tableList.clear();
                        tableList.addAll(includeTableList);
                    }
                }
                // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
                tableList.forEach(this::convertTableFields);
                return tableList;
            } catch (Exception e) {

            }

            return null;
        }

        private void convertTableFields(TableInfo tableInfo) {
            DbType dbType = this.dataSourceConfig.getDbType();
            String tableName = tableInfo.getName();

        }
    }
}
