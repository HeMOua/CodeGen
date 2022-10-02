package com.hemou.generator.config;

import com.hemou.generator.config.rules.DateType;
import org.apache.commons.lang3.StringUtils;

public class GlobalConfig {

    private GlobalConfig(){}

    /**
     * 开发人员
     */
    private String author = "作者";

    /**
     * 作者邮箱
     */
    private String email = StringUtils.EMPTY;

    /**
     * 作者电话
     */
    private String mobile = StringUtils.EMPTY;

    /**
     * 项目英文名
     */
    private String projectEnName = StringUtils.EMPTY;

    /**
     * 项目中文名
     */
    private String projectZhName = StringUtils.EMPTY;

    /**
     * 项目描述
     */
    private String projectDesc = StringUtils.EMPTY;

    /**
     * 开启 Kotlin 模式（默认 false）
     */
    private boolean kotlin;

    /**
     * 开启 swagger 模式（默认 false）
     */
    private boolean swagger;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.ONLY_DATE;

    public String getAuthor() {
        return author;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getProjectEnName() {
        return projectEnName;
    }

    public String getProjectZhName() {
        return projectZhName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public boolean isKotlin() {
        return kotlin;
    }

    public boolean isSwagger() {
        return swagger;
    }

    public DateType getDateType() {
        return dateType;
    }

    /**
     * 全局配置构建
     *
     * @author nieqiurong 2020/10/11.
     * @since 3.5.0
     */
    public static class Builder implements IConfigBuilder<GlobalConfig> {

        private final GlobalConfig globalConfig;

        public Builder() {
            this.globalConfig = new GlobalConfig();
        }

        public Builder author(String author) {
            this.globalConfig.author = author;
            return this;
        }

        public Builder email(String email) {
            this.globalConfig.email = email;
            return this;
        }

        public Builder mobile(String mobile) {
            this.globalConfig.mobile = mobile;
            return this;
        }

        public Builder projectEnName(String projectEnName) {
            this.globalConfig.projectEnName = projectEnName;
            return this;
        }

        public Builder projectZhName(String projectZhName) {
            this.globalConfig.projectZhName = projectZhName;
            return this;
        }

        public Builder projectDesc(String projectDesc) {
            this.globalConfig.projectDesc = projectDesc;
            return this;
        }

        public Builder kotlin() {
            this.globalConfig.kotlin = true;
            return this;
        }

        public Builder swagger() {
            this.globalConfig.swagger = true;
            return this;
        }

        public Builder dateType(DateType dateType) {
            this.globalConfig.dateType = dateType;
            return this;
        }

        @Override
        public GlobalConfig build() {
            return this.globalConfig;
        }
    }
}
