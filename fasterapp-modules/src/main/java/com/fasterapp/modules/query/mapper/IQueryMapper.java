package com.fasterapp.modules.query.mapper;

import java.util.List;
import java.util.Map;

public interface IQueryMapper {
    /**
     * 查询接口
     * @param metaData
     * @param parameters
     * @return
     * @throws Exception
     */
    List select(MapperMetaData metaData, Map parameters) throws Exception;
}
