package com.xcky.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.xcky.config.OssConfig;
import com.xcky.service.OssFileService;
import com.xcky.util.Constants;
import com.xcky.util.StringUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS文件服务实现类
 *
 * @author lbchen
 */
@Service
@Slf4j
public class OssFileServiceImpl implements OssFileService {
    @Autowired
    private OssConfig ossConfig;

    @Override
    public String uploadUserHeadImg(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        //JDK8新特性写法，构建路径
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String folder = dtf.format(ldt);

        String extension = originalFilename.substring(originalFilename.lastIndexOf(Constants.DOT));
        String fileName = StringUtil.generateNonceNum("userHead_", 20);
        //在oss上创建文件夹test路径
        String newFileName = "test/" + folder + "/" + fileName + extension;
        OSS ossClient = null;
        try {
            //创建oss对象
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult result = ossClient.putObject(bucketName, newFileName, file.getInputStream());
            //返回访问路径
            if (null != result) {
                return "https://" + bucketName + "." + endpoint + "/" + newFileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ossClient) {
                // 关闭OSS服务
                ossClient.shutdown();
            }
        }
        return "";
    }
}
