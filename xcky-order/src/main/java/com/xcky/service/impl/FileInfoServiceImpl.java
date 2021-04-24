package com.xcky.service.impl;

import com.xcky.mapper.FileInfoMapper;
import com.xcky.model.entity.FileInfo;
import com.xcky.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 文件信息服务实现类
 *
 * @author lbchen
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public List<FileInfo> getFileInfoPageByMap(Map<String, Object> map) {
        return fileInfoMapper.selectFileInfoPageByMap(map);
    }
}
