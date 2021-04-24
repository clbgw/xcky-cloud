package com.xcky.exception.handler;

import com.alibaba.fastjson.JSONObject;
import com.xcky.exception.BizException;
import com.xcky.model.resp.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 * 注意事项：
 * <ol>
 *     <li>类的定义上应该有@RestControllerAdvice注解【org.springframework.web.bind.annotation.RestControllerAdvice】</li>
 *     <li>异常处理方法上应该有ExceptionHandler注解 【org.springframework.web.bind.annotation.ExceptionHandler】并指明value属性值</li>
 *     <li>异常处理方法的入参应该就是@ExceptionHandler注解中value指明的异常对象</li>
 * </ol>
 *
 * @author lb-chen
 * @creation 2020年10月9日
 */
@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {
    
    /**
     * controller参数异常
     *
     * @param e 异常对象
     * @return 响应对象
     */
    @ExceptionHandler(value = BizException.class)
    public R parameterError(BizException e) {
        R r = e.getR();
        log.error(JSONObject.toJSONString(r));
        return r;
    }
}
