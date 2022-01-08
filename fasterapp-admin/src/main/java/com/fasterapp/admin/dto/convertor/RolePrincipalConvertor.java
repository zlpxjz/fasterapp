package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.RolePrincipalModel;
import com.fasterapp.admin.dto.RolePrincipalDto;

@Mapper
public interface RolePrincipalConvertor {
    RolePrincipalConvertor  INSTANCE = Mappers.getMapper(RolePrincipalConvertor.class);

    /**
     * 单个对象的转换
     **/
    RolePrincipalDto convert(RolePrincipalModel rolePrincipalModel);

    /**
    * 集合对象的转换
    **/
    List<RolePrincipalDto> convert(List<RolePrincipalModel> rolePrincipalModels);
}
