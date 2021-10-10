package com.fasterapp.modules.query.processors;

import com.fasterapp.modules.query.QueryParameter;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2021/9/3.
 */
public interface IQueryProcessor {
	/**
	 * 参数处理
	 * @param parameters
	 * @throws Exception
	 */
	default void processParameters(Map<String, Object> parameters) throws Exception{

	}

	/**
	 * 查询结果处理
	 * @param parameter
	 * @param result
	 * @throws Exception
	 */
	default void processResult(QueryParameter parameter, List<Map> result) throws Exception{

	}
}
