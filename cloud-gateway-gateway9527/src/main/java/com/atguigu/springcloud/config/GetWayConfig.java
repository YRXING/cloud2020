package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetWayConfig {
    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        /*
        * 配置了一个id为path_route_atguigu1的路由规则，
        * 当访问地址http://localhost:9527/guonei时会自动转发到地址：http://news.baidu.com/guonei
        * */
        routes.route("path_route_atguigu1",
                r -> r.path("/guonei").
                uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
}
