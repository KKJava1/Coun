package com.jiawa.wiki.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  @author KangJunJie
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueTask implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 实际任务
     */
    private String task;

    /**
     * 触发时间
     */
    private LocalDateTime startTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueueTask queueTask = (QueueTask) o;
        return Objects.equals(getType(), queueTask.getType()) &&
                Objects.equals(getTask(), queueTask.getTask());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getTask());
    }
}
