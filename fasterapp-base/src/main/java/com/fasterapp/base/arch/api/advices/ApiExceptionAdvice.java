package com.fasterapp.base.arch.api.advices;

import com.fasterapp.base.AppException;
import com.fasterapp.base.arch.ApiResponse;
import com.fasterapp.base.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.OptimisticLockException;

/**
 * Created by Tony on 2021/9/3.
 */
@RestControllerAdvice
@Slf4j(topic="ApiExceptionAdvice")
public class ApiExceptionAdvice {
	@ExceptionHandler(value = AppException.class)
	public ApiResponse appExceptionHandler(Exception ex) {
		log.error("异常信息", ex);

		AppException appException = (AppException) ex;

		return ApiResponse.error(appException.getCode(), appException.getMessage());
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

	@ExceptionHandler(value = Exception.class)
	public ApiResponse exceptionHandler(Exception ex) {
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
