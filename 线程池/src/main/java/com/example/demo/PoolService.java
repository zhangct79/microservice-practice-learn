package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * @author zhangct
 * @date 20/7/17
 */
@Service
public class PoolService implements InitializingBean {
    @Autowired
    private ExecutorService executorService;

    void invoke() {
        executorService.execute(()-> System.out.println(Thread.currentThread().getName()));
        System.out.println(executorService +"/" + executorService.hashCode());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        invoke();
    }
}
