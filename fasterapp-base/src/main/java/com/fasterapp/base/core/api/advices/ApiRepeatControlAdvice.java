package com.fasterapp.base.core.api.advices;

import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 重复提交控制
 * Created by Tony on 2021/9/3.
 */
@Order(101)
@Aspect
@Component
public class ApiRepeatControlAdvice {
}
