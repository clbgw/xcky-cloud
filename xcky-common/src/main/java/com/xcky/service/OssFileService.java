package com.xcky.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件服务接口
 *
 * @author lbchen
 */
public interface OssFileService {
    /**
     * 上传用户头像
     *
     * @param file 文件对象
     * @return 访问文件URL
     */
    String uploadUserHeadImg(MultipartFile file);
}
