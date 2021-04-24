package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.enums.CommonTimeAreaTypeEnum;
import com.xcky.enums.ResponseEnum;
import com.xcky.model.resp.R;
import com.xcky.util.Constants;
import com.xcky.util.ResponseUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取枚举类的数据
 *
 * @author lbchen
 */
@RestController
public class ReadEnumsController {
    /**
     * 读取响应的枚举类
     *
     * @return 响应对象
     */
    @PostMapping("/enums/responseEnum")
    public R getResponseEnum() {
        ResponseEnum[] responseEnums = ResponseEnum.values();
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject;
        for (ResponseEnum item : responseEnums) {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.NAME_FIELD, item.name());
            jsonObject.put(Constants.CODE_FIELD, item.getCode());
            jsonObject.put(Constants.MSG_FIELD, item.getMsg());
            list.add(jsonObject);
        }
        return ResponseUtil.page(list);
    }

    /**
     * 读取公共时间范围类型的枚举类
     *
     * @return 响应对象
     */
    @PostMapping("/enums/commonTimeAreaTypeEnum")
    public R getCommonTimeAreaTypeEnum() {
        CommonTimeAreaTypeEnum[] commonTimeAreaTypeEnums = CommonTimeAreaTypeEnum.values();
        List<JSONObject> list = new ArrayList<>();
        JSONObject jsonObject;
        for (CommonTimeAreaTypeEnum item : commonTimeAreaTypeEnums) {
            jsonObject = new JSONObject();
            jsonObject.put(Constants.CODE_FIELD, item.getCode());
            jsonObject.put(Constants.DESC_FIELD, item.getDesc());
            list.add(jsonObject);
        }
        return ResponseUtil.page(list);
    }


}
