package com.xcky.mapper;

import com.xcky.model.entity.UserJlRela;
import com.xcky.model.vo.UserJlRelaVo;
import java.util.List;
import java.util.Map;

/**
 * 用户简历关系数据访问接口
 *
 * @author lbchen
 */
public interface UserJlRelaMapper {
    /**
     * 新增用户简历关系
     *
     * @param userJlRela 用户简历关系实体类
     * @return 新增行数
     */
    Integer insertUserJlRela(UserJlRela userJlRela);

    /**
     * 更新用户简历关系
     *
     * @param userJlRela 用户简历关系实体类
     * @return 更新行数
     */
    Integer updateUserJlRela(UserJlRela userJlRela);

    /**
     * 根据map条件查询用户简历关系列表
     *
     * @param map map条件
     * @return 用户简历关系列表
     */
    List<UserJlRelaVo> selectUserJlRelaVoByMap(Map<String, Object> map);
}
