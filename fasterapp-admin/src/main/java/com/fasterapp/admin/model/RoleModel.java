package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.base.core.model.annotations.Column;
import com.fasterapp.base.core.model.annotations.Entity;
import com.fasterapp.base.core.model.annotations.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Table(name="t_sys_role_info")
@Entity(name="Role")
public class RoleModel extends BaseModel<String> {
	@Column(name="code", type="varchar(32)", comment="角色编码")
	private  String code;

	@Column(name="name", type="varchar(32)", comment="角色名称")
	private  String name;

	@Column(name="status", type="char(1)", comment="启用状态")
	private  String status = "Y";
}
