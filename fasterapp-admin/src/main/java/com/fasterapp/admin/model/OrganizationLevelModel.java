package com.fasterapp.admin.model;

import com.fasterapp.generator.annotations.Column;
import com.fasterapp.generator.annotations.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Tony on 2021/12/17.
 */
@Model(name="OrganizationLevel")
@Getter
@Setter
public class OrganizationLevelModel {
	@Column(name="parent_id", type="varchar(64)", comment="上级组织ID")
	private String parentId;

	@Column(name="name", type="varchar(64)", comment="组织名称")
	private String name;

	@Column(name="level", type="varchar(64)", comment="组织层级")
	private String level;

	@Column(name="status", type="char(1)", comment="组织状态")
	private String status;
}
