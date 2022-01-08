package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.base.core.model.annotations.Column;
import com.fasterapp.base.core.model.annotations.Entity;
import com.fasterapp.base.core.model.annotations.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Table(name="t_sys_account_info")
@Entity(name="Account")
public class AccountModel extends BaseModel<String> {
	@Column(name="name", type="varchar(32)", comment="账号")
	private  String userName;

	@Column(name="password", type="varchar(32)", comment="密码")
	private  String password;

	@Column(name="status", type="char(1)", comment="启用状态")
	private  String status = "Y";
}
