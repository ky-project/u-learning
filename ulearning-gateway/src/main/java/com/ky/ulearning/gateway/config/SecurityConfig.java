package com.ky.ulearning.gateway.config;

import com.ky.ulearning.gateway.common.filter.AccessFilter;
import com.ky.ulearning.gateway.common.filter.JwtAuthorizationTokenFilter;
import com.ky.ulearning.gateway.common.security.JwtAccountDetailsService;
import com.ky.ulearning.gateway.common.security.JwtAuthenticationEntryPoint;
import com.ky.ulearning.gateway.common.security.JwtAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author luyuhao
 * @date 19/12/06 21:22
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccountDetailsService jwtAccountDetailsService;

    @Autowired
    private JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Autowired
    private AccessFilter accessFilter;

    /**
     * 加载全局认证配置
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtAccountDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 默认角色前缀为"ROLE_"，将其设为""
     */
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    /**
     * 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //禁用CSRF
                .csrf().disable()
                //授权异常捕获
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                // 不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //登出设置
                .logout()
                .logoutUrl("/auth/logout")
                .deleteCookies("token", "refresh_token")
                //登出提示
                .logoutSuccessUrl("/auth/logout/success")
                .and()
                // 过滤请求
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/*.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.ico",
                        "/**/*.png",
                        "/**/*.svg"
                ).anonymous()

                .antMatchers(HttpMethod.POST, "/auth/login").anonymous()
                .antMatchers("/auth/logout").anonymous()
                .antMatchers("/auth/logout/success").anonymous()
                .antMatchers("/auth/vCode").anonymous()

                // swagger start
                .antMatchers("/swagger-resources/**").anonymous()
                .antMatchers("/webjars/**").anonymous()
                .antMatchers("/*/v2/api-docs").anonymous()
                .antMatchers("/v2/api-docs").anonymous()
                // swagger end

                //监控
//                .antMatchers("/druid/**").anonymous()
                .antMatchers("/actuator/**").anonymous()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and()
                .headers().frameOptions().disable();

        httpSecurity.addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(accessFilter, JwtAuthorizationTokenFilter.class)
                .formLogin().failureHandler(jwtAuthenticationFailureHandler);
    }
}
