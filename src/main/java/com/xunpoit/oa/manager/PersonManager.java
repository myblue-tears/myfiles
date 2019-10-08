package com.xunpoit.oa.manager;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.web.PageModel;

/**
 * 定义人员的增删改查方法
 * */
public interface PersonManager {

	//增加人员
	public void addPerson(Person person,int orgId);
	
	//删除人员,根据id删除
	public void delPerson(int id);
	
	//修改人员
	public void modifyPerson(Person person);
	
	//查询单个人员
	Person findPersonById(int id);
	
	//分页查询所有
	
	PageModel<Person> findAll(int offset,int pageSize);
}



