package com.hemou.server.common.core.page;

import lombok.Data;

import java.util.List;

@Data
public class TableDataInfo {

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> rows;

    /** 消息状态码 */
    private int code;

    /** 消息内容 */
    private int msg;

    public TableDataInfo() {
    }

    public TableDataInfo(List<?> rows, int total) {
        this.total = total;
        this.rows = rows;
    }
}
