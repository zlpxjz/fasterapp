package com.fasterapp.generator;

import java.io.Serializable;

public class FieldMetaData implements Serializable {
    private String name;
    private String javaType;
    private String columnName;
    private String columnDefinition;
    private boolean key = false;
    private boolean auto;
    private int length;
    private int precious;

    public FieldMetaData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConciseJavaType() {
        return this.javaType.substring(this.javaType.lastIndexOf(".") + 1);
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String fullJavaType) {
        javaType = fullJavaType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcType() {
        String definition = getColumnDefinition().trim();
        String type = definition.split(" ")[0];
        type = type.split("[(]")[0].toUpperCase();
        if(type.equalsIgnoreCase("INT")){
            type = "INTEGER";
        }else if(type.equalsIgnoreCase("ENUM")){
            type = "VARCHAR";
        }else if(type.equalsIgnoreCase("DATETIME")){
            type = "TIMESTAMP";
        }
        return type;
    }

    public boolean isKey() {
        return key;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrecious() {
        return precious;
    }

    public void setPrecious(int precious) {
        this.precious = precious;
    }

    public String getColumnDefinition() {
        if(columnDefinition == null || columnDefinition.trim().equals("")) {
            String javaType = getConciseJavaType();
            if("Integer".equalsIgnoreCase(javaType)){
                return "INTEGER(11)";
            }else if("Date".equalsIgnoreCase(javaType)){
                return "DATE";
            }else if("timestamp".equalsIgnoreCase(javaType)){
                return "TIMESTAMP";
            }else{
                return "VARCHAR(32)";
            }
        }else {
            return columnDefinition;
        }
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }


}
