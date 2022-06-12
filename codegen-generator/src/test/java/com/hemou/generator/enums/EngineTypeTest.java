package com.hemou.generator.enums;

import com.hemou.generator.config.rules.EngineType;
import org.junit.jupiter.api.Test;

class EngineTypeTest {

    @Test
    public void testEngine() {
        System.out.println(EngineType.FreeMarker);
        System.out.println(EngineType.valueOf(EngineType.FreeMarker.toString()));
    }
}