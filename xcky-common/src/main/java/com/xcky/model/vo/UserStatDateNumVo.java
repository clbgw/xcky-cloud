package com.xcky.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户日期数量统计值对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class UserStatDateNumVo {
    /**
     * 日期字符串:yyyy-MM-dd
     */
    private String dateStr;
    /**
     * 用户数量
     */
    private Integer num;
}
