package ${basePackage}.apis;

import ${basePackage}.models.${entity?cap_first}Model;
import ${basePackage}.services.I${entity?cap_first}Service;
import com.fasterapp.base.arch.api.BaseApi;
import com.fasterapp.base.arch.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/${entity?uncap_first}")
@Slf4j
@Api("${entity?cap_first}相关API")
public class ${entity?cap_first}Api extends BaseApi {
    @Autowired
    private I${entity?cap_first}Service ${entity?uncap_first}Service;

    @ApiOperation(value = "保存数据", notes = "保存数据")
    @PostMapping(path="/save")
    public ApiResponse save(@RequestBody ${entity?cap_first}Model model) throws Exception{
        ${entity?uncap_first}Service.save(model);
        return ApiResponse.success().setMessage("保存成功。");
    }

    @ApiOperation(value = "根据id获取信息", notes = "查询数据库中某个信息")
    @GetMapping(path="/{id}/get")
    public ApiResponse get(@PathVariable("id") ${pkType} Id) throws Exception{
        ${entity?cap_first}Model model = ${entity?uncap_first}Service.getOne(Id);
        return ApiResponse.success(model);
    }

    @ApiOperation(value = "删除指定数据", notes = "删除指定数据")
    @GetMapping(path="/{id}/delete")
    public ApiResponse delete(@PathVariable("id") ${pkType} id) throws Exception{
        ${entity?uncap_first}Service.delete(id);
        return ApiResponse.success().setMessage("删除成功。");
    }
}
