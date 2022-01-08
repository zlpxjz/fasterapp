package com.fasterapp.admin.api;

import com.fasterapp.admin.model.PermissionModel;
import com.fasterapp.admin.service.IPermissionService;
import com.fasterapp.base.core.api.BaseApi;
import com.fasterapp.base.core.api.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/permission")
@Slf4j
@Api("Permission相关API")
public class PermissionApi extends BaseApi {
    @Autowired
    private IPermissionService permissionService;

    @ApiOperation(value = "保存数据", notes = "保存数据")
    @PostMapping(path="/save")
    public ApiResponse save(@RequestBody PermissionModel model) throws Exception{
        permissionService.save(model);
        return ApiResponse.success().setMessage("保存成功。");
    }

    @ApiOperation(value = "根据id获取信息", notes = "查询数据库中某个信息")
    @GetMapping(path="/{id}/get")
    public ApiResponse get(@PathVariable("id") String Id) throws Exception{
        PermissionModel model = permissionService.getOne(Id);
        return ApiResponse.success(model);
    }

    @ApiOperation(value = "删除指定数据", notes = "删除指定数据")
    @GetMapping(path="/{id}/delete")
    public ApiResponse delete(@PathVariable("id") String id) throws Exception{
        permissionService.delete(id);
        return ApiResponse.success().setMessage("删除成功。");
    }
}
