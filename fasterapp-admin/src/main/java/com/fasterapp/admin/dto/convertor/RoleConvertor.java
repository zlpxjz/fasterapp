package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.RoleModel;
import com.fasterapp.admin.dto.RoleDto;

@Mapper
public interface RoleConvertor {
    RoleConvertor  INSTANCE = Mappers.getMapper(RoleConvertor.class);

    /**
     * 单个对象的转换
     **/
    RoleDto convert(RoleModel roleModel);

    /**
    * 集合对象的转换
    **/
    List<RoleDto> convert(List<RoleModel> roleModels);
}
