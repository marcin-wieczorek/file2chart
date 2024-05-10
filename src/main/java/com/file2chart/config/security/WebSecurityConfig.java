package com.file2chart.config.security;

import com.file2chart.service.utils.JsonConverter;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final JsonConverter converter;

    @Bean
    @Order(1)
    public FilterRegistrationBean<AuthenticationFilter> apiAccessFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthenticationFilter(converter));
        registrationBean.addUrlPatterns("/v1/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
