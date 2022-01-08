package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.RolePermissionModel;
import com.fasterapp.admin.dto.RolePermissionDto;

@Mapper
public interface RolePermissionConvertor {
    RolePermissionConvertor  INSTANCE = Mappers.getMapper(RolePermissionConvertor.class);

    /**
     * 单个对象的转换
     **/
    RolePermissionDto convert(RolePermissionModel rolePermissionModel);

    /**
    * 集合对象的转换
    **/
    List<RolePermissionDto> convert(List<RolePermissionModel> rolePermissionModels);
}
