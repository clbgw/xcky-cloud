package com.xcky.mapper;

import com.xcky.model.entity.JlQrcodeCreate;

import java.util.List;
import java.util.Map;

/**
 * 微信小程序二维码创建记录对象映射接口
 *
 * @author lbchen
 */
public interface JlQrcodeCreateMapper {
    /**
     * 新增微信小程序二维码创建记录
     *
     * @param jlQrcodeCreate 微信小程序二维码创建记录
     * @return 新增行数
     */
    Integer insertJlQrcodeCreate(JlQrcodeCreate jlQrcodeCreate);
    
    /**
     * 更新微信小程序二维码创建记录
     *
     * @param jlQrcodeCreate 微信小程序二维码创建记录
     * @return 更新行数
     */
    Integer updateJlQrcodeCreate(JlQrcodeCreate jlQrcodeCreate);
    
    /**
     * 根据map条件查询微信小程序二维码创建记录列表
     *
     * @param map map条件
     * @return 微信小程序二维码创建记录列表
     */
    List<JlQrcodeCreate> selectJlQrcodeCreateByMap(Map<String, Object> map);
    
}
