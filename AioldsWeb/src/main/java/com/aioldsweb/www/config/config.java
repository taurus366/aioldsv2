package com.aioldsweb.www.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class config implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    public config(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor);
    }
}
