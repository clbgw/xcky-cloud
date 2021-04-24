package com.xcky.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 *
 * @author lbchen
 */
@RestController
public class CaptcheController {
    @Autowired
    private DefaultKaptcha captchaProducer;

    @GetMapping("/getCode")
    public void getCaptcha(HttpServletResponse response) {
        String text = captchaProducer.createText();
        BufferedImage bufferedImage = captchaProducer.createImage(text);
        try (OutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
