package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.web.PageModel;

/**
 * 用户的增删改查
 * 
 * */
public interface UserManager {

	//增加用户
	public void addUser(User user,int personId);
	
	//根据主键id删除用户
	public void delUserById(int id);
	
	//修改用户
	public void modifyUser(User user);
	
	//根据主键id查询单个用户
	public User findUserById(int id);
	
	//分页显示所有用户，也就是所有人员
	public PageModel<Person> findAll(int offset,int pageSize);
	
	//查询所有角色，也就是在给用户分配角色之前先查询用户已经拥有的角色
	public List<UsersRoles> findUrsListByUser(int userId);
	
	//给用户添加用户角色
	public void addUsersRoles(UsersRoles urs);
	
	//删除用户的角色,根据用户角色的id
	public void delUsersRoles(int ursId);
	
	//在登录之前，要匹配用户的信息
	public User login(User user);
}
