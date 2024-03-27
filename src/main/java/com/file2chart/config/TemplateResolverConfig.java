package com.file2chart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class TemplateResolverConfig {

    private static final String TEMPLATES_PREFIX = "/templates/";
    private static final String TEMPLATES_SUFFIX = ".html";
    private static final String TEMPLATES_MODE = "HTML";

    @Bean
    public SpringTemplateEngine templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(TEMPLATES_PREFIX);
        templateResolver.setSuffix(TEMPLATES_SUFFIX);
        templateResolver.setTemplateMode(TEMPLATES_MODE);

        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver);

        return springTemplateEngine;
    }
}
