package com.jiawa.wiki.task;

import com.jiawa.wiki.service.WsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  @author KangJunJie
 */
@Component(ExecuteQueueTaskService.TASK_01)
@Slf4j
public class ExecuteQueueTaskServiceImpl implements ExecuteQueueTaskService {
    @Resource
    public WsService wsService;


    @Override
    public void execute(String params) {
        wsService.sendInfo("【" + params + "】被点赞！");
        log.info("任务执行...{}", params);
    }
}
