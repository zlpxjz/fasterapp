package com.fasterapp.modules.query.service.impl;

import com.fasterapp.base.exceptions.AppException;
import com.fasterapp.modules.query.mapper.MapperMetaData;
import com.fasterapp.modules.query.service.IQueryRegisterService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("QueryRegisterService")
public class QueryRegisterServiceImpl implements IQueryRegisterService {
    private Map<String, MapperMetaData> mapperMetaDataMap = new HashMap<>();

    @Override
    public void register(MapperMetaData metaData) throws Exception{
        String code = metaData.getCode();

        MapperMetaData existedMetaData = mapperMetaDataMap.get(code);
        if(existedMetaData != null){
            if(existedMetaData.isPrimary() && metaData.isPrimary()){
                throw new AppException("查询编码{}重复注册", code);
            }

            if(!existedMetaData.isPrimary() && !metaData.isPrimary()){
                throw new AppException("查询编码{}重复注册", code);
            }

            if(existedMetaData.isPrimary()){
                return;
            }
        }

        mapperMetaDataMap.put(code, metaData);
    }

    @Override
    public MapperMetaData getMetaData(String code) throws Exception{
        MapperMetaData metaData = mapperMetaDataMap.get(code);
        if(metaData == null){
            throw new AppException("找不到查询配置。查询编码={}", code);
        }

        return metaData;
    }
}
