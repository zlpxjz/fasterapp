package ${basePackage}.mappers;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.models.${entity?cap_first}Model;

import com.fasterapp.base.arch.mapper.BaseMapper;

public interface ${entity?cap_first}Mapper extends BaseMapper<${pkType}, ${entity?cap_first}Model> {
}

