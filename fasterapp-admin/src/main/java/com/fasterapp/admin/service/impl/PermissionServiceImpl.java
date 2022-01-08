package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.PermissionModel;
import com.fasterapp.admin.mapper.PermissionMapper;
import com.fasterapp.admin.service.IPermissionService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service("PermissionService")
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends BaseServiceImpl<String, PermissionModel, PermissionMapper> implements IPermissionService {
	@Override
	public List<PermissionModel> getPermissionsByRole(List<String> roleId) throws Exception {
		return null;
	}
}
