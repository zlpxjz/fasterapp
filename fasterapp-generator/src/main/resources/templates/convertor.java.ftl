package ${basePackage}.dto.convertor;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

import ${basePackage}.model.${entity?cap_first}Model;
import ${basePackage}.dto.${entity?cap_first}Dto;

@Mapper
public interface ${entity?cap_first}Convertor {
    ${entity?cap_first}Convertor  INSTANCE = Mappers.getMapper(${entity?cap_first}Convertor.class);

    /**
     * 单个对象的转换
     **/
    ${entity?cap_first}Dto convert(${entity?cap_first}Model ${entity?uncap_first}Model);

    /**
    * 集合对象的转换
    **/
    List<${entity?cap_first}Dto> convert(List<${entity?cap_first}Model> ${entity?uncap_first}Models);
}
