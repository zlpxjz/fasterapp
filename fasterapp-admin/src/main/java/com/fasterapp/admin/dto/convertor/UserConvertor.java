package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.UserModel;
import com.fasterapp.admin.dto.UserDto;

@Mapper
public interface UserConvertor {
    UserConvertor  INSTANCE = Mappers.getMapper(UserConvertor.class);

    /**
     * 单个对象的转换
     **/
    UserDto convert(UserModel userModel);

    /**
    * 集合对象的转换
    **/
    List<UserDto> convert(List<UserModel> userModels);
}
