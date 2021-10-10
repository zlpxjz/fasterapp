package com.fasterapp.modules.sequence.models;

import com.fasterapp.base.arch.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name="T_SYS_SEQUENCE_INFO")
@Getter
@Setter
public class SequenceModel extends BaseModel {
	@Id
	@Column(name="ID", columnDefinition = "INT NOT NULL COMMENT '许可'")
    private Integer id;
	
	@Column(name="SEQ_NAME", columnDefinition = "VARCHAR(64) COMMENT '序列名称'")
    private String seqName;

	@Column(name="SEQ_DATE", columnDefinition = "CHAR(8) COMMENT '序列日期'")
	private String seqDate;

	@Column(name="SEQ_VAL", columnDefinition = "INT COMMENT '序列值'")
	private BigInteger seqValue;

	@Override
	public boolean isInsert() {
		return false;
	}
}
