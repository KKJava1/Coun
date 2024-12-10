package com.jiawa.wiki.task;


import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  @author KangJunJie
 */
@Slf4j
@Component
public class QueueTaskService {
    @Resource
    private RedissonClient redissonClient;

    @Resource
    private Map<String, ExecuteQueueTaskService> executeQueueTaskMap;

    /**
     * 执行
     *
     * @param queueTask 队列任务
     */
    public void executeTask(QueueTask queueTask) {
        try {
            String type = queueTask.getType();
            if (StringUtils.isBlank(type)) {
                log.error("当前任务的ID不对");
                return;
            }

            String task = queueTask.getTask();
            if (StringUtils.isBlank(task)) {
                log.error("任务对象不存在");
                return;
            }

            ExecuteQueueTaskService executeQueueTask = executeQueueTaskMap.get(type);
            if (executeQueueTask == null) {
                log.error("任务:{}对应的执行器不存在", type);
                return;
            }

            //调用执行方法
            executeQueueTask.execute(task);
        } catch (Exception e) {
            log.error("执行队列任务发生异常", e);
        }
    }

    public void addTask(QueueTask queueTask) {
        log.info("添加任务到队列中...时间:{},参数:{}", queueTask.getStartTime(), queueTask.getTask());
        RBlockingQueue<QueueTask> blockingFairQueue = redissonClient.getBlockingQueue(queueTask.getType());
        RDelayedQueue<QueueTask> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);

        //计算延时时间
        long between = ChronoUnit.SECONDS.between(LocalDateTime.now(), queueTask.getStartTime());
        delayedQueue.offer(queueTask, between, TimeUnit.SECONDS);
    }

    public void removeTask(QueueTask queueTask) {
        RBlockingQueue<QueueTask> blockingFairQueue = redissonClient.getBlockingQueue(queueTask.getTask());
        RDelayedQueue<QueueTask> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.remove(queueTask);
    }

    public void addTaskWithDelay(QueueTask queueTask, long delayInSec) {
        log.info("将任务添加到延迟队列，延迟时间：{}秒，任务类型：{}，任务内容：{}", delayInSec, queueTask.getType(), queueTask.getTask());

        RBlockingQueue<QueueTask> blockingFairQueue = redissonClient.getBlockingQueue(queueTask.getType());
        /**
         * RDelayedQueue：是一个延迟队列，它允许你将任务放入队列中，并设置一个延迟时间。
         * 在延迟时间到达之前，任务不会被消费，只有当延迟时间到期后，任务才会被移到 RBlockingQueue 中进行处理。
         */
        RDelayedQueue<QueueTask> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);

        // 将任务放入延迟队列中，延迟时间为传入的 delayInSec 秒
        delayedQueue.offer(queueTask, delayInSec, TimeUnit.SECONDS);
    }
}
