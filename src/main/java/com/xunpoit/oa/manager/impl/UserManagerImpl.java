package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.PersonMapper;
import com.xunpoit.oa.dao.UserMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;
/**
 * 用户表的服务层
 * 
 * */
@Service
public class UserManagerImpl implements UserManager {

	//声明mapper接口
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private UsersRolesMapper ursMapper;
	
	public void addUser(User user,int personId) {
		//在添加用户之前首先要先把person查询出来，因为是在所以已知的人员里面创建爱你用户，
		Person person = personMapper.selectByPrimaryKey(personId);
		user.setPerson(person);
		userMapper.insertSelective(user);
	}

	public void delUserById(int id) {
		userMapper.deleteByPrimaryKey(id);
	}

	public void modifyUser(User user) {
		userMapper.updateByPrimaryKeySelective(user);
	}

	public User findUserById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	//分页查询所有
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

	public List<UsersRoles> findUrsListByUser(int userId) {
		//可以根据用户查询所有的角色
		return ursMapper.selectUsersRolesListByUser(userId);
	}

	//添加用户角色表
	public void addUsersRoles(UsersRoles urs) {
		ursMapper.insertSelective(urs);
	}

	//删除用户角色表的信息
	public void delUsersRoles(int ursId) {
		ursMapper.deleteByPrimaryKey(ursId);
	}

	//登录界面需要的user
	public User login(User user) {
		return userMapper.login(user);
	}

}
