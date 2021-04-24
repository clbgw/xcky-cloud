package com.xcky.convert;

import com.xcky.model.entity.UserInfo;
import com.xcky.model.req.UserInfoUpdateReq;
import org.springframework.beans.BeanUtils;

/**
 * 用户基本信息转化类
 *
 * @author lbchen
 */
public class UserInfoConvert {
    
    /**
     * 通过更新请求参数获取实体类
     *
     * @param userInfoUpdateReq 用户基本信息更新请求参数
     * @return 用户基本信息
     */
    public static UserInfo getUserInfoByUpdateReq(UserInfoUpdateReq userInfoUpdateReq) {
        if (null == userInfoUpdateReq) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoUpdateReq, userInfo);
        return userInfo;
    }
}
