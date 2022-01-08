package com.fasterapp.base.core.service;

import com.fasterapp.base.core.model.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础服务接口，包括对象的CRUD。
 * @param <K>
 * @param <M>
 */
public interface IBaseService<K extends Serializable, M extends BaseModel> {
	/**
	 * 新增实体
	 * @param model
	 * @throws Exception
	 */
	void save(M model) throws Exception;

	/**
	 * 有选择更新对象
	 * @param model
	 * @return
	 * @throws Exception
	 */
	void updateSelective(Map model) throws Exception;

	/**
	 * 根据关键字获取实体
	 * @param key
	 * @return
	 * @throws Exception
	 */
	M getOne(K key) throws Exception;

	/**
	 * 获取所有数据库记录
	 * @return
	 * @throws Exception
	 */
	List<M> getList() throws Exception;

	/**
	 * 单条删除
	 * @param key
	 * @throws Exception
	 */
	void delete(K key) throws Exception;

	/**
	 * 批量删除
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	void delete(List<K> keys) throws Exception;
}
