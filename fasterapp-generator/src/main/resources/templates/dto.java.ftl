package ${basePackage}.dtos;

import lombok.Data;


@Data
public class ${entity?cap_first}Dto {
<#list fields as field>
    <#if field.name != 'createdBy' && field.name != 'createdDate' && field.name != 'updatedBy' && field.name != 'updatedDate' && field.name != 'deleted'>
    private ${field.conciseJavaType}   ${field.name};
    </#if>
</#list>
}
