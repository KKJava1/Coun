package com.jiawa.wiki.task;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author KangJunJie
 */
@Slf4j
@Configuration
public class QueueTaskConfigTest {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private QueueTaskService queueTaskService;

    private final Executor executor = Executors.newSingleThreadExecutor();  // 使用单线程执行任务

    @PostConstruct
    public void queueTask() {
        // 创建一个单独的线程来处理任务队列
        Runnable runnable = () -> {
            try {
                /**
                 * RBlockingQueue：是一个阻塞队列，它的作用是管理任务队列。
                 * 当你调用 take() 方法时，如果队列为空，当前线程会被阻塞，直到队列中有任务可以取出。
                 */
                RBlockingQueue<QueueTask> blockingFairQueue = redissonClient.getBlockingQueue(ExecuteQueueTaskService.TASK_01);
                while (true) {

                    final QueueTask take = blockingFairQueue.take();  // 阻塞，直到有任务
                    queueTaskService.executeTask(take);  // 执行任务
                }

            } catch (InterruptedException e) {
                log.error("队列线程被中断，错误信息：", e);
                Thread.currentThread().interrupt();
            }
        };

        // 启动单个线程来处理队列
        executor.execute(runnable);
    }
}
