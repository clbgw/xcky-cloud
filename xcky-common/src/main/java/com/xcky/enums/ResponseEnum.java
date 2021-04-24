package com.xcky.enums;

import lombok.Getter;

/**
 * 响应枚举类
 *
 * @author lbchen
 */
@Getter
public enum ResponseEnum {
    /**
     * 200-成功
     */
    SUCCESS(0,"SUCCESS"),
    /**
     * 200-成功
     */
    OK(200, "OK"),
    /**
     * 1001-登录状态失效
     */
    LOGOUT(1001,"登录状态失效"),
    /**
     * 登录超时
     */
    LOGIN_TIMEOUT(1002,"登录超时"),

    /**
     * 用户名称已经被使用
     */
    USERNAME_EMAIL_HAD_USED(1003,"该邮箱已被注册"),
    USERNAME_PHONE_HAD_USED(1003,"该手机号已被注册"),
    USERNAME_NAME_HAD_USED(1003,"该用户名已被注册"),

    /**
     * 403-参数错误
     */
    PARAM_ERROR(403, "请填写参数"),
    /**
     * 4033-参数错误
     */
    NOT_SELECT_DATA(4033, "未选中数据"),
    /**
     * 4031-参数状态类型错误
     */
    PARAM_STATUS_TYPE_ERROR(4031, "参数状态类型错误"),
    /**
     * 4032-参数不完整
     */
    PARAM_NOT_ENOUGH(4032, "参数不完整"),
    /**
     * 网络错误
     */
    NETWORK_ERROR(4000, "网络错误"),
    /**
     * 500-服务器错误
     */
    SERVER_ERROR(500, "SERVER ERROR"),
    /**
     * 暂无数据
     */
    NO_DATA(700, "暂无数据"),
    /**
     * 服务器配置文件有误
     */
    CONFIG_ERROR(545, "请检查服务器应用配置"),
    /**
     * 新增或更新失败
     */
    INSERT_OR_UPDATE_ERROR(901, "新增或更新失败"),
    /**
     * 新增失败
     */
    INSERT_ERROR(902, "新增失败"),
    /**
     * 更新失败
     */
    UPDATE_ERROR(903, "更新失败"),
    /**
     * 删除失败
     */
    DELETE_ERROR(904, "删除失败"),
    /**
     * 水印信息错误
     */
    WATER_MARK_ERROR(905, "水印信息错误"),


    /////////////////// 订单模块 //////////////////
    /**
     * 订单中的商品不能为空
     */
    GOODS_EMPTY(10022, "订单中的商品不能为空"),
    /**
     * 订单数量错误
     */
    ORDER_NUM_ERROR(10023, "订单数量错误"),
    /**
     * 订单数量错误
     */
    ORDER_AMOUNT_ERROR(10024, "订单合计数量错误"),
    /**
     * 订单号不存在
     */
    ORDER_NO_NOT_EXIST(11001, "订单号不存在"),
    /**
     * 下单人和付款人不一致
     */
    ORDER_PAY_USER_VALID(11002, "下单人和付款人不一致"),
    /**
     * 订单明细不存在
     */
    ORDER_DETAIL_NOT_EXIST(11003, "订单明细不存在"),
    /**
     * 库存不足
     */
    STORE_NOT_ENOUGH(10025, "商品库存不足"),
    /**
     * 超出单次可购数量
     */
    PEER_STORE_OUT_OF_RANGE(10026, "超出单次可购数量"),
    /**
     * 超出可购买时间区间
     */
    BUY_TIME_OUT_OF_RANGE(10026, "超出可购买时间区间"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(10027, "用户不存在"),

    /**
     * 账号或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(10127, "账号或密码错误"),
    /**
     * 邮箱或密码错误
     */
    EMAIL_OR_PASSWORD_ERROR(10227, "邮箱或密码错误"),
    /**
     * 手机或密码错误
     */
    PHONE_OR_PASSWORD_ERROR(10327, "手机或密码错误"),
    /**
     * 手机或密码错误
     */
    USER_SHIPPING_ERROR(10427, "用户收货地址有误"),
    /**
     * 系统扣减库存失败
     */
    DIS_STORE_FAIL(10028, "系统扣减库存失败"),
    /**
     * 新增订单信息失败
     */
    SAVE_ORDER_FAIL(10029, "新增订单信息失败"),
    /**
     * 新增订单明细信息失败
     */
    SAVE_ORDER_DETAIL_FAIL(10030, "新增订单明细信息失败"),
    /**
     * 所选的优惠券有误
     */
    INVALID_COUPON_SELECTED(10031, "所选的优惠券有误"),
    /**
     * 扣减优惠券失败-更新优惠券状态
     */
    UPDATE_COUPON_USE_STATUS(10032, "扣减优惠券失败"),
    /**
     * 领券失败了
     */
    GET_COUPON_FAIL(10033, "领券失败了"),
    /**
     * 优惠券不存在
     */
    COUPON_NOT_EXIST(10034, "优惠券不存在"),
    /**
     * 优惠券已下架
     */
    COUPON_HAD_OFF_SHELF(10035, "优惠券已下架"),
    /**
     * 优惠券已删除
     */
    COUPON_HAD_DELETED(10036, "优惠券已删除"),
    /**
     * 优惠券目前不可领取
     */
    COUPON_NOT_GET(10037, "优惠券目前不可领取"),
    /**
     * 未选择收货地址
     */
    NO_SELECTED_SHIPPING(10038, "未选择收货地址"),
    /**
     * 获取券码失败
     */
    GET_COUPON_NO_FAIL(10039, "获取券码失败"),
    /**
     * 获取券码失败
     */
    HAD_NO_USED_COUPON(10040, "您还有未使用的该券"),
    /**
     * 用户积分不足
     */
    NOT_ENOUGH_INTEGRAL(10041, "用户积分不足"),
    /**
     * 商品不可购买
     */
    GOODS_STATUS_NOT_ENABLE(10042, "有商品不可购买"),
    /**
     * 商品可购次数限制
     */
    GOODS_PEER_NUM_LIMIT(10043, "商品可购次数限制"),
    /**
     * 解密失败
     */
    DECODE_FAIL(10044, "解密失败"),
    /**
     * 调用微信支付统一下单接口失败
     */
    CALL_WXPAY_ERROR(11004, "调用微信支付统一下单接口失败"),
    /**
     * 今日已经签到过了
     */
    TODAY_HAD_CHECKIN(11005, "今日已经签到过了"),
    /**
     * 签到订阅失败，请稍后重试！
     */
    HAD_SUBSCRIPT_CHECKIN(11006, "签到订阅失败，请稍后重试！"),
    /**
     * TOKEN失效或者不存在
     */
    TOKEN_IS_NOT_EXIST(11007,"TOKEN失效或者不存在"),
    /**
     * 用户会话密钥不存在
     */
    USER_SESSION_KEY_IS_NOT_EXIST(11008,"用户会话密钥不存在"),
    /**
     * 确认密码与密码不一致
     */
    NOT_SAME_PASSWORD(11009,"确认密码与密码不一致"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(11010,"旧密码错误"),

    /**
     * 新旧密码不能一致
     */
    PASSWORD_SAME_ERROR(11011,"新旧密码不能一致"),
    /**
     * 内容涉及敏感信息
     */
    MSG_DANGER(11012,"内容涉及敏感信息"),
    /**
     * 接口未被授权
     */
    API_UNAUTH(11014,"接口未被授权"),
    /**
     * TOKEN不合法
     */
    TOKEN_IS_INVALID(11015,"TOKEN不合法"),


    /**
     * 试卷不存在
     */
    PAPER_NOT_EXIST(21001,"试卷不存在"),

    /**
     * 该试卷考试时间未到
     */
    PAPER_NOT_BEGIN(21002,"该试卷考试时间未到"),
    /**
     * 该试卷考试时间已过
     */
    PAPER_HAD_END(21003,"该试卷考试时间已过"),
    /**
     * 该试卷未启用
     */
    PAPER_NOT_USE(21004,"该试卷未启用"),
    /**
     * 考试已经结束
     */
    PAPER_ANSWER_LIMIT_TIME(21005,"考试已经结束"),


    ;
    /**
     * 返回值编码
     */
    private final Integer code;
    /**
     * 返回值描述
     */
    private final String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "{\"code\"=" + code +", \"msg\"=\"" + msg + "\"}";
    }

}
