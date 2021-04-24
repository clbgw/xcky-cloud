package com.xcky.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 任务调度配置实体
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class CronEntity {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 任务调度名称
     */
    private String cronName;
    /**
     * 任务调度周期
     */
    private String cron;
    /**
     * 周期状态(0-需要开始,1-需要停止)
     */
    private String status;
    /**
     * 任务调度描述
     */
    private String remark;
    /**
     * 类的全路径名称
     */
    private String className;
    /**
     * 方法名称
     */
    private String methodName;

    private String isRun;

    private String errMsg;
}
