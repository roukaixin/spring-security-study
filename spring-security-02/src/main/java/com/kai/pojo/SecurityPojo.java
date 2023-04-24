package com.kai.pojo;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.kai.HttpIgnoreAuthentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * spring security 排除拦截 url
 *
 * @author 不北咪
 * @date 2023/4/23 22:57
 */
@Component
public class SecurityPojo {

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    public Multimap<RequestMethod, String> ignoreAuthenticationUrlMapping(){
        Multimap<RequestMethod, String> urlMapping = ArrayListMultimap.create();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        // 遍历所有的方法
        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
            // 获取 key
            RequestMappingInfo key = requestMappingInfoHandlerMethodEntry.getKey();
            // 获取 value
            HandlerMethod value = requestMappingInfoHandlerMethodEntry.getValue();
            HttpIgnoreAuthentication inClassAnnotation = value.getBeanType().getAnnotation(HttpIgnoreAuthentication.class);
            if (inClassAnnotation != null){
                // 请求方式
                addIgnoreUrlMapping(key, urlMapping);
            }else {
                // 获取方法上的注解
                HttpIgnoreAuthentication methodAnnotation = value.getMethodAnnotation(HttpIgnoreAuthentication.class);
                if (methodAnnotation != null){
                    addIgnoreUrlMapping(key, urlMapping);
                }
            }
        }
        return urlMapping;
    }

    /**
     * 把需要放行的 url 添加到 map 中
     * @param key 请求信息
     * @param urlMapping map
     */
    private void addIgnoreUrlMapping(RequestMappingInfo key, Multimap<RequestMethod, String> urlMapping){
        Set<RequestMethod> methods = key.getMethodsCondition().getMethods();
        // 请求 url
        Set<String> patternValues = key.getDirectPaths();
        if (methods.size() > 0) {
            methods.forEach(method -> {
                urlMapping.putAll(method, patternValues);
            });
        }else if (patternValues.size() > 0){
            // 如果使用 @RequestMapping 注解映射，那么放行的时候默认只放行 get 请求
            urlMapping.putAll(RequestMethod.GET, patternValues);
        }
    }
}
