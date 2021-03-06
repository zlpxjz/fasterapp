package com.fasterapp.modules.query.service.impl;

import com.fasterapp.base.exceptions.AppException;
import com.fasterapp.modules.query.BatchQueryParameter;
import com.fasterapp.modules.query.QueryParameter;
import com.fasterapp.modules.query.mapper.IQueryMapper;
import com.fasterapp.modules.query.mapper.MapperMetaData;
import com.fasterapp.modules.query.processors.IQueryProcessor;
import com.fasterapp.modules.query.service.IQueryRegisterService;
import com.fasterapp.modules.query.service.IQueryService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("QueryService")
@Slf4j
@Transactional(readOnly = true)
public class QueryServiceImpl implements IQueryService {
    @Autowired
    private IQueryRegisterService queryRegisterService;

    @Autowired
    private IQueryMapper mapper;

    public QueryServiceImpl() {
    }

    @Override
    public List select(QueryParameter parameter) throws Exception {
        return this.select(0,0, parameter);
    }

    @Override
    public List select(Integer pageIndex, Integer pageSize, QueryParameter parameter) throws Exception {
        String code = parameter.getQueryCode();
        MapperMetaData metaData = getMapperMetaData(code);

        Map<String, Object> parameters = parameter.getParameters();

        IQueryProcessor queryProcessor = metaData.getQueryProcessor();
        if(queryProcessor != null){
            //查询预处理
            queryProcessor.processParameters(parameters);
        }

        if(pageIndex > 0 && pageSize > 0) {
            pageIndex = pageIndex <= 1 ? 1 : pageIndex;
            PageHelper.startPage(pageIndex, pageSize);
        }

        List list = mapper.select(metaData, parameters);
        if(queryProcessor != null){
            //查询结果处理
            queryProcessor.processResult(parameter, list);
        }

        return list;
    }

    @Override
    public Map select(List<BatchQueryParameter> parameters) throws Exception {
        Map<String, List> result = new HashMap<>();

        String queryCode;
        Map<String, Object> parameter;
        List list;
        for(BatchQueryParameter queryParameter : parameters){
            queryCode = queryParameter.getQueryCode();
            MapperMetaData metaData = getMapperMetaData(queryCode);
            if(metaData == null){
                throw new AppException("查询配置不存在。");
            }

            parameter = queryParameter.getParameters();
            IQueryProcessor queryProcessor = metaData.getQueryProcessor();
            if(queryProcessor != null){
                queryProcessor.processParameters(parameter);
            }

            list = mapper.select(metaData, parameter);

            if(queryProcessor != null){
                //查询结果处理
                queryProcessor.processResult(queryParameter, list);
            }

            result.put(queryParameter.getResultCode(), list);
        }

        return result;
    }

    /**
     * 根据Code获取注册的查询
     * @param code
     * @return
     * @throws Exception
     */
    private MapperMetaData getMapperMetaData(String code) throws Exception {
        return queryRegisterService.getMetaData(code);
    }
}
