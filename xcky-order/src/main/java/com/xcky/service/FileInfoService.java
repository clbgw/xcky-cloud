package com.xcky.service;

import com.xcky.model.entity.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * 文件信息服务接口
 *
 * @author lbchen
 */
public interface FileInfoService {
    /**
     * 根据map条件获取文件信息列表
     *
     * @param map map条件
     * @return 文件信息列表
     */
    List<FileInfo> getFileInfoPageByMap(Map<String, Object> map);
}
