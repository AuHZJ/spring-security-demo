package com.au.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {
    SUCCESS_CODE(200, "处理成功"),
    FAILURE_CODE(500, "系统异常"),
    UNAUTHORIZED_CODE(401, "未登录"),
    PERMISSION_DENIED_CODE(403, "权限不足");

    private Integer code;

    private String desc;

}
