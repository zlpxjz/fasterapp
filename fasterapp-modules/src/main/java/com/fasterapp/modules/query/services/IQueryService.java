package com.fasterapp.modules.query.services;

import com.fasterapp.modules.query.BatchQueryParameter;
import com.fasterapp.modules.query.QueryParameter;

import java.util.List;
import java.util.Map;

/**
 * 通用查询服务
 */
public interface IQueryService {
    /**
     * 适合单个查询
     * @param parameter
     * @return
     * @throws Exception
     */
    public List select(QueryParameter parameter) throws Exception;

    /**
     * 适合单个查询
     * @param parameter
     * @return
     * @throws Exception
     */
    public List select(Integer pageIndex, Integer pageSize, QueryParameter parameter) throws Exception;

    /**
     * 适合不同脚本的批次查询
     * @param parameters
     * @return
     * @throws Exception
     */
    public Map select(List<BatchQueryParameter> parameters) throws Exception;
}
