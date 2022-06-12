package com.hemou.generator.config;

import com.hemou.generator.config.rules.DateType;
import com.hemou.generator.enums.IdType;

public class GlobalConfig {

    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 开发人员
     */
    private String author;

    /**
     * 作者邮箱
     */
    private String email;

    /**
     * 作者电话
     */
    private String mobile;

    /**
     * 项目英文名
     */
    private String projectEnName;

    /**
     * 项目中文名
     */
    private String projectZhName;

    /**
     * 项目描述
     */
    private String projectDesc;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.ONLY_DATE;

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType;

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public boolean isEnableCache() {
        return enableCache;
    }

    public void setEnableCache(boolean enableCache) {
        this.enableCache = enableCache;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProjectEnName() {
        return projectEnName;
    }

    public void setProjectEnName(String projectEnName) {
        this.projectEnName = projectEnName;
    }

    public String getProjectZhName() {
        return projectZhName;
    }

    public void setProjectZhName(String projectZhName) {
        this.projectZhName = projectZhName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }
}
