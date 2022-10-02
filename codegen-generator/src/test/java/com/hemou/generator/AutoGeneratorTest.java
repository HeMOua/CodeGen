package com.hemou.generator;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.TemplateInfo;
import com.hemou.generator.config.rules.EngineType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutoGeneratorTest {

    @Test
    public void test() {
        AutoGenerator generator = new AutoGenerator();

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .author("贺墨于")
                .projectDesc("生成不同类别的代码")
                .projectEnName("代码生成器")
                .projectZhName("code generator")
                .build();
        generator.setGlobalConfig(globalConfig);

        // Template
        List<TemplateInfo> templateInfos = new ArrayList<>();

        // template_1
        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setEngineType(EngineType.Beetl);
        templateInfo.setIdentity("1");
        String templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n";
        templateInfo.setTemplateContent(templateStr);
        templateInfo.setFilePath("src\\Demo1.java");
        templateInfos.add(templateInfo);

        // template_2
        TemplateInfo templateInfo2 = new TemplateInfo();
        templateInfo2.setEngineType(EngineType.Thymeleaf);
        templateInfo2.setIdentity("2");
        templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_time.yyyy})][(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n" +
                "[(${comment})]";
        templateInfo2.setTemplateContent(templateStr);
        templateInfo2.setFilePath("src\\Demo2.java");
        templateInfos.add(templateInfo2);

        generator.setTemplateConfig(
                new TemplateConfig
                        .Builder()
                        .templateList(templateInfos)
                        .build()
        );

        Map<String, String> result = generator.execute();
        System.out.println(result);
    }
}