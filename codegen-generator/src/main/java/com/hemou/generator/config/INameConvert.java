package com.hemou.generator.config;

import com.hemou.generator.config.po.TableField;
import com.hemou.generator.config.po.TableInfo;
import com.hemou.generator.config.rules.NamingStrategy;
import com.hemou.generator.utils.StringUtils;

import java.util.Set;

/**
 * 名称转换接口类
 */
public interface INameConvert {

    /**
     * 执行实体名称转换
     *
     * @param tableInfo 表信息对象
     * @return
     */
    String entityNameConvert(TableInfo tableInfo);

    /**
     * 执行属性名称转换
     *
     * @param field 表字段对象，如果属性表字段命名不一致注意 convert 属性的设置
     * @return
     */
    String propertyNameConvert(TableField field);

    /**
     * 默认名称转换接口类
     *
     * @author nieqiurong 2020/9/20.
     * @since 3.5.0
     */
    class DefaultNameConvert implements INameConvert {

        private final StrategyConfig strategyConfig;

        public DefaultNameConvert(StrategyConfig strategyConfig) {
            this.strategyConfig = strategyConfig;
        }

        @Override
        public String entityNameConvert(TableInfo tableInfo) {
            return NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategyConfig.entity().getNaming(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix()));
        }

        @Override
        public String propertyNameConvert(TableField field) {
            return processName(field.getName(), strategyConfig.entity().getColumnNaming(), strategyConfig.getFieldPrefix(), strategyConfig.getFieldSuffix());
        }

        private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
            String propertyName = name;
            // 删除前缀
            if (prefix.size() > 0) {
                propertyName = NamingStrategy.removePrefix(propertyName, prefix);
            }
            // 删除后缀
            if (suffix.size() > 0) {
                propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
            }
            if (StringUtils.isBlank(propertyName)) {
                throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
            }
            // 下划线转驼峰
            if (NamingStrategy.underline_to_camel.equals(strategy)) {
                return NamingStrategy.underlineToCamel(propertyName);
            }
            return propertyName;
        }
    }
}
