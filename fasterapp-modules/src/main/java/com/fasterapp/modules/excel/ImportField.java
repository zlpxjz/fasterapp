package com.fasterapp.modules.excel;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Tony on 2021/9/16.
 */
@Data
public class ImportField {
	/**
	 * 数据类型
	 */
	public enum TYPE{
		Integer("Integer"), Double("Double"), Date("Date"), String("String");

		String type;
		TYPE(String type){
			this.type = type;
		}
		public static TYPE getType(String type){
			return valueOf(type);
		}
	}

	@Getter	@Setter	int index = -1;
	@Getter @Setter int colIndex = -1;
	@Getter @Setter String title;
	@Getter @Setter @NonNull String dataKey;
	@Getter @Setter @NonNull TYPE dataType = TYPE.String;
	@Getter @Setter String dataFormat;

	public ImportField(String title, String dataKey, TYPE dataType){
		this.title = title;
		this.dataKey = dataKey;
		this.dataType = dataType;
	}

	public ImportField(int colIndex, String dataKey, TYPE dataType){
		this.colIndex = colIndex;
		this.dataKey = dataKey;
		this.dataType = dataType;
	}
}
