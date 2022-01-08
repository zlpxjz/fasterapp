package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.RoleModel;
import com.fasterapp.admin.mapper.RoleMapper;
import com.fasterapp.admin.service.IRoleService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("RoleService")
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<String, RoleModel, RoleMapper> implements IRoleService {

}
