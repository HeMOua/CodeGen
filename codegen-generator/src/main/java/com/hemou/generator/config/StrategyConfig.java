package com.hemou.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 策略配置项
 */
@Data
@Accessors(chain = true)
public class StrategyConfig {

    /**
     * 是否大写命名
     */
    private boolean isCapitalMode = false;

    /**
     * 是否启用注释
     */
    private boolean commentSupported = true;
}
