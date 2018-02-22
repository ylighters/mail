package com.qt.mail.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 */
public interface BaseDao<T> {
	
	int insert(T t);
	
	void save(Map<String, Object> map);
	
	void saveBatch(List<T> list);
	
	int updateByPrimaryKey(T t);
	
	int update(Map<String, Object> map);
	
	int deleteByPrimaryKey(Object id);
	
	int delete(Map<String, Object> map);
	
	int deleteBatch(Object[] id);

	T selectByPrimaryKey(Object id);
	
	List<T> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	int queryTotal();
}
