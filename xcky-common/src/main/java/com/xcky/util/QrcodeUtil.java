package com.xcky.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * 二维码生成工具
 *
 * @author lbchen
 */
public class QrcodeUtil {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    
    private QrcodeUtil() {
    }
    
    /**
     * 根据二维矩阵的碎片 生成对应的二维码图像缓冲
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @return 二维码图像缓冲
     * @see com.google.zxing.common.BitMatrix
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
    
    /**
     * 二维码生成文件
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @param format 格式
     * @param file   保持的文件地址
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) {
        BufferedImage image = toBufferedImage(matrix);
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 二维码生成流
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @param format 格式
     * @param outputStream 保持的文件输出流
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream outputStream) {
        BufferedImage image = toBufferedImage(matrix);
        try {
            ImageIO.write(image, format, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 二维码信息写成JPG文件
     *
     * @param content  二维码信息
     * @param filePath 文件路径
     */
    public static void writeInfoToJpgFile(String content, String filePath) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>(8);
        hints.put(EncodeHintType.CHARACTER_SET, Constants.CHARSET);
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, 250, 250, hints);
            QrcodeUtil.writeToFile(bitMatrix, "jpg", new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 二维码信息写成JPG BufferedImage
     *
     * @param content 二维码信息
     * @return JPG BufferedImage
     */
    public static BufferedImage writeInfoToJpgBuff(String content) {
        BufferedImage re = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map<EncodeHintType, String> hints = new HashMap<>(8);
        hints.put(EncodeHintType.CHARACTER_SET, Constants.CHARSET);
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE,
                    250,
                    250,
                    hints);
            re = QrcodeUtil.toBufferedImage(bitMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }
}
