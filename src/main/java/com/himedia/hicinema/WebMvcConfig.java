package com.himedia.hicinema;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 파일을 업로드시 서버의 경로와 물리적인 경로를 매핑 설정 


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    #리소스 업로드 경로
//    uploadPath=file:///C:/hicinema/
    @Value("${uploadPath}")   //application.properties 파일의 변수를 로딩
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")		// 서버에서 처리되는 경로
                .addResourceLocations(uploadPath + "/upload/");		//실제 이미지가 저장되는 물리적 경로
    }




}