package ${basePackage}.service;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.model.${entity?cap_first}Model;
import com.fasterapp.base.core.service.IBaseService;

public interface I${entity?cap_first}Service extends IBaseService<${pkType}, ${entity?cap_first}Model> {
}
