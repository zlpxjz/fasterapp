package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Entity;
import com.fasterapp.generator.annotations.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Table(name="t_sys_role_principal_info")
@Entity(name="RolePrincipal")
public class RolePrincipalModel extends BaseModel<String> {
	@Column(name="principal_id", type="varchar(32)", comment="主体ID")
	private String principalId;

	@Column(name="principal_type", type="varchar(32)", comment="主体类型")
	private String principalType;

	@Column(name="role_id", type="varchar(32)", comment="角色编号")
	private String roleId;

	@Column(name="status", type="char(1)", comment="状态")
	private String status;
}
