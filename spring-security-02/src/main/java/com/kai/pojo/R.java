package com.kai.pojo;

import com.kai.enums.HttpStatusEnum;
import lombok.*;

/**
 * 统一返回结果
 *
 * @author 不北咪
 * @date 2023/4/18 19:03
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class R<T> {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public static <T> R<T> success(T data){
        return
                success(HttpStatusEnum.SUCCESS, data);

    }

    public static <T> R<T> success(HttpStatusEnum httpStatusEnum, T data){
        return
                new R<T>(
                        httpStatusEnum.getStatus(),
                        httpStatusEnum.getMessage(),
                        data
                );
    }

    public static <T> R<T> success(HttpStatusEnum httpStatusEnum){
        return success(httpStatusEnum, null);
    }

    public static <T> R<T> success(){
        return success(HttpStatusEnum.SUCCESS,null);
    }

    public static <T> R<T> success(String message, T data){
        return
                new R<T>(HttpStatusEnum.SUCCESS.getStatus(), message, data);
    }

    public static <T> R<T> failure(Integer status, String message, T data){
        return new R<T>(status,message,data);
    }

    public static <T> R<T> failure(String message, T data){
        return failure(HttpStatusEnum.FAILURE.getStatus(), message, data);
    }

    public static <T> R<T> failure(){
        return failure(HttpStatusEnum.FAILURE,null);
    }

    public static <T> R<T> failure(HttpStatusEnum httpStatusEnum, T data){
        return failure(httpStatusEnum.getStatus(), httpStatusEnum.getMessage(), data);
    }

    public static <T> R<T> failure(T data){
        return failure(HttpStatusEnum.FAILURE, data);
    }
}
