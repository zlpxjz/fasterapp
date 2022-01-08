package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.RolePrincipalModel;
import com.fasterapp.admin.mapper.RolePrincipalMapper;
import com.fasterapp.admin.service.IRolePrincipalService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("RolePrincipalService")
@Transactional(rollbackFor = Exception.class)
public class RolePrincipalServiceImpl extends BaseServiceImpl<String, RolePrincipalModel, RolePrincipalMapper> implements IRolePrincipalService {

}
