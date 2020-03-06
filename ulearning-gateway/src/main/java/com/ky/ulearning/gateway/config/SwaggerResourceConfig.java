package com.ky.ulearning.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/12 01:08
 */
@Slf4j
@Component
@Primary
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final static String[] IGNORED_ROUTE = {"config", "xxl-job-admin", "ulearning-register"};

    private final static String IGNORED_PREFIX = "gateway";

    private final static String SUFFIX = "v2/api-docs";

    @Autowired
    RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        //获取所有router
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        for (Route route : routes) {
            boolean skipFlag = false;
            for (String ignoredRoute : IGNORED_ROUTE) {
                if(route.getId().equals(ignoredRoute)){
                    skipFlag = true;
                    break;
                }
            }
            if(skipFlag){
                continue;
            }
            String docUrl;
            if(route.getId().equals(IGNORED_PREFIX)){
                docUrl = "/" + SUFFIX;
            } else {
                docUrl = route.getFullPath().replace("**", SUFFIX);
            }
            resources.add(swaggerResource(route.getId(), docUrl));
        }
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0");
        return swaggerResource;
    }
}
