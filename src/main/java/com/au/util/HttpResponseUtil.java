package com.au.util;

import com.au.constant.ResponseCodeEnum;
import com.au.model.DTO.HttpResponseDTO;

/**
 * @author: huzhijin
 * @create: 2020/4/2 1:06 下午
 **/
public class HttpResponseUtil {
    private HttpResponseUtil() {
    }

    public static HttpResponseDTO success() {
        return success(null, 200, HttpResponseDTO.DEFAULT_SUCCESS_MSG);
    }

    public static HttpResponseDTO success(Object data) {
        return success(data, 200, HttpResponseDTO.DEFAULT_SUCCESS_MSG);
    }

    public static HttpResponseDTO success(Object data, ResponseCodeEnum responseCodeEnum) {
        Integer code = responseCodeEnum != null ? responseCodeEnum.getCode() : 200;
        String msg = responseCodeEnum != null ? responseCodeEnum.getDesc() : HttpResponseDTO.DEFAULT_SUCCESS_MSG;
        return success(data, code, msg);
    }

    public static HttpResponseDTO success(Object data, Integer code, String msg) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        httpResponseDTO.setCode(code);
        httpResponseDTO.setData(data);
        httpResponseDTO.setMsg(msg);
        httpResponseDTO.setIsError(false);
        return httpResponseDTO;
    }

    public static HttpResponseDTO failure() {
        return failure(null, 500, HttpResponseDTO.DEFAULT_FAILURE_MSG, true);
    }

    public static HttpResponseDTO failure(String msg) {
        return failure(null, 500, msg, true);
    }

    public static HttpResponseDTO failure(ResponseCodeEnum responseCodeEnum, Boolean isError) {
        Integer code = responseCodeEnum != null ? responseCodeEnum.getCode() : 500;
        String msg = responseCodeEnum != null ? responseCodeEnum.getDesc() : HttpResponseDTO.DEFAULT_FAILURE_MSG;
        return failure(null, code, msg, isError);
    }

    public static HttpResponseDTO failure(Object data, Integer code, String msg, Boolean isError) {
        HttpResponseDTO httpResponseDTO = new HttpResponseDTO();
        httpResponseDTO.setCode(code);
        httpResponseDTO.setData(data);
        httpResponseDTO.setMsg(msg);
        httpResponseDTO.setIsError(isError);
        return httpResponseDTO;
    }
}
