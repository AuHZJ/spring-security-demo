package com.au.model.DTO;

import lombok.Data;

/**
 * @author: huzhijin
 * @create: 2020/4/2 11:21 上午
 **/
@Data
public class HttpResponseDTO {
    public static final String DEFAULT_SUCCESS_MSG = "操作成功";
    public static final String DEFAULT_FAILURE_MSG = "操作失败";

    private Integer code;

    private Object data;

    private String msg;

    private Boolean isError;
}
