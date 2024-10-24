package com.hanghae.concert.api.common.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Interceptor interceptor;

    public WebConfig(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/**",
                        "/member",
                        "/queue",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}