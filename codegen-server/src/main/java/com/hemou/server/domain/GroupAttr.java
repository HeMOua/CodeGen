package com.hemou.server.domain;


import lombok.Data;

/**
 * 模板组属性对象 t_group_attr
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@Data
public class GroupAttr {

    private static final long serialVersionUID = 1L;

    /**
     * 模板组属性 ID
     */
    private Long attrId;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性变量
     */
    private String attrVar;

    /**
     * 输入类型
     */
    private String attrType;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 属性说明
     */
    private String introduce;

    /**
     * 必填
     */
    private Boolean notNull;

    /**
     * 配置键
     */
    private Boolean configKey;

}