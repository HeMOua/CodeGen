package com.hemou.generator;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import com.hemou.generator.config.rules.EngineType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        templateInfo.setFileName("${_author}.java");
        templateInfo.setEngineType(EngineType.Beetl);
        templateInfo.setIdentity("1");
        String templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n";
        templateInfo.setContent(templateStr);
        templateInfo.setFilePath("src");
        templateInfos.add(templateInfo);

        // template_2
        TemplateInfo templateInfo2 = new TemplateInfo();
        templateInfo2.setFileName("${_author}.java");
        templateInfo2.setEngineType(EngineType.Thymeleaf);
        templateInfo2.setIdentity("2");
        templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_time.yyyy})][(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n" +
                "[(${comment})]";
        templateInfo2.setContent(templateStr);
        templateInfo2.setFilePath("src\\[(${_author})].java");
        templateInfos.add(templateInfo2);

        TemplateInfo templateInfo3 = new TemplateInfo();
        templateInfo3.setFileName("${_author}.java");
        templateInfo3.setEngineType(EngineType.Thymeleaf);
        templateInfo3.setIdentity("2");
        templateStr = "老表不爱我，${_author}";
        templateInfo3.setContent(templateStr);
        templateInfo3.setFilePath("src\\[(${_author})].java");
        templateInfos.add(templateInfo3);

        generator.setTemplateConfig(
                new TemplateConfig
                        .Builder()
                        .templateList(templateInfos)
                        .build()
        );

        List<ResultInfo> result = generator.execute();
        System.out.println(result);
    }
}