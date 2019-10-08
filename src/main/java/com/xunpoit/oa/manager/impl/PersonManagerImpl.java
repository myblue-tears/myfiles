package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.web.PageModel;

//人员的服务类，调用接口mapperdao类

@Service
public class PersonManagerImpl implements PersonManager {
	
	//声明mapper接口类，方便调用其中增删改查的方法
	@Autowired
	private PersonMapper personMapper;
	
	//在添加之前需要先查询出来所属机构，所以也要声明机构的mapper接口类
	@Autowired
	private  OrgMapper orgMapper;

	public void addPerson(Person person,int orgId) {
		//在添加之前需要先查询出所属机构，就是在真正添加之前获取选择的orgid
		Org org = orgMapper.selectByPrimaryKey(orgId);
		person.setOrg(org);
		//最后进行插入人员数据
		personMapper.insertSelective(person);
		
	}

	//删除人员,根据主键id进行删除
	public void delPerson(int id) {
		personMapper.deleteByPrimaryKey(id);
	}

	//修改人员
	public void modifyPerson(Person person) {
		personMapper.updateByPrimaryKeySelective(person);
	}

	//分页显示所有人员
	public PageModel<Person> findAll(int offset, int pageSize) {
		//要用分页模型
		PageModel<Person> pm = new PageModel<Person>();
		//用map集合存储多个值
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		List<Person> dataList = personMapper.findAll(paramMap);
		//给模型赋值
		pm.setDataList(dataList);
		pm.setPageSize(pageSize);
		//要查询总条数
		long items = personMapper.selectCount();
		
		pm.setItems((int)items);
		return pm;
	}

	//根据主键id查询单个人员
	public Person findPersonById(int id) {
		return personMapper.selectByPrimaryKey(id);
	}

}
