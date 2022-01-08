package com.fasterapp.admin.service;

import com.fasterapp.admin.model.PermissionModel;
import com.fasterapp.base.core.service.IBaseService;

import java.util.List;

public interface IPermissionService extends IBaseService<String, PermissionModel> {
	/**
	 * 根据角色获取权限清单
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<PermissionModel> getPermissionsByRole(List<String> roleId) throws Exception;
}
