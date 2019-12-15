package com.ky.ulearning.gateway.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/12 01:19
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {
    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar1 = new ParameterBuilder();
        ParameterBuilder ticketPar2 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar1.name(gatewayConfigParameters.getTokenHeader()).description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer ")
                .required(true)
                .build();
        ticketPar2.name(gatewayConfigParameters.getRefreshTokenHeader()).description("refresh_token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer ")
                .required(true)
                .build();

        pars.add(ticketPar1.build());
        pars.add(ticketPar2.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(gatewayConfigParameters.getSwaggerEnabled())
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                //扫描指定注解
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("U-Learning 接口文档")
                .version("0.1")
                .build();
    }
}
