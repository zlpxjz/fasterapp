package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.UserModel;
import com.fasterapp.admin.mapper.UserMapper;
import com.fasterapp.admin.service.IUserService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("UserService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<String, UserModel, UserMapper> implements IUserService {

}
