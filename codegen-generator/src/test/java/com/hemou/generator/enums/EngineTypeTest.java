package com.hemou.generator.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineTypeTest {

    @Test
    public void testEngine() {
        System.out.println(EngineType.FreeMarker);
        System.out.println(EngineType.valueOf(EngineType.FreeMarker.toString()));
    }
}