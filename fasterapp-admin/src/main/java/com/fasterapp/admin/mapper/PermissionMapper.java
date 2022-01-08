package com.fasterapp.admin.mapper;

import com.fasterapp.admin.model.PermissionModel;
import com.fasterapp.base.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends BaseMapper<String, PermissionModel> {
	List<PermissionModel> selectByRoles(@Param("roleIds") List<String> roleIds);
}

