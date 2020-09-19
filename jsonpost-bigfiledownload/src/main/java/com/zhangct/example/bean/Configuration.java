package com.zhangct.example.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public RestTemplate httpClientTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}
