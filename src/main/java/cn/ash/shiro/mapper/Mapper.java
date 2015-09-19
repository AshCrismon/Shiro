package cn.ash.shiro.mapper;

import java.util.List;

public interface Mapper<T> {
	
	public T findById(String id);
	
	public List<T> findAll();
	
	public int add(T t);
	
	public int delete(String id);
	
	public int update(T t);
}
