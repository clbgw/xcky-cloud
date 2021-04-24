package com.xcky.controller;

import com.alibaba.fastjson.JSONObject;
import com.xcky.config.UnionPayConfig;
import com.xcky.model.resp.R;
import com.xcky.util.CertUtil;
import com.xcky.util.Constants;
import com.xcky.util.EntityUtil;
import com.xcky.util.ResponseUtil;
import com.xcky.util.RestTemplateUtil;
import com.xcky.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 银联云闪付控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class UnnionPayController {
    @Autowired
    private UnionPayConfig unionPayConfig;
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    @Autowired
    private CertUtil certUtil;

    @SuppressWarnings("unchecked")
	@GetMapping("/unionPay")
    public R unionNativePay() {
        Date nowDate = new Date();
        Map<String, String> contentData = new HashMap<>(32);
        //版本号 全渠道默认值
        contentData.put("version", unionPayConfig.getVersion());
        contentData.put("encoding", Constants.CHARSET);
        //签名方法
        contentData.put("signMethod", unionPayConfig.getSignMethod());
        //交易类型 01:消费
        contentData.put("txnType", "01");
        //交易子类 06：二维码消费
        contentData.put("txnSubType", "06");
        //填写000000
        contentData.put("bizType", "000000");
        //渠道类型 08手机
        contentData.put("channelType", "08");
        //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
        contentData.put("merId", unionPayConfig.getMchId());
        //接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）
        contentData.put("accessType", "0");
        String txnTime = new SimpleDateFormat("yyyyMMddHHmmss").format(nowDate);
        //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则
        contentData.put("orderId", StringUtil.generateNonceNum(txnTime, 6));
        //订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
        contentData.put("txnTime", txnTime);
        //交易金额 单位为分，不能带小数点
        contentData.put("txnAmt", "1");
        //境内商户固定 156 人民币
        contentData.put("currencyCode", "156");
        //C2B码,1-20位数字
        contentData.put("qrNo", "233");
        //终端号
        contentData.put("termId", "33");
        contentData.put("backUrl", "backUrl");

        Map<String, String> reqData = certUtil.sign(contentData, Constants.CHARSET);
        String requestAppUrl = unionPayConfig.getBackUrl();
        Map<String, Object> reqDatas = EntityUtil.entityToMap(reqData);
        ResponseEntity<String> responseEntity = restTemplateUtil.postReqByJson(requestAppUrl, reqDatas, String.class);
        String body = responseEntity.getBody();
        log.info(body);
        return ResponseUtil.ok(JSONObject.parseObject(body));
    }

    @RequestMapping("backRcvResponse")
    public R backRcvResponse(@RequestBody String body) {
        return ResponseUtil.ok(body);
    }
}
