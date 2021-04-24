package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 更新简历基本信息请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class JlBasicInfoUpdateReq {
    /**
     * 简历主键ID(更新必传该字段)
     */
    private Long jlId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 联系电话
     */
    private String tel;
    /**
     * 工作年限
     */
    private String exp;
    /**
     * 相片URL
     */
    private String photo;
    /**
     * 自我介绍
     */
    private String job;
    
    /**
     * 期望工作地
     */
    private String address;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age = 18;
    /**
     * 自我介绍
     */
    private String introduction;
    /************* 附加属性 *************/
    /**
     * 微信OPENID
     */
    private String openid;
    
    /**
     * 用户账号ID
     */
    private Long userId;
}
