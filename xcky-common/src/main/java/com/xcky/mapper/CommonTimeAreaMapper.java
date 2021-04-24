package com.xcky.mapper;

import com.xcky.model.entity.CommonTimeArea;
import java.util.List;
import java.util.Map;

/**
 * 公共时间范围数据访问对象接口
 *
 * @author lbchen
 */
public interface CommonTimeAreaMapper {
    /**
     * 查询时间区域
     *
     * @param map
     * @return
     */
    List<CommonTimeArea> selectTimeAreaByMap(Map<String, Object> map);

    /**
     * 新增公共时间范围
     *
     * @param commonTimeArea 公共时间范围
     * @return 新增行数
     */
    Integer insertCommonTimeArea(CommonTimeArea commonTimeArea);

    /**
     * 更新公共时间范围
     *
     * @param commonTimeArea 公共时间范围
     * @return 更新行数
     */
    Integer updateCommonTimeArea(CommonTimeArea commonTimeArea);
}
