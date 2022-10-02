package com.hemou.generator.config.builder;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.StrategyConfig;

public class GeneratorBuilder {

    public static GlobalConfig globalConfig() {
        return new GlobalConfig.Builder().build();
    }

    public static StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder().build();
    }
}
