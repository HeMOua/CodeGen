package com.hemou.generator.config;

import com.hemou.generator.config.rules.DateType;
import com.hemou.generator.config.rules.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
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
}
