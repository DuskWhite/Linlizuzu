package com.xyxy.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Component
public class MypPicConfig implements WebMvcConfigurer {
    ApplicationHome applicationHome = new ApplicationHome(this.getClass());
    private String filePath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath()+"\\src\\main\\resources\\static\\image/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:"+filePath+"/");
    }
}
