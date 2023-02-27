package com.example.validationcodedemo.controller;

import com.example.validationcodedemo.common.Result;
import com.example.validationcodedemo.dto.UserLoginRequestJson;
import com.google.code.kaptcha.Producer;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码
 * @author qzz
 */
@RestController
public class ValidationCodeController {

    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private Producer kaptchaProducerMath;

    /**
     * 生成验证码图片
     * @param request
     * @param response
     */
    @RequestMapping("/createKaptchaCodeImg")
    public void createKaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control","no-store");
        response.setContentType("image/jpeg");

        //文本验证码
        String text = kaptchaProducer.createText();
        //图片验证码
        BufferedImage image = kaptchaProducer.createImage(text);
        //保存验证码到session
        request.getSession().setAttribute("kaptchaCode",text);

        ServletOutputStream outputStream = response.getOutputStream();
        //设置写出图片的格式
        ImageIO.write(image,"jpg",outputStream);
        //关闭输出流
        IOUtils.closeQuietly(outputStream);
    }

    /**
     * 生成验证码图片
     * @param request
     * @param response
     */
    @RequestMapping("/createKaptchaCodeImg2")
    public void createKaptchaCode2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control","no-store");
        response.setContentType("image/jpeg");

        //文本验证码
        String text = kaptchaProducer.createText();
        //图片验证码
        BufferedImage image = kaptchaProducerMath.createImage(text);
        //保存验证码到session
        request.getSession().setAttribute("kaptchaCodeMath",text);

        ServletOutputStream outputStream = response.getOutputStream();
        //设置写出图片的格式
        ImageIO.write(image,"jpg",outputStream);
        //关闭输出流
        IOUtils.closeQuietly(outputStream);
    }

    /**
     * 用户登录 校验验证码
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginRequestJson requestJson,HttpServletRequest request){
        Result result = new Result();
        //从session中获取验证码
        String kaptchaCode = String.valueOf(request.getSession().getAttribute("kaptchaCode"));
        //用户输入和session获取的验证码进行验证
        if (!requestJson.getCode().equals(kaptchaCode)){
            //验证码验证失败
            return Result.fail(10910,"验证码不正确，请重新输入");
        }
        //登录流程...（省略）
        return Result.success();
    }
}
