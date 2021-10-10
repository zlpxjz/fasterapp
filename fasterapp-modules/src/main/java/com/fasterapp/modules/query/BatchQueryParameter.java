package com.fasterapp.modules.query;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class BatchQueryParameter extends QueryParameter{
    @Getter @Setter @NonNull private String resultCode; //批次查询中返回结果集code
}