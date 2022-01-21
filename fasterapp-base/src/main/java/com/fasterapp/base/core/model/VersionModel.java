package com.fasterapp.base.core.model;

import com.fasterapp.generator.annotations.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Tony on 2022/1/6.
 */
@NoArgsConstructor
@EqualsAndHashCode
public class VersionModel<K extends Serializable> extends BaseModel {
	@JsonIgnore
	@Column(name = "version_no" , type = "Integer(10)", comment="版本号")
	@Getter @Setter private Integer versionNo = 1;
}
