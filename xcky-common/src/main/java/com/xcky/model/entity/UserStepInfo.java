package com.xcky.model.entity;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户步数信息
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserStepInfo {
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户openid
     */
    private String openid;
    /**
     * 步数
     */
    private Integer step;

    /**
     * 步数对应的日期
     */
    private Date stepDate;
}
