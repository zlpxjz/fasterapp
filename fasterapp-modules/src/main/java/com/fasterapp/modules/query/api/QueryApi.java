package com.fasterapp.modules.query.api;

import com.fasterapp.base.annotations.ApiNoLog;
import com.fasterapp.base.arch.ApiResponse;
import com.fasterapp.base.arch.api.BaseApi;
import com.fasterapp.base.utils.CollectionUtil;
import com.fasterapp.modules.query.BatchQueryParameter;
import com.fasterapp.modules.query.service.IQueryService;
import com.fasterapp.modules.query.PageList;
import com.fasterapp.modules.query.QueryParameter;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2021/9/3.
 */
@RestController
@RequestMapping(path="/v1/query")
@Slf4j
public class QueryApi extends BaseApi {
	@Autowired
	private IQueryService queryService;

	/**
	 * 单条记录查询， 主要用在表单数据的查询
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "单条记录查询", notes = "单条记录查询")
	@PostMapping(path="/get")
	public ApiResponse get(@RequestBody QueryParameter parameter) throws Exception{
		List data = queryService.select(parameter);
		if(CollectionUtil.isEmpty(data)) return ApiResponse.error("0001","数据获取异常：数据不存在。");
		if(data.size() > 1) return ApiResponse.error("0002", "数据获取异常：存在多条数据。");

		return ApiResponse.success(data.get(0));
	}

	/**
	 * 单个语句查询
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "无分页查询", notes = "无分页查询")
	@PostMapping
	public @ApiNoLog ApiResponse query(@RequestBody QueryParameter parameter) throws Exception{
		List data = queryService.select(parameter);
		return ApiResponse.success(data);
	}

	/**
	 * 支持多个语句批次查询
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "无分页查询", notes = "无分页查询, 一次可以执行相同查询语句多次查询")
	@PostMapping(path="/batch")
	public @ApiNoLog
	ApiResponse batchQuery(@RequestBody List<BatchQueryParameter> parameters) throws Exception{
		Map data = queryService.select(parameters);
		return ApiResponse.success(data);
	}

	/**
	 * 分页查询
	 * @param pageIndex
	 * @param pageSize
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageIndex", value = "查询页码", required = true, dataType = "Integer", paramType = "path"),
			@ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, dataType = "Integer", paramType = "path")
	})
	@PostMapping(path="/{pageIndex}/{pageSize}")
	public @ApiNoLog ApiResponse paginationQuery(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize,
												 @RequestBody QueryParameter parameter)   throws Exception{
		Page page = (Page) queryService.select(pageIndex, pageSize, parameter);
		PageList pl = new PageList(pageIndex, pageSize, page.getPages(),page.getTotal(), page.getResult());
		return ApiResponse.success(pl);
	}
}
