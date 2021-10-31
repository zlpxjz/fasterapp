package com.fasterapp.modules.query.mapper;

import com.fasterapp.modules.query.processors.IQueryProcessor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class MapperMetaData {
    @Getter @Setter @NonNull public  String desc; //功能描述
    @Getter @Setter @NonNull public  String code; //查询编码，在客户端请求中提供该参数
    @Getter @Setter @NonNull private String nameSpace; //mapper.xml文件的NameSpace
    @Getter @Setter @NonNull private String statementId; //查询脚本id
    @Getter @Setter @NonNull private boolean primary = false; //是否优先
    @Getter private IQueryProcessor queryProcessor; //查询处理器

    public MapperMetaData(String desc, String code, String nameSpace, String statementId, boolean primary) {
        this.desc = desc;
        this.code = code;
        this.nameSpace = nameSpace;
        this.statementId = statementId;
        this.primary = primary;
    }
}