package cn.jiegeng.luobin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPool {
    @Bean(name = "pool")
    public ExecutorService getThreadPool() {
                return
                new ThreadPoolExecutor(
                2,  //核心线程池大小
                5,  //最大核心线程池大小
                3,  //超时时间
                TimeUnit.SECONDS,  //超时单位
                new LinkedBlockingQueue<>(3), //阻塞队列 -->  相当于银行的候客区
                Executors.defaultThreadFactory(), //线程工厂
                new ThreadPoolExecutor.DiscardOldestPolicy()); //拒绝策略
    }

}
