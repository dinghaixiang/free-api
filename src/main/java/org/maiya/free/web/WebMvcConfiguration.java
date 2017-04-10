package org.maiya.free.web;

import org.maiya.free.web.interceptor.AppIdInterceptor;
import org.maiya.free.web.interceptor.DigestInterceptor;
import org.maiya.free.web.interceptor.TimestampInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wanglei on 16/11/10.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public TimestampInterceptor timestampInterceptor() {
        return new TimestampInterceptor();
    }

    @Bean
    public AppIdInterceptor appIdInterceptor() {
        return new AppIdInterceptor();
    }

    @Bean
    public DigestInterceptor digestInterceptor() {
        return new DigestInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timestampInterceptor());
//        registry.addInterceptor(appIdInterceptor());
//        registry.addInterceptor(digestInterceptor());
    }

}
