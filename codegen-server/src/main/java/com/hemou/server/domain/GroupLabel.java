package com.hemou.server.domain;

import lombok.Data;

@Data
public class GroupLabel {

    /** 标签 ID */
    private Long labelId;

    /** 标签名 */
    private String labelName;
}
