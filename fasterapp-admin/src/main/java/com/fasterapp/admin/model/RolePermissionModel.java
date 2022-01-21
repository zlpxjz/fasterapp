package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Entity;
import com.fasterapp.generator.annotations.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Table(name="t_sys_role_permssion_info")
@Entity(name="RolePermission")
public class RolePermissionModel extends BaseModel<String> {
	@Column(name="role_id", type="varchar(32)", comment="角色")
	private  String roleId;

	@Column(name="permission_id", type="varchar(32)", comment="权限")
	private  String permissionId;
}
