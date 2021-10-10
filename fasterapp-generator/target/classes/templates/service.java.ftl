package ${basePackage}.services;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.models.${entity?cap_first}Model;
import com.fasterapp.base.arch.service.IBaseService;

public interface I${entity?cap_first}Service extends IBaseService<${pkType}, ${entity?cap_first}Model> {
}
