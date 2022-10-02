package com.hemou.generator;

import com.hemou.generator.config.GlobalConfig;
import com.hemou.generator.config.TemplateConfig;
import com.hemou.generator.config.po.ResultInfo;
import com.hemou.generator.config.po.TemplateInfo;
import com.hemou.generator.engine.ThymeleafTemplateEngine;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StandardGeneratorTest {

    @Test
    public void execute() {
        StandardGenerator generator = new StandardGenerator();

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .author("贺墨于")
                .projectDesc("生成不同类别的代码")
                .projectEnName("代码生成器")
                .projectZhName("code generator")
                .build();
        generator.setGlobalConfig(globalConfig);

        List<TemplateInfo> templateInfos = new ArrayList<>();
        TemplateInfo templateInfo = new TemplateInfo();
        templateInfo.setIdentity("1");
        String templateStr = "${_author}\n${_project.desc} ${_project.enName} ${_project.zhName}\n" +
                "当前时间：${_time.yyyy}/${_time.MM}/${_time.dd} ${_time.HH}:${_time.mm}:${_time.ss}.${_time.sss}";
        templateStr += "\n[(${_author})]\n[(${_project.desc})] [(${_project.enName})] [(${_project.zhName})]\n";
        templateInfo.setContent(templateStr);
        templateInfo.setFilePath("src\\Demo1.java");
        templateInfos.add(templateInfo);

        generator.setTemplateConfig(new TemplateConfig.Builder().templateList(templateInfos).build());

        List<ResultInfo> result = generator.execute(new ThymeleafTemplateEngine());
        System.out.println(result);
    }

    public void testCon() throws SQLException {
        Connection temp = null;
        Connection connection = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
        try (PreparedStatement ps = connection.prepareStatement("select * from news");
             ResultSet result = ps.executeQuery()) {
            while (result.next()) {
                System.out.println(result.getString(1));
            }
            System.out.println("nei");
            System.out.println(connection);
            temp = connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("wai");
        System.out.println(temp.prepareStatement("select 1 from news"));
    }
}