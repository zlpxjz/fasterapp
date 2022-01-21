package com.fasterapp.admin.service;

import com.fasterapp.admin.model.AccountModel;
import com.fasterapp.base.core.service.IBaseService;

public interface IAccountService extends IBaseService<String, AccountModel> {
	/**
	 * 登录验证
	 * @param userName
	 * @param password
	 * @param authcode
	 * @throws Exception
	 */
	void login(String userName, String password, String authcode) throws Exception;

	/**
	 * 登录退出
	 * @param code
	 * @throws Exception
	 */
	void logout(String code) throws Exception;
}
