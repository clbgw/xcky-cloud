package com.xcky.util;

import java.io.FileInputStream;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 文件头校验工具类
 *
 * @author lbchen
 */
@Slf4j
public class FileHeaderValidatorUtil {
    /**
     * 缓存文件头信息-文件头信息
     */
    public static final HashMap<String, String> mFileTypes = new HashMap<>(48);

    static {
        // images
        mFileTypes.put("424D", "bmp");
        mFileTypes.put("FFD8FF", "jpg");
        mFileTypes.put("FFD8FFE0", "jpeg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("49492A00", "tif");

        // CAD
        mFileTypes.put("41433130", "dwg");
        mFileTypes.put("38425053", "psd");
        mFileTypes.put("D0CF11E0", "doc");
        mFileTypes.put("504B0304", "docx");
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("57415645", "wav");
        mFileTypes.put("41564920", "avi");
        mFileTypes.put("2E524D46", "rm");
        mFileTypes.put("000001BA", "mpg");
        mFileTypes.put("000001B3", "mpg");
        mFileTypes.put("6D6F6F76", "mov");
        mFileTypes.put("4D546864", "mid");
        mFileTypes.put("4D5A9000", "exe/dll");
        mFileTypes.put("75736167", "txt");


        mFileTypes.put("3C3F786D6C", "xml");
        mFileTypes.put("68746D6C3E", "html");
        mFileTypes.put("1F8B08", "gz");
        // 日记本
        mFileTypes.put("7B5C727466", "rtf");
        mFileTypes.put("255044462D312E", "pdf");
        mFileTypes.put("3026B2758E66CF11", "asf");
        mFileTypes.put("5374616E64617264204A", "mdb");
        mFileTypes.put("252150532D41646F6265", "ps");
        // 邮件
        mFileTypes.put("44656C69766572792D646174653A", "eml");
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileType(String filePath) {
        String headerValue = getFileHeader(filePath);
        String fileHeaderType = mFileTypes.get(getFileHeader(filePath));
        if(StringUtils.isEmpty(fileHeaderType)) {
            log.error("文件头【{}】暂时找不到",headerValue);
            return "UNKNOW";
        }else {
            return mFileTypes.get(getFileHeader(filePath));
        }
    }

    /**
     * 根据文件路径获取文件头信息
     *
     * @param filePath 文件路径
     * @return 文件头信息
     */
    public static String getFileHeader(String filePath) {
        String value = null;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] b = new byte[4];
            fis.read(b, 0, b.length);
            value = Util.byteToHex(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        final String fileType = getFileType("C:\\document\\证件\\jdk.jpeg");
        System.out.println(fileType);
    }
}
