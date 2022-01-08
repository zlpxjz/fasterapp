package com.fasterapp.admin.api;

import com.fasterapp.admin.model.RolePrincipalModel;
import com.fasterapp.admin.service.IRolePrincipalService;
import com.fasterapp.base.core.api.BaseApi;
import com.fasterapp.base.core.api.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/rolePrincipal")
@Slf4j
@Api("RolePrincipal相关API")
public class RolePrincipalApi extends BaseApi {
    @Autowired
    private IRolePrincipalService rolePrincipalService;

    @ApiOperation(value = "保存数据", notes = "保存数据")
    @PostMapping(path="/save")
    public ApiResponse save(@RequestBody RolePrincipalModel model) throws Exception{
        rolePrincipalService.save(model);
        return ApiResponse.success().setMessage("保存成功。");
    }

    @ApiOperation(value = "根据id获取信息", notes = "查询数据库中某个信息")
    @GetMapping(path="/{id}/get")
    public ApiResponse get(@PathVariable("id") String Id) throws Exception{
        RolePrincipalModel model = rolePrincipalService.getOne(Id);
        return ApiResponse.success(model);
    }

    @ApiOperation(value = "删除指定数据", notes = "删除指定数据")
    @GetMapping(path="/{id}/delete")
    public ApiResponse delete(@PathVariable("id") String id) throws Exception{
        rolePrincipalService.delete(id);
        return ApiResponse.success().setMessage("删除成功。");
    }
}
