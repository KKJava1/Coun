package com.jiawa.wiki.task;

/**
 *  @author KangJunJie
 */
//@Configuration
//@Slf4j
//public class QueueTaskConfig {
//
//
//    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Autowired
//    private QueueTaskService queueTaskService;
//
//    private final Executor executor = new ThreadPoolExecutor(CORE_SIZE << 1,
//            CORE_SIZE << 2,
//            1, TimeUnit.MINUTES,
//            new LinkedBlockingQueue<>(4096),
//            ThreadFactoryBuilder.create().setNameFormat("-io-executor-").get(),
//            new ThreadPoolExecutor.CallerRunsPolicy());
//
//
//    private final Executor tempExecutor = new ThreadPoolExecutor(CORE_SIZE,
//            CORE_SIZE + 1,
//            1, TimeUnit.MINUTES,
//            new LinkedBlockingQueue<>(4096),
//            ThreadFactoryBuilder.create().setNameFormat("-temp-executor-").get(),
//            new ThreadPoolExecutor.CallerRunsPolicy());
//
//    @PostConstruct
//    public void queueTask() {
//
//        Runnable runnable = () -> {
//            try {
//                RBlockingQueue<QueueTask> blockingFairQueue = redissonClient.getBlockingQueue(ExecuteQueueTaskService.TASK_01);
//                //noinspection InfiniteLoopStatement
//                while (true) {
//                    final QueueTask take = blockingFairQueue.take();
//
//                    executor.execute(() -> queueTaskService.executeTask(take));
//                }
//
//            } catch (InterruptedException e) {
//                log.error("Interrupted!" + e.getMessage(), e);
//                Thread.currentThread().interrupt();
//            }
//        };
//        tempExecutor.execute(runnable);
//    }
//
//}
