package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Model;

/**
 * Created by Tony on 2021/12/17.
 */
@Model(name="RolePermission")
public class RolePermissionModel extends BaseModel<String> {
	@Column(name="role_id", type="varchar(32)", comment="角色")
	private  String roleId;

	@Column(name="permission_id", type="varchar(32)", comment="权限")
	private  String permissionId;
}
