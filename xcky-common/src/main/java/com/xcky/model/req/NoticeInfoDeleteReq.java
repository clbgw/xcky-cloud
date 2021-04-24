package com.xcky.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 删除消息内容请求参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class NoticeInfoDeleteReq extends LongIdsReq {

    /******* 前端不必传的参数 *******/
    /**
     * 删除时间
     */
    private Date deleteTime;
}
