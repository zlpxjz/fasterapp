package com.fasterapp.demo.apis;

import com.fasterapp.demo.models.DemoModel;
import com.fasterapp.demo.services.IDemoService;
import com.fasterapp.base.arch.api.BaseApi;
import com.fasterapp.base.arch.api.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/demo")
@Slf4j
@Api("Demo相关API")
public class DemoApi extends BaseApi {
    @Autowired
    private IDemoService demoService;

    @ApiOperation(value = "保存数据", notes = "保存数据")
    @PostMapping
    public ApiResponse save(@RequestBody DemoModel model) throws Exception{
        demoService.save(model);
        return ApiResponse.success().setMessage("保存成功。");
    }

    @ApiOperation(value = "根据id获取信息", notes = "查询数据库中某个信息")
    @GetMapping(path="/{id}")
    public ApiResponse get(@PathVariable("id") Integer Id) throws Exception{
        DemoModel model = demoService.getOne(Id);
        return ApiResponse.success(model);
    }

    @ApiOperation(value = "删除指定数据", notes = "删除指定数据")
    @DeleteMapping(path="/{id}")
    public ApiResponse delete(@PathVariable("id") Integer id) throws Exception{
        demoService.delete(id);
        return ApiResponse.success().setMessage("删除成功。");
    }
}
