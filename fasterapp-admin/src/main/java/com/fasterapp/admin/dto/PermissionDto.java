package com.fasterapp.admin.dto;

import lombok.Data;

@Data
public class PermissionDto {
    private String   parentCode;
    private String   code;
    private String   name;
    private boolean   _insert;
    private String   id;
}
