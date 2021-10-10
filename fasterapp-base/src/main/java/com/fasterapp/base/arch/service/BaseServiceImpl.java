package com.fasterapp.base.arch.service;

import com.fasterapp.base.arch.mapper.BaseMapper;
import com.fasterapp.base.arch.model.BaseModel;
import com.fasterapp.base.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
public class BaseServiceImpl<K extends Serializable, E extends BaseModel, M extends BaseMapper<K, E>> implements IBaseService<K, E> {
	@Autowired
	protected M mapper;

	public BaseServiceImpl() {
	}

	@Override
	public void save(E model) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("Model={}", JsonUtil.toString(model));
		}

		if (model.isInsert()) {
			this.mapper.insert(model);
		} else {
			this.mapper.updateByPrimaryKey(model);
		}
	}

	@Override
	public void updateSelective(Map model) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("Model={}", JsonUtil.toString(model));
		}
		mapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public E getOne(K key) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("Key={}", JsonUtil.toString(key));
		}

		return this.mapper.selectByPrimaryKey(key);
	}

	@Override
	public List<E> getList() throws Exception {
		return this.mapper.selectAll();
	}

	@Override
	public void delete(K key) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("Key={}", JsonUtil.toString(key));
		}

		this.mapper.deleteByPrimaryKey(key);
	}

	@Override
	public void delete(List<K> keys) throws Exception {
		if(log.isDebugEnabled()){
			log.debug("Keys={}", JsonUtil.toString(keys));
		}

		for(K key : keys){
			this.mapper.deleteByPrimaryKey(key);
		}
	}
}
