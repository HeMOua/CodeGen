package com.hemou.server.domain;

import lombok.Data;

@Data
public class TemplateFile {

    /** 模板文件 ID */
    private Long tplId;

    /** 模板文件名（包括后缀） */
    private String tplName;

    /** 模板路径 */
    private String tplPath;

    /** 模板内容 */
    private String content;

    /** 模板引擎 */
    private String engineType;

    /** 是否仅生成一次 */
    private Boolean onlyOnce;

    /** 是否为公共模板 */
    private Boolean useForAll;
}
