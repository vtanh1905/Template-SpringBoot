package com.vtanh1905.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**") // Các URL sẽ bị ảnh hưởng
				.allowedOrigins("*") // Cho phép tên miền nào truy cập
				.allowedMethods("GET", "POST", "DELETE", "PUT") // Các method được phép sử dụng
				.allowCredentials(false) // Có lưu vào cookie không
				.maxAge(4800); // Setting thời gian luu vào bộ đệm
	}
}
