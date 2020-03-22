package com.huige.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.huige.cloud")//这样，Mapper/dao接口就不用注解为@Mapper
@EnableScheduling
public class CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

}
