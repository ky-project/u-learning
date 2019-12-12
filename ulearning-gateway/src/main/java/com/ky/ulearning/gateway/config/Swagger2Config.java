package com.ky.ulearning.gateway.config;

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
public class Swagger2Config {
    @Autowired
    private GatewayConfig gatewayConfig;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar1 = new ParameterBuilder();
        ParameterBuilder ticketPar2 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        ticketPar1.name(gatewayConfig.getTokenHeader()).description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer ")
                .required(true)
                .build();
        ticketPar2.name(gatewayConfig.getRefreshTokenHeader()).description("refresh_token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer ")
                .required(true)
                .build();

        pars.add(ticketPar1.build());
        pars.add(ticketPar2.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(gatewayConfig.getSwaggerEnabled())
                .useDefaultResponseMessages(true)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.ky.ulearning.gateway.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("uLearning-system-manage 接口文档")
                .version("0.1")
                .build();
    }
}
