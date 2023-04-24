package com.kai;

import java.lang.annotation.*;

/**
 * http请求认证放行
 *
 * @author 不北咪
 * @date 2023/4/23 23:42
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpIgnoreAuthentication {
}
