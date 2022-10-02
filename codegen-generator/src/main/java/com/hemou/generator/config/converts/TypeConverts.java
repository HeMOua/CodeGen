package com.hemou.generator.config.converts;

import com.hemou.generator.config.converts.select.BranchBuilder;
import com.hemou.generator.config.converts.select.Selector;
import com.hemou.generator.config.rules.DbType;
import com.hemou.generator.config.rules.IColumnType;

public class TypeConverts {

    public static Selector<String, IColumnType> use(String param) {
        return new Selector<>(param.toLowerCase());
    }

    public static BranchBuilder<String, IColumnType> contains(CharSequence value) {
        return BranchBuilder.of(s -> s.contains(value));
    }

    public static BranchBuilder<String, IColumnType> containsAny(CharSequence... values) {
        return BranchBuilder.of(s -> {
            for (CharSequence value : values) {
                if (s.contains(value)) return true;
            }
            return false;
        });
    }


    public static ITypeConvert getTypeConvert(DbType dbType) {
        switch (dbType) {
            case ORACLE:
                // typeConvert = new OracleTypeConvert();
                break;
            case SQL_SERVER:
                // typeConvert = new SqlServerTypeConvert();
                break;
            case POSTGRE_SQL:
                // typeConvert = new PostgreSqlTypeConvert();
                break;
            case DB2:
                // typeConvert = new DB2TypeConvert();
                break;
            case SQLITE:
                // typeConvert = new SqliteTypeConvert();
                break;
            case DM:
                // typeConvert = new DmTypeConvert();
                break;
            case MARIADB:
            case MYSQL:
                return MySqlTypeConvert.INSTANCE;
        }
        return null;
    }
}
