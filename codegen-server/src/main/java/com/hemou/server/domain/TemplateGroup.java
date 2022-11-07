package com.hemou.server.domain;

import lombok.Data;

@Data
public class TemplateGroup {

    /** 模板组 ID */
    private Long groupId;

    /** 模板组名 */
    private String groupName;

    /** 介绍 */
    private String introduce;

    /** 父模板组 ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 帮助手册 */
    private String manual;

    /** 图片 */
    private String images;
}
