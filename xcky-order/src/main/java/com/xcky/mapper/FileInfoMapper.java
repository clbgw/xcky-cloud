package com.xcky.mapper;

import com.xcky.model.entity.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * 文件信息数据访问接口
 *
 * @author lbchen
 */
public interface FileInfoMapper {
    /**
     * 根据map查询文件信息列表
     *
     * @param map map条件
     * @return 文件信息列表
     */
    List<FileInfo> selectFileInfoPageByMap(Map<String, Object> map);
}
