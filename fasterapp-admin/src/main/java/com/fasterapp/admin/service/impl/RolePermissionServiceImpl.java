package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.RolePermissionModel;
import com.fasterapp.admin.mapper.RolePermissionMapper;
import com.fasterapp.admin.service.IRolePermissionService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("RolePermissionService")
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends BaseServiceImpl<String, RolePermissionModel, RolePermissionMapper> implements IRolePermissionService {

}
