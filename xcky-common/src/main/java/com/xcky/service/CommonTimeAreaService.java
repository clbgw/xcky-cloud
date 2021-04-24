package com.xcky.service;

import com.github.pagehelper.PageInfo;
import com.xcky.model.entity.CommonTimeArea;
import java.util.Map;

/**
 * 公共时间范围服务接口
 *
 * @author lbchen
 */
public interface CommonTimeAreaService {
    /**
     * 更新公共时间范围
     *
     * @param commonTimeArea 公共时间范围
     * @return 更新行数
     */
    Integer updateCommonTimeArea(CommonTimeArea commonTimeArea);

    /**
     * 根据主键ID查询公共时间范围
     *
     * @param id 主键ID
     * @return 公共时间范围
     */
    CommonTimeArea getCommonTimeAreaById(Long id);

    /**
     *
     *
     * @param map
     * @return
     */
    /**
     * 根据map条件查询公共时间范围列表
     *
     * @param map  map条件
     * @param page 页码
     * @param size 每页显示大小
     * @return 公共时间范围分页列表
     */
    PageInfo<CommonTimeArea> getCommonTimeAreaPageInfoByMap(Map<String, Object> map, Integer page, Integer size);
}
