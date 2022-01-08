package ${basePackage}.mapper;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.model.${entity?cap_first}Model;

import com.fasterapp.base.core.mapper.BaseMapper;

public interface ${entity?cap_first}Mapper extends BaseMapper<${pkType}, ${entity?cap_first}Model> {
}

