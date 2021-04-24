package com.xcky.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xcky.annotation.LoginAuthAnnotation;
import com.xcky.enums.ResponseEnum;
import com.xcky.exception.BizException;
import com.xcky.util.Constants;
import com.xcky.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * 后台登录切面
 *
 * @author lbchen
 */
@Aspect
@Component
@Slf4j
public class AdminLoginAspect {
    /**
     * 定义切面的点
     */
    @Pointcut("execution(* com.xcky.controller.**.*(..))")
    public void loginValid() {
    }

    /**
     * 定义切面的处理逻辑
     *
     * @param pjp proceedingJoinPoint对象
     * @return 接口返回值
     * @throws ClassNotFoundException 反射时类找不到
     */
    @Around(value = "loginValid()")
    public Object signVerification(ProceedingJoinPoint pjp) throws ClassNotFoundException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (null == attributes) {
            log.error("attributes 对象为空");
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        // 获取请求头中的token
        String tokenString = request.getHeader(Constants.ACCESS_TOKEN_HEADER);
        String newToken = "";
        // 解析token
        Claims claims = JwtUtil.parseJWT(tokenString);
        // 请求头中没有合法的token
        if (null == claims || StringUtils.isEmpty("" + claims.get(Constants.TOKEN_FIELD))) {
            boolean isExist = false;
            String targetName = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();
            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    isExist = method.isAnnotationPresent(LoginAuthAnnotation.class);
                    break;
                }
            }
            // 如果存在则代表有登录需求
            if (isExist) {
                throw new BizException(ResponseEnum.LOGOUT, null);
            }
        } else {
            String username = "" + claims.get(Constants.TOKEN_FIELD);
            //TODO 判断redis中有存在并且存在的token是否一致
            log.info("现在登录的用户:" + username);
        }
        try {
            Object result = pjp.proceed();
            // 后置处理逻辑
            if (null != claims) {
                String username = "" + claims.get(Constants.TOKEN_FIELD);
                newToken = JwtUtil.createJWT(username);
                //TODO 在redis中设置用户的token
                response.setHeader(Constants.ACCESS_TOKEN_HEADER, newToken);
            }
            return result;
        } catch (BizException e) {
            throw e;
        } catch (Throwable e) {
            e.printStackTrace();
            throw new BizException(ResponseEnum.SERVER_ERROR, null);
        }
    }
}
