package ${basePackage}.services.impl;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.models.${entity?cap_first}Model;
import ${basePackage}.mappers.${entity?cap_first}Mapper;
import ${basePackage}.services.I${entity?cap_first}Service;
import com.fasterapp.base.arch.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("${entity?cap_first}Service")
@Transactional(rollbackFor = Exception.class)
public class ${entity?cap_first}ServiceImpl extends BaseServiceImpl<${pkType}, ${entity?cap_first}Model, ${entity?cap_first}Mapper> implements I${entity?cap_first}Service {

}
