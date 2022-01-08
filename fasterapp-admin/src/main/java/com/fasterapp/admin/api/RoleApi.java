package com.fasterapp.admin.api;

import com.fasterapp.admin.model.RoleModel;
import com.fasterapp.admin.service.IRoleService;
import com.fasterapp.base.core.api.BaseApi;
import com.fasterapp.base.core.api.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/role")
@Slf4j
@Api("Role相关API")
public class RoleApi extends BaseApi {
    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "保存数据", notes = "保存数据")
    @PostMapping(path="/save")
    public ApiResponse save(@RequestBody RoleModel model) throws Exception{
        roleService.save(model);
        return ApiResponse.success().setMessage("保存成功。");
    }

    @ApiOperation(value = "根据id获取信息", notes = "查询数据库中某个信息")
    @GetMapping(path="/{id}/get")
    public ApiResponse get(@PathVariable("id") String Id) throws Exception{
        RoleModel model = roleService.getOne(Id);
        return ApiResponse.success(model);
    }

    @ApiOperation(value = "删除指定数据", notes = "删除指定数据")
    @GetMapping(path="/{id}/delete")
    public ApiResponse delete(@PathVariable("id") String id) throws Exception{
        roleService.delete(id);
        return ApiResponse.success().setMessage("删除成功。");
    }
}
