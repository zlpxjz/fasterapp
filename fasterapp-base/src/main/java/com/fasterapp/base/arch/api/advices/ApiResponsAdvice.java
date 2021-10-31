package com.fasterapp.base.arch.api.advices;

import com.fasterapp.base.AppException;
import com.fasterapp.base.arch.ApiResponse;
import com.fasterapp.base.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tony on 2021/9/3.
 */
@RestControllerAdvice(annotations = RestController.class)
@Slf4j(topic="ApiExceptionAdvice")
public class ApiResponsAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if(body == null){
			return ApiResponse.success();
		}

		if(body instanceof ApiResponse){
			return body;
		}

		return ApiResponse.success(body);
	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse sendErrorResponse_System(Exception exception, HttpServletResponse httpServletResponse){
		log.error("异常信息", exception);

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		Throwable cause = this.findRootExceptionThrowable(exception);
		if(cause != null && cause instanceof AppException){
			AppException appException = (AppException) cause;
			return ApiResponse.error(appException.getCode(), appException.getMessage());
		}else {
			return ApiResponse.error("系统异常，请稍后再试。");
		}
	}

	@ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
	public ApiResponse httpMediaTypeNotAcceptableExceptionHandler(Exception ex) {
		log.error("异常信息", ex);

		Throwable cause = this.findRootExceptionThrowable(ex);
		if(cause != null && cause instanceof AppException){
			AppException appException = (AppException) cause;
			return ApiResponse.error(appException.getCode(), appException.getMessage());
		}else {
			return ApiResponse.error("系统异常，请稍后再试。");
		}
	}

	/**
	 * 附件上传操作异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ApiResponse handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		return ApiResponse.error(StringUtil.format("文件大小超出{0}限制，无法上传! ",e.getMaxUploadSize()));
	}

	/**
	 * 并发操作乐观锁异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(OptimisticLockException.class)
	public ApiResponse handleOptimisticLockException(OptimisticLockException e) {
		return ApiResponse.error("数据并发操作异常，请稍后再提交。");
	}

	/**
	 * find root exception throwable.
	 *
	 * @param e
	 * @return
	 */
	protected Throwable findRootExceptionThrowable(Exception e) {
		Throwable rootCause = e;

		while (rootCause.getCause() != null) {
			rootCause = rootCause.getCause();
		}
		return rootCause;
	}
}
