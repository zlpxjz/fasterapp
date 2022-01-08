package com.fasterapp.admin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Tony on 2021/12/17.
 */
@Entity
@Table(name="T_SYS_ORG_LEVEL_INFO")
@Getter
@Setter
public class OrganizationLevelModel {
	@Column(name="parent_id", columnDefinition = "VARCHAR(64) COMMENT '序列名称'")
	private String parentId;
	@Column(name="name", columnDefinition = "VARCHAR(64) COMMENT '序列名称'")
	private String name;
	@Column(name="level", columnDefinition = "VARCHAR(64) COMMENT '序列名称'")
	private String level;
	@Column(name="status", columnDefinition = "VARCHAR(64) COMMENT '序列名称'")
	private String status;
}
