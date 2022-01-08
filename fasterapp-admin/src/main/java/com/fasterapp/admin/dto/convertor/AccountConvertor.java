package com.fasterapp.admin.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import com.fasterapp.admin.model.AccountModel;
import com.fasterapp.admin.dto.AccountDto;

@Mapper
public interface AccountConvertor {
    AccountConvertor  INSTANCE = Mappers.getMapper(AccountConvertor.class);

    /**
     * 单个对象的转换
     **/
    AccountDto convert(AccountModel accountModel);

    /**
    * 集合对象的转换
    **/
    List<AccountDto> convert(List<AccountModel> accountModels);
}
