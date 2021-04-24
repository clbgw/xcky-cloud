package com.xcky.mapper;

import com.xcky.model.entity.CronEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 周期调度数据访问接口
 *
 * @author lbchen
 */
public interface CronMapper {

    /**
     * 根据map条件查询任务调度列表
     *
     * @param map map条件
     * @return 任务调度列表
     */
    List<CronEntity> selectCronEntityByMap(Map<String, Object> map);

    /**
     * 更新任务调度状态
     *
     * @param className  类名
     * @param methodName 方法名
     * @param isRun      是否在运行，1-是，0-否
     * @param errMsg     错误描述信息
     * @return 影响行数
     */
    Integer updateCronStatus(@Param("className") String className, @Param("methodName") String methodName,
                             @Param("isRun") String isRun, @Param("errMsg") String errMsg);
}
