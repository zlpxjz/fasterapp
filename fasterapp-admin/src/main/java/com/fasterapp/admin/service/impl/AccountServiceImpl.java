package com.fasterapp.admin.service.impl;

import com.fasterapp.admin.model.AccountModel;
import com.fasterapp.admin.mapper.AccountMapper;
import com.fasterapp.admin.service.IAccountService;
import com.fasterapp.base.core.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("AccountService")
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl extends BaseServiceImpl<String, AccountModel, AccountMapper> implements IAccountService {

}
