package com.hemou.generator;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.InjectionConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.engine.ThymeleafTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AutoGeneratorTest {

    @Test
    void execute() {
        AutoGenerator generator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("贺墨于");
        globalConfig.setProjectDesc("生成不同类别的代码");
        globalConfig.setProjectEnName("代码生成器");
        globalConfig.setProjectZhName("code generator");
        generator.setGlobalConfig(globalConfig);

        generator.setInjectionConfig(new InjectionConfig() {

            {
                List<TemplateConfig> templateConfigs = new ArrayList<>();
                TemplateConfig templateConfig = new TemplateConfig();
                templateConfig.setIdentity("1");
                String templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                        "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
                templateStr += "\n[(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n";
                templateConfig.setTemplateContent(templateStr);
                templateConfig.setFilePath("src\\Demo1.java");
                templateConfigs.add(templateConfig);

                setTemplateList(templateConfigs);
            }

            @Override
            public void initMap() {
                if(getMap() == null){
                    setMap(new HashMap<>());
                }
            }
        });

        generator.setTemplateEngine(new ThymeleafTemplateEngine());
        // generator.setTemplateEngine(new BeetlTemplateEngine());
        // generator.setTemplateEngine(new VelocityTemplateEngine());

        Map<String, String> result = generator.execute();
        System.out.println(result);
    }
}