package com.fasterapp.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tony on 2021/9/2.
 */
@Configuration
@ConfigurationProperties(prefix = "fasterapp", ignoreUnknownFields = true)
@Data
public class FasterappProperties {
	//日志输出配置
	private Logger logger;

	@Data
	public class Logger{
		private String headers;
	}
}
