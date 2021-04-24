package com.xcky.mapper;

import com.xcky.model.entity.UserStepInfo;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 用户步数信息数据访问接口
 *
 * @author lbchen
 */
public interface UserStepInfoMapper {
    /**
     * 查询单个用户步数信息
     *
     * @param stepDate 步数所对应的日期
     * @param openid   用户OPENID
     * @return 用户步数信息列表
     */
    List<UserStepInfo> selectUserStepOne(@Param("stepDate") Date stepDate, @Param("openid") String openid);

    /**
     * 新增用户步数信息
     *
     * @param userStepInfo 用户步数信息
     * @return 新增行数
     */
    Integer insertUserStepInfo(UserStepInfo userStepInfo);

    /**
     * 更新用户步数信息
     *
     * @param userStepInfo 用户步数信息
     * @return 更新行数
     */
    Integer updateUserStepInfo(UserStepInfo userStepInfo);
}
