package com.whyisee.getdata.configurer;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/23 10:32
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
@Configuration
public class FileConfigurer {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("1MB"));
        factory.setMaxRequestSize(DataSize.parse("10MB"));
        //factory.setLocation("F:\\data\\test");
        return factory.createMultipartConfig();
    }
}
