package com.xcky.util.encry;

import com.xcky.util.Constants;
import com.xcky.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * MD5工具类
 *
 * @author lbchen
 */
public class Md5Util {

    /**
     * 生成 MD5
     *
     * @param str 待处理字符串数据
     * @return MD5结果
     */
    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance(Constants.MD5);
            byte[] array = md.digest(str.getBytes(Constants.CHARSET));
            return Util.byteToHex(array);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件/文件夹的MD5
     *
     * @param path 文件或文件夹的绝对路径
     * @return MD5字符串
     */
    public static String getMd5(String path) {
        File filePath = new File(path);
        if (filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            List<String> md5s = new ArrayList<>(16);
            for (File file : files) {
                String md5Temp = getMd5(file.getAbsolutePath());
                if(!StringUtils.isEmpty(md5Temp)) {
                    md5s.add(md5Temp);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String md5Sub : md5s) {
                stringBuilder = stringBuilder.append(md5Sub);
            }
            return encode(stringBuilder.toString());
        } else {
            return encodePath(path);
        }
    }

    /**
     * 获取文件的MD5
     *
     * @param absolutePath 文件或文件夹的绝对路径
     * @return MD5字符串
     */
    private static String encodePath(String absolutePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(absolutePath);
            return DigestUtils.md5Hex(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
