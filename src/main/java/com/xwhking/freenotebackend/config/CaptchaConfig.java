package com.xwhking.freenotebackend.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties properties = new Properties();
        // 配置相关属性，如图片宽度、高度、字符长度等
        // 可根据需要进行自定义配置
         properties.setProperty("kaptcha.image.width", "150");
         properties.setProperty("kaptcha.image.height", "50");
        // ...

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
