package com.ants.antsbackground.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 添加bean到Spring容器中
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Configuration
public class BeanConfig {
    //创建线程池的数量
    private static final Integer THREAD_POOL_NUMBER = 10;

    /**
     * 创建线程池对象，将其注入到Spring容器中
     * @return
     */
    @Bean("ExecutorService")
    public ExecutorService getExecutorPool(){
        return Executors.newFixedThreadPool(THREAD_POOL_NUMBER);
    }
}
