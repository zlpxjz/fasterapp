package com.fasterapp.modules.query;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;

public class QueryParameter {
    @Getter @Setter @NonNull private String queryCode; //查询编码，用来获取注册的查询信息
    @Getter @Setter private Map<String, Object> parameters; //查询参数
}