package com.xcky.schedule;

import com.xcky.annotation.ScheduleJobAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 打印hello的任务
 *
 * @author lbchen
 */
@Component
@Slf4j
public class PrintHelloJob {
    
    /**
     * 打印hello
     */
    @ScheduleJobAnnotation
    public void printHello() {
        log.info("hello world");
    }
}
