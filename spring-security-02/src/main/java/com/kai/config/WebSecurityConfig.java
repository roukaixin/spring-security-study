package com.kai.config;

import com.google.common.collect.Multimap;
import com.kai.handler.UsernamePasswordAuthenticationFailureHandler;
import com.kai.handler.UsernamePasswordAuthenticationSuccessHandler;
import com.kai.handler.UsernamePasswordLogoutSuccessHandler;
import com.kai.pojo.SecurityPojo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 认证成功处理器
     */
    @Resource
    private UsernamePasswordAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 认证失败处理器
     */
    @Resource
    private UsernamePasswordAuthenticationFailureHandler usernamePasswordAuthenticationFailureHandler;

    /**
     * 退出登录成功处理器
     */
    @Resource
    private UsernamePasswordLogoutSuccessHandler usernamePasswordLogoutSuccessHandler;

    @Resource
    private SecurityPojo securityPojo;


    /**
     * http 配置
     * @param http http
     * @throws Exception e
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Multimap<RequestMethod, String> urlMapping = securityPojo.ignoreAuthenticationUrlMapping();
        http
                .authorizeRequests()
                .antMatchers(GET, urlMapping.get(RequestMethod.GET).toArray(new String[]{}))
                .permitAll()
                .antMatchers(HEAD, urlMapping.get(RequestMethod.HEAD).toArray(new String[]{}))
                .permitAll()
                .antMatchers(POST, urlMapping.get(RequestMethod.POST).toArray(new String[]{}))
                .permitAll()
                .antMatchers(PUT, urlMapping.get(RequestMethod.PUT).toArray(new String[]{}))
                .permitAll()
                .antMatchers(PATCH, urlMapping.get(RequestMethod.PATCH).toArray(new String[]{}))
                .permitAll()
                .antMatchers(DELETE, urlMapping.get(RequestMethod.DELETE).toArray(new String[]{}))
                .permitAll()
                .antMatchers(OPTIONS, urlMapping.get(RequestMethod.OPTIONS).toArray(new String[]{}))
                .permitAll()
                .antMatchers(TRACE, urlMapping.get(RequestMethod.TRACE).toArray(new String[]{}))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                // 表单登录
                .formLogin()
                // 登录接口
                .loginProcessingUrl("/api/auth2/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(usernamePasswordAuthenticationFailureHandler)
                .and()
                // 退出
                .logout()
                .logoutUrl("/api/auth2/logout")
                .logoutSuccessHandler(usernamePasswordLogoutSuccessHandler)
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(User.withUsername("root").password("{noop}123456").roles("admin").build());
    }

    /**
     * 自定义 authenticationManager
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService());
    }

    /**
     * 配置全局 authenticationManager
     *
     */
    @Override
    @Bean // 把工厂本地的 authenticationManager 注入到 spring 容器中，方便之后使用
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
