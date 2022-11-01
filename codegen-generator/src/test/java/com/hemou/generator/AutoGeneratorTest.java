package com.hemou.generator;

import com.hemou.generator.config.DataSourceConfig;
import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import com.hemou.generator.config.rules.EngineType;
import com.hemou.generator.config.rules.IdType;
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

        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
                "jdbc:mysql://localhost:3306/music?useInformationSchema=true",
                "root", "root").build();
        generator.setDataSource(dataSourceConfig);

        // Template
        List<TemplateInfo> templateInfos = new ArrayList<>();

        // template_1
        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setFileName("${author}.java");
        templateInfo.setEngineType(EngineType.Beetl);
        String templateStr = "{}${author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n";
        templateInfo.setContent(templateStr);
        templateInfo.setFilePath("src");
        templateInfo.setOnlyOnce(true);
        templateInfos.add(templateInfo);

        // template_2
        TemplateInfo templateInfo2 = new TemplateInfo();
        templateInfo2.setFileName("${author}.java");
        templateInfo2.setEngineType(EngineType.Thymeleaf);
        templateStr = "${author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_time.yyyy})][(${author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n" +
                "[(${comment})]";
        templateInfo2.setContent(templateStr);
        templateInfo2.setFilePath("src\\[(${author})].java");
        templateInfos.add(templateInfo2);

        TemplateInfo templateInfo3 = new TemplateInfo();
        templateInfo3.setFileName("${author}.java");
        templateInfo3.setEngineType(EngineType.Thymeleaf);
        templateStr = "老表不爱我，${author}";
        templateInfo3.setContent(templateStr);
        templateInfo3.setFilePath("src\\[(${author})].java");
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

    @Test
    public void testIdType() {
        System.out.println(IdType.AUTO.toString());
    }

    @Test
    public void testBasePackage() {
        GlobalConfig globalConfig = new GlobalConfig.Builder().basePackage("com.hemou").build();
        System.out.println(globalConfig.getBasePath());
    }
}