package com.xcky.controller;

import com.xcky.model.resp.R;
import com.xcky.service.FileInfoService;
import com.xcky.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件信息控制器
 *
 * @author lbchen
 */
@RestController
public class FileInfoController {
    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 获取文件列表
     *
     * @return 响应对象
     */
    @GetMapping("/list")
    public R getFileInfoPage() {
        Map<String, Object> map = new HashMap<>(4);
        return ResponseUtil.ok(fileInfoService.getFileInfoPageByMap(map));
    }
}
