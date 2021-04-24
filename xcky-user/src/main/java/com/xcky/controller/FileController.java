package com.xcky.controller;

import com.xcky.model.resp.R;
import com.xcky.service.OssFileService;
import com.xcky.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件控制器
 *
 * @author lbchen
 */
@RestController
@Slf4j
public class FileController {
    @Autowired
    private OssFileService ossFileService;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 基本响应对象
     */
    @PostMapping("/uploadFile")
    public R uploadFile(@RequestPart("file") MultipartFile file) {
        String fileUrl = ossFileService.uploadUserHeadImg(file);
        return ResponseUtil.ok(fileUrl);
    }
}
