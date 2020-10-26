package com.whyisee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/19 15:12
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
