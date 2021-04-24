package com.xcky.enums;

import lombok.Getter;

/**
 * 考试模式枚举接口
 *
 * @author lbchen
 */
@Getter
public enum ExamModeEnum {
    /**
     * 考试
     */
    EXAM(0, "考试"),

    /**
     * 干净
     */
    CLEAN(1, "干净"),

    /**
     * 回顾
     */
    REVIEW(2, "回顾"),

    ;

    /**
     * 商品信息状态编码
     */
    private final Integer code;
    /**
     * 商品信息状态描述
     */
    private final String desc;

    ExamModeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
