package com.xcky.model.req;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Long类型ID参数
 *
 * @author lbchen
 */
@Getter
@Setter
@ToString
public class LongIdsReq {
    /**
     * 主键ID列表
     */
    private List<Long> ids;
}
