package com.ky.ulearning.common.core.component.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * swagger2 在线api文档配置
 *
 * @author luyuhao
 * @date 19/12/12 01:19
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private ServletContext servletContext;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        String moduleSuffix = isNullOrEmpty(servletContext.getContextPath()) ? "" : servletContext.getContextPath();
                        return defaultConfigParameters.getSystemSuffix() + moduleSuffix;
                    }
                })
                .enable(defaultConfigParameters.getSwaggerEnabled())
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                //扫描指定注解
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
//                .securityContexts(Lists.newArrayList(tokenSecurityContext(), refreshTokenSecurityContext()))
//                .securitySchemes(Lists.<SecurityScheme>newArrayList(token(), refreshToken()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("U-Learning 接口文档")
                .version("1.0.0")
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
