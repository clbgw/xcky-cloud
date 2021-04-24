package com.xcky.model.resp;

import com.xcky.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class R {
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回提示信息
     */
    private String msg;
    /**
     * 返回值
     */
    private Object data;
    /**
     * 分页数据量
     */
    private Long count;

    /**
     * 默认无返回值成功返回值对象
     */
    public R() {
        this(ResponseEnum.OK, null);
    }

    /**
     * 默认有返回值成功返回值对象
     *
     * @param data 返回值
     */
    public R(Object data) {
        this(ResponseEnum.OK, data);
    }

    /**
     * 无返回值的通用返回对象
     *
     * @param responseEnum 响应枚举类
     */
    public R(ResponseEnum responseEnum) {
        this(responseEnum, null);
    }

    /**
     * 有返回值的通用返回对象
     *
     * @param responseEnum 响应枚举类
     * @param data         返回值
     */
    public R(ResponseEnum responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.data = data;
    }

}
