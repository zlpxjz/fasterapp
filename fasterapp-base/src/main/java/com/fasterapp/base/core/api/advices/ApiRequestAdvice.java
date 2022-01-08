package com.fasterapp.base.core.api.advices;

import com.fasterapp.base.FasterappProperties;
import com.fasterapp.base.annotations.ApiNoLog;
import com.fasterapp.base.core.api.ApiResponse;
import com.fasterapp.base.utils.ApiUtil;
import com.fasterapp.base.utils.JsonUtil;
import com.fasterapp.base.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * Api访问日志记录
 */
@Order(100)
@Slf4j(topic = "ApiRequestAdvice")
@Aspect
@Component
public class ApiRequestAdvice {
	@Autowired
	private FasterappProperties fasterappProperties;

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void executeApi() {
	}

	@Before(value = "executeApi()")
	public void doBefore(JoinPoint joinPoint) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		StringBuffer sb = new StringBuffer();
		sb.append("Url=").append(request.getRequestURI());

		//输出HTTP头信息
		boolean first = true;
		String loggableHeaders = FasterappProperties.logger_headers;
		if (!StringUtil.isNullOrBlank(loggableHeaders)) {
			sb.append(", Headers={");
			String[] headers = loggableHeaders.split(",");
			for (String header : headers) {
				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				sb.append(header).append("=").append(ApiUtil.getHeader(request, header));
			}
			sb.append("}");
		}

		//输出请求参数
		first = true;
		Object[] args = joinPoint.getArgs();
		if (args != null) {
			sb.append(", Parameters={");
			Parameter[] parameters = method.getParameters();
			for (int index = 0; index < parameters.length; index++) {
				if (parameters[index].isAnnotationPresent(ApiNoLog.class)) {
					continue;
				}

				Object arg = args[index];
				if (arg instanceof HttpServletRequest || arg instanceof HttpServletResponse || arg instanceof MultipartFile) {
					continue;
				}

				if (!first) {
					sb.append(",");
				} else {
					first = false;
				}
				sb.append("arg").append(index).append("=").append(JsonUtil.toString(arg));
			}
			sb.append("},");
		}

		log.info(sb.toString());
	}

	/**
	 * 方法之后调用打印返回参数
	 *
	 * @param joinPoint
	 * @param returnValue 方法返回值
	 */
	@AfterReturning(pointcut = "executeApi()", returning = "returnValue")
	public void doAfterReturning(JoinPoint joinPoint, Object returnValue) {
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			if (method.isAnnotationPresent(ApiNoLog.class)) {
				return;
			}

			if (returnValue != null) {
				if (returnValue instanceof ApiResponse) {
					Object data = ((ApiResponse) returnValue).getData();
					if (!(data instanceof List)) {
						log.info(JsonUtil.toString(returnValue));
					}
				}
			}
		} catch (Exception exc) {
			log.error("打印返回数据异常。", exc);
		}
	}
}