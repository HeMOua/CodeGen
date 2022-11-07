package com.hemou.server.domain;


import lombok.Data;

/**
 * 属性选项对象 t_attr_item
 *
 * @author 贺墨于
 * @date 2022-11-07
 */
@Data
public class AttrItem {

    private static final long serialVersionUID = 1L;

    /** 属性选项 ID */
    private Long itemId;

    /** 名称 */
    private String name;

    /** 值 */
    private String value;

    /** 说明 */
    private String introduce;

    /** 模板组属性 ID */
    private Long attrId;

}