package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.PermissionModel;
import com.fasterapp.admin.dto.PermissionDto;

@Mapper
public interface PermissionConvertor {
    PermissionConvertor  INSTANCE = Mappers.getMapper(PermissionConvertor.class);

    /**
     * 单个对象的转换
     **/
    PermissionDto convert(PermissionModel permissionModel);

    /**
    * 集合对象的转换
    **/
    List<PermissionDto> convert(List<PermissionModel> permissionModels);
}
