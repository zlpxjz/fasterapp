package com.fasterapp.generator;

import java.util.ArrayList;
import java.util.List;

public class ClassMetaData {
    private String option;
    private String basePackage;
    private String basePath;
    private String table;
    private String entity;
    private String model;
    private String pkType;
    private String pkFullType;
    private Class<?> pkClass;
    private String pkFieldImport;
    private List<FieldMetaData> fields;

    public ClassMetaData() {
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<FieldMetaData> getPkFields() {
        List<FieldMetaData> primaryFields = new ArrayList<>();
        for(FieldMetaData field : fields){
            if(field.isKey()){
                primaryFields.add(field);
            }
        }
        return primaryFields;
    }

    public List<FieldMetaData> getNonPkFields() {
        List<FieldMetaData> NonPrimaryFields = new ArrayList<>();
        for(FieldMetaData field : fields){
            if(!field.isKey()){
                NonPrimaryFields.add(field);
            }
        }
        return NonPrimaryFields;
    }

    public List<FieldMetaData> getFields() {
        return fields;
    }

    public void setFields(List<FieldMetaData> fields) {
        this.fields = fields;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPkType() {
        return pkType;
    }

    public void setPkType(String pkType) {
        this.pkType = pkType;
    }

    public String getPkFullType() {
        return pkFullType;
    }

    public void setPkFullType(String pkFullType) {
        this.pkFullType = pkFullType;
    }

    public String getPkFieldImport() {
        return pkFieldImport;
    }

    public void setPkFieldImport(String pkFieldImport) {
        this.pkFieldImport = pkFieldImport;
    }

    public Class<?> getPkClass() {
        return pkClass;
    }

    public void setPkClass(Class<?> pkClass) {
        this.pkClass = pkClass;
    }

    public String getBaseColumnList(){
        int index = 0;
        StringBuffer columns = new StringBuffer();
        for(FieldMetaData field : fields){
            if(columns.length() > 0){
                columns.append(",");
            }
            columns.append(this.table).append(".").append(field.getColumnName());
            if(++ index % 5 == 0){
                columns.append("\n");
            }
        }

        return columns.toString();
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
