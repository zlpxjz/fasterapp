package com.fasterapp.base.arch.mapper;

import com.fasterapp.base.arch.model.BaseModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Tony on 2021/9/2.
 */
public interface BaseMapper<K extends Serializable, M extends BaseModel> {
	/**
	 * 根据关键字删除记录
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(K id);

	/**
	 * 新增记录
	 * @param model
	 * @return
	 */
	int insert(M model);

	/**
	 * 查询所有数据
	 * @return
	 */
	List<M> selectAll();

	/**
	 * 根据关键字检索数据
	 * @param id
	 * @return
	 */
	M selectByPrimaryKey(K id);

	/**
	 *
	 * @param model
	 * @return
	 */
	int updateByPrimaryKeySelective(Map model);

	/**
	 * 根据关键字更新数据
	 * @param model
	 * @return
	 */
	int updateByPrimaryKey(M model);
}
