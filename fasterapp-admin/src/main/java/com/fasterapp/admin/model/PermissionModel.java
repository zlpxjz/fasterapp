package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.generator.annotations.Model;
import com.fasterapp.generator.annotations.Column;

/**
 * Created by Tony on 2021/12/17.
 */
@Model(name="Permission", table="t_permission_info")
public class PermissionModel extends BaseModel<String> {
	@Column(name="parent_code", type="varchar(32)", comment="上级权限编码")
	private String  parentCode;

	@Column(name="code", type="varchar(32)", comment="权限编码")
	private String 	code;

	@Column(name="name", type="varchar(32)", comment="权限名称")
	private String 	name;
}
