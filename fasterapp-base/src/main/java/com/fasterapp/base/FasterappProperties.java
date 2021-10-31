package com.fasterapp.base;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tony on 2021/9/2.
 */

@Configuration
public class FasterappProperties {
	//日志输出配置
	public static String  logger_headers;


	@Value("${fasterapp.logger.headers:''}")
	public void setLoggerHeaders(String value){
		logger_headers = value;
	}

}
