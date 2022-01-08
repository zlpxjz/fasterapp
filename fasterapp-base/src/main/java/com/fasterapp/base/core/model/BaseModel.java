package com.fasterapp.base.core.model;

import com.fasterapp.base.core.model.annotations.Column;
import com.fasterapp.base.core.model.annotations.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@JsonIgnore
	@Column(name = "created_by" , type="varchar(32)", comment = "创建用户")
	@Getter @Setter private String createdBy;

	@JsonIgnore
	@Column(name = "created_date" , type="timestamp", comment = "创建日期")
	@Getter @Setter private Timestamp createdDate;

	@JsonIgnore
	@Column(name = "updated_by" , type="varchar(32)", comment = "更新用户")
	@Getter @Setter private String updatedBy;

	@JsonIgnore
	@Column(name = "updated_date" , type="timestamp", comment = "更新日期")
	@Getter @Setter private Timestamp updatedDate;

	@JsonIgnore
	@Column(name = "is_deleted" , type="char(1)", comment = "删除状态")
	@Getter @Setter private String deleted = "N";

	@JsonIgnore
	public boolean isInsert() {
		return id == null || _insert;
	}
}
