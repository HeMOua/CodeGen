package com.hemou.generator.config.function;

/**
 * 转换输出文件名称
 */
@FunctionalInterface
public interface ConverterFileName {

    String convert(String entityName);
}