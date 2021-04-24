package com.xcky.exception;

import com.xcky.enums.ResponseEnum;
import com.xcky.model.resp.R;
import com.xcky.util.ResponseUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 业务异常对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class BizException extends RuntimeException {
	private static final long serialVersionUID = 302168868580115687L;
	/**
     * 失败的响应枚举对象
     */
    private ResponseEnum responseEnum;
    /**
     * 失败的返回数据
     */
    private Object data;
    
    /**
     * 业务异常构造器
     *
     * @param responseEnum 响应枚举对象
     * @param data         返回值
     */
    public BizException(ResponseEnum responseEnum, Object data) {
        this.responseEnum = responseEnum;
        this.data = data;
    }
    
    /**
     * 获得业务异常的响应对象
     *
     * @return 响应对象
     */
    public R getR() {
        return ResponseUtil.fail(this.responseEnum, this.data);
    }
}
