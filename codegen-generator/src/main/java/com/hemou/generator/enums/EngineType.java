package com.hemou.generator.enums;

public enum EngineType {

    FreeMarker("freemarker"),
    Velocity("velocity"),
    Thymeleaf("thymeleaf");

    private final String engineName;

    EngineType(String engineName) {
        this.engineName = engineName;
    }

    public String getEngineName() {
        return engineName;
    }
}
