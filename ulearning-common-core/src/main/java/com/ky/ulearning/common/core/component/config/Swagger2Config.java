package com.ky.ulearning.common.core.component.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * swagger2 在线api文档配置
 *
 * @author luyuhao
 * @date 19/12/12 01:19
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(defaultConfigParameters.getSwaggerEnabled())
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                //扫描指定注解
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(tokenSecurityContext(), refreshTokenSecurityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(token(), refreshToken()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("U-Learning 接口文档")
                .version("0.1")
                .build();
    }

    private ApiKey token() {
        return new ApiKey("token(Bearer xxx.yyy.zzz)", defaultConfigParameters.getTokenHeader(), "header");
    }

    private ApiKey refreshToken() {
        return new ApiKey("refresh_token(Bearer xxx.yyy.zzz)", defaultConfigParameters.getRefreshTokenHeader(), "header");
    }

    private SecurityContext tokenSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(tokenAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private SecurityContext refreshTokenSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(refreshTokenAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private List<SecurityReference> tokenAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("token", authorizationScopes));
    }

    private List<SecurityReference> refreshTokenAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("refresh_token", authorizationScopes));
    }

}
