package com.fasterapp.modules.query.mappers;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class QueryMapperImpl implements IQueryMapper {
    @Autowired
    private SqlSessionTemplate SqlSessionTemplate;

    public QueryMapperImpl() {
    }

    @Override
    public List select(MapperMetaData metaData, Map parameters) throws Exception {
        List result;

        String statementId = metaData.getNameSpace() + "." + metaData.getStatementId();
        if(parameters == null || parameters.isEmpty()){
            result = SqlSessionTemplate.selectList(statementId);
        }else {
            result = SqlSessionTemplate.selectList(statementId, parameters);
        }

        return result == null? new ArrayList():result;
    }
}
