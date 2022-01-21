package com.fasterapp.admin.model;

import com.fasterapp.base.core.model.BaseModel;
import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Entity;
import com.fasterapp.generator.annotations.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Table(name="t_sys_user_info")
@Entity(name="User")
public class UserModel extends BaseModel<String> {
	@Column(name="name", type="varchar(32)", comment="姓名")
	private  String name;

	@Column(name="phone", type="varchar(32)", comment="电话号码")
	private  String phone;

	@Column(name="sex", type="char(1)", comment="性别")
	private  String sex;
}
