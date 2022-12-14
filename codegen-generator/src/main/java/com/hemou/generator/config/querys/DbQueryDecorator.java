package com.hemou.generator.config.querys;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.StrategyConfig;
import com.hemou.generator.config.rules.DbType;
import com.hemou.generator.exception.CodeGenException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DbQueryDecorator extends AbstractDbQuery {

    private final IDbQuery dbQuery;
    private final Connection connection;
    private final DbType dbType;
    private final StrategyConfig strategyConfig;
    private final String schema;
    private final Logger logger;

    public DbQueryDecorator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig) {
        this.dbQuery = dataSourceConfig.getDbQuery();
        this.connection = dataSourceConfig.getConn();
        this.dbType = dataSourceConfig.getDbType();
        this.strategyConfig = strategyConfig;
        this.schema = dataSourceConfig.getSchemaName();
        this.logger = LoggerFactory.getLogger(dbQuery.getClass());
    }

    @Override
    public String tablesSql() {
        String tablesSql = dbQuery.tablesSql();
        if (DbType.POSTGRE_SQL == dbType || DbType.DB2 == dbType || DbType.ORACLE == dbType) {
            tablesSql = String.format(tablesSql, this.schema);
        }
        if (strategyConfig.isEnableSqlFilter()) {
            StringBuilder sql = new StringBuilder(tablesSql);
            Set<String> tables;
            if (!(tables = strategyConfig.getInclude()).isEmpty()) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" IN (")
                        .append(tables.stream().map(tb -> "'" + tb + "'").collect(Collectors.joining(","))).append(")");
            } else if (!(tables = strategyConfig.getExclude()).isEmpty()) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" NOT IN (")
                        .append(tables.stream().map(tb -> "'" + tb + "'").collect(Collectors.joining(","))).append(")");
            }
            return sql.toString();
        }
        return tablesSql;
    }

    @Override
    public String tableFieldsSql() {
        return dbQuery.tableFieldsSql();
    }

    /**
     * ??????{@link #tableFieldsSql()}??????
     *
     * @param tableName ??????
     * @return ?????????????????????
     */
    public String tableFieldsSql(String tableName) {
        String tableFieldsSql = this.tableFieldsSql();
        if (DbType.DB2 == dbType) {
            tableFieldsSql = String.format(tableFieldsSql, this.schema, tableName);
        } else if (DbType.ORACLE == dbType) {
            tableFieldsSql = String.format(tableFieldsSql.replace("#schema", this.schema), tableName, tableName.toUpperCase());
        } else if (DbType.DM == dbType) {
            tableName = tableName.toUpperCase();
            tableFieldsSql = String.format(tableFieldsSql, tableName);
        } else if (DbType.POSTGRE_SQL == dbType) {
            tableFieldsSql = String.format(tableFieldsSql, tableName, tableName, tableName);
        } else {
            tableFieldsSql = String.format(tableFieldsSql, tableName);
        }
        return tableFieldsSql;
    }

    @Override
    public String tableName() {
        return dbQuery.tableName();
    }

    @Override
    public String tableComment() {
        return dbQuery.tableComment();
    }

    @Override
    public String fieldName() {
        return dbQuery.fieldName();
    }

    @Override
    public String fieldType() {
        return dbQuery.fieldType();
    }

    @Override
    public String fieldComment() {
        return dbQuery.fieldComment();
    }

    @Override
    public String fieldKey() {
        return dbQuery.fieldKey();
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) {
        try {
            return dbQuery.isKeyIdentity(results);
        } catch (SQLException e) {
            logger.warn("????????????????????????:{}", e.getMessage());
            // ignore ??????????????????H2????????????????????????????????????????????????.
        }
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return dbQuery.fieldCustom();
    }

    public Map<String, Object> getCustomFields(ResultSet resultSet) {
        String[] fcs = this.fieldCustom();
        if (null != fcs) {
            Map<String, Object> customMap = new HashMap<>((int)(fcs.length / 0.75 + 1));
            for (String fc : fcs) {
                try {
                    customMap.put(fc, resultSet.getObject(fc));
                } catch (SQLException sqlException) {
                    throw new RuntimeException("???????????????????????????:", sqlException);
                }
            }
            return customMap;
        }
        return Collections.emptyMap();
    }

    /**
     * ?????? SQL ???????????????????????????
     *
     * @param sql      ??????SQL
     * @param consumer ????????????
     * @throws SQLException
     */
    public void execute(String sql, Consumer<ResultSetWrapper> consumer) throws SQLException {
        logger.debug("??????SQL:{}", sql);
        int count = 0;
        long start = System.nanoTime();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                consumer.accept(new ResultSetWrapper(resultSet, this, this.dbType));
                count++;
            }
            long end = System.nanoTime();
            logger.debug("???????????????:{},??????(ms):{}", count, (end - start) / 1000000);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        Optional.ofNullable(connection).ifPresent((con) -> {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    public static class ResultSetWrapper {

        private final IDbQuery dbQuery;

        private final ResultSet resultSet;

        private final DbType dbType;

        ResultSetWrapper(ResultSet resultSet, IDbQuery dbQuery, DbType dbType) {
            this.resultSet = resultSet;
            this.dbQuery = dbQuery;
            this.dbType = dbType;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }

        public String getStringResult(String columnLabel) {
            try {
                return resultSet.getString(columnLabel);
            } catch (SQLException sqlException) {
                throw new CodeGenException("??????[%s]????????????!", sqlException, columnLabel);
            }
        }

        public String getFieldComment() {
            return getComment(dbQuery.fieldComment());

        }

        private String getComment(String columnLabel) {
            return StringUtils.isNotBlank(columnLabel) ? formatComment(getStringResult(columnLabel)) : StringUtils.EMPTY;
        }

        public String getTableComment() {
            return getComment(dbQuery.tableComment());
        }

        public String formatComment(String comment) {
            return StringUtils.isBlank(comment) ? StringUtils.EMPTY : comment.replaceAll("\r\n", "\t");
        }

        public boolean isPrimaryKey() {
            String key = this.getStringResult(dbQuery.fieldKey());
            if (DbType.DB2 == dbType || DbType.SQLITE == dbType) {
                return StringUtils.isNotBlank(key) && "1".equals(key);
            } else {
                return StringUtils.isNotBlank(key) && "PRI".equalsIgnoreCase(key);
            }
        }
    }
}
