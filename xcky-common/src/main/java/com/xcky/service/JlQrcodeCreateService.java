package com.xcky.service;

import com.xcky.model.entity.JlQrcodeCreate;

import java.util.List;
import java.util.Map;

/**
 * 微信小程序二维码创建记录服务接口
 *
 * @author lbchen
 */
public interface JlQrcodeCreateService {
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
    List<JlQrcodeCreate> getJlQrcodeCreateByMap(Map<String, Object> map);
    
    /**
     * 通过主键ID查询微信小程序二维码创建记录
     *
     * @param id 主键ID
     * @return 微信小程序二维码创建记录
     */
    JlQrcodeCreate getJlQrcodeCreateById(Long id);
}
