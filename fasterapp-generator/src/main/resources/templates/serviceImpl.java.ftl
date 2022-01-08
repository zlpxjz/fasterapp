package ${basePackage}.service.impl;

<#if pkFieldImport?exists>
import ${pkFieldImport};
</#if>
import ${basePackage}.model.${entity?cap_first}Model;
import ${basePackage}.mapper.${entity?cap_first}Mapper;
import ${basePackage}.service.I${entity?cap_first}Service;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("${entity?cap_first}Service")
@Transactional(rollbackFor = Exception.class)
public class ${entity?cap_first}ServiceImpl extends BaseServiceImpl<${pkType}, ${entity?cap_first}Model, ${entity?cap_first}Mapper> implements I${entity?cap_first}Service {

}
