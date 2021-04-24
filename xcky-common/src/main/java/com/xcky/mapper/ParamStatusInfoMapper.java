package com.xcky.mapper;

import com.xcky.model.entity.ParamStatusInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 参数状态信息对象映射接口
 *
 * @author lbchen
 */
public interface ParamStatusInfoMapper {
    /**
     * 根据主键查询参数状态对象
     *
     * @param type
     * @param userId
     * @return
     */
    ParamStatusInfo selectStatusByKey(@Param("type") String type, @Param("userId") Long userId);
    
    /**
     * 新增参数状态
     *
     * @param paramStatusInfo 参数状态信息
     * @return 新增行数
     */
    Integer insertParamStatusInfo(ParamStatusInfo paramStatusInfo);
    
    /**
     * 更新参数状态
     *
     * @param paramStatusInfo 参数状态信息
     * @return 更新行数
     */
    Integer updateParamStatusInfo(ParamStatusInfo paramStatusInfo);
}
