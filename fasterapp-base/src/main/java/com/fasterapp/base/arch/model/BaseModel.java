package com.fasterapp.base.arch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Tony on 2021/9/2.
 */
@NoArgsConstructor
@EqualsAndHashCode
public class BaseModel<K extends Serializable>  implements Serializable {
	private boolean _insert = false;

	@Id
	@Getter
	@Setter
	private K id;

	public BaseModel(K id){
		this.id = id;
		this._insert = true;
	}

	@JsonIgnore
	@Column(name = "created_by" , columnDefinition = "varchar(32) default null comment '创建用户'")
	@Getter @Setter private String createdBy;

	@JsonIgnore
	@Column(name = "created_date" , columnDefinition = "timestamp default current_timestamp comment '创建日期'")
	@Getter @Setter private Timestamp createdDate;

	@JsonIgnore
	@Column(name = "updated_by" , columnDefinition = "varchar(32) not null comment '更新用户'")
	@Getter @Setter private String updatedBy;

	@JsonIgnore
	@Column(name = "updated_date" , columnDefinition = "timestamp default current_timestamp on update current_timestamp comment '更新日期'")
	@Getter @Setter private Timestamp updatedDate;

	@JsonIgnore
	@Column(name = "is_deleted", length = 1 , columnDefinition = "char(1) default 'N' not null comment 'Y/N'")
	@Getter @Setter private String deleted = "N";


	@JsonIgnore
	public boolean isInsert() {
		return id == null || _insert;
	}
}
