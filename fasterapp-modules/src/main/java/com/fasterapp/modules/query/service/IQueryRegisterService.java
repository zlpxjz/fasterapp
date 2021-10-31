package com.fasterapp.modules.query.service;

import com.fasterapp.modules.query.mapper.MapperMetaData;

/**
 * 查询注册服务
 */
public interface IQueryRegisterService {
    /**
     * 查询注册，每个查询以MapperMetaData对象注册到系统。
     * @return
     */
    void register(MapperMetaData metaData) throws Exception;

    /**
     * 获取查询配置
     * @param code
     * @return
     */
    MapperMetaData getMetaData(String code) throws Exception;
}
