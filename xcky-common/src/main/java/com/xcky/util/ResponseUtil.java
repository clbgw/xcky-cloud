package com.xcky.util;

import com.github.pagehelper.PageInfo;
import com.xcky.enums.ResponseEnum;
import com.xcky.model.resp.R;
import java.util.List;

/**
 * 返回对象工具类
 *
 * @author lbchen
 */
public class ResponseUtil {
    /**
     * 无返回值成功对象
     *
     * @return 返回对象
     */
    public static R ok() {
        return new R();
    }


    /**
     * 返回值为分页对象
     * @param pageInfo 分页数据
     * @return 返回对象
     */
    public static R page(PageInfo<?> pageInfo) {
        R r = new R();
        if (null != pageInfo) {
            r.setData(pageInfo.getList());
            r.setCount(pageInfo.getTotal());
        }
        return r;
    }

    /**
     * 返回值为分页对象
     *
     * @return 返回对象
     */
    public static R page(List<?> list) {
        R r = new R();
        r.setData(list);
        r.setCount(Long.valueOf(list.size()));
        return r;
    }


    /**
     * 有返回值成功对象
     *
     * @param data 返回值
     * @return 返回对象
     */
    public static R ok(Object data) {
        return new R(data);
    }

    /**
     * 无返回值失败对象
     *
     * @param responseEnum 响应枚举
     * @return 返回对象
     */
    public static R fail(ResponseEnum responseEnum) {
        return new R(responseEnum);
    }

    /**
     * 有返回值失败对象
     *
     * @param responseEnum 响应枚举
     * @param data         返回值
     * @return 返回对象
     */
    public static R fail(ResponseEnum responseEnum, Object data) {
        return new R(responseEnum, data);
    }
}
