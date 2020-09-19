package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author zhangct
 * @date 20/7/17
 */
@Configuration
public class TreadConfig {
    @Bean(name="ExecutorService")
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(5,10,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>(10));
    }
}
