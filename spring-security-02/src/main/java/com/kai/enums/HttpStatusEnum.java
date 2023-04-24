package com.kai.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum HttpStatusEnum {

    SUCCESS(200,"请求成功"),
    FAILURE(500,"请求失败");

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 消息
     */
    private String message;
}
