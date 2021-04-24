package com.xcky.model.resp;

import com.xcky.model.entity.NoticeDistributeConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息分发配置响应对象
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeDistributeConfigResp extends NoticeDistributeConfig {
    /******** 附加属性 **********/
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 消息模板内容
     */
    private String messageTemplate;
}
