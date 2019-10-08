package com.xunpoit.oa.manager;

import java.util.List;

import com.xunpoit.oa.entity.Role;

/**
 * 业务逻辑层
 * 角色的增删改查方法
 * */
public interface RoleManager {

	//添加角色
	public void addRole(Role role);
	
	//删除角色
	public void delRole(int id);
	
	//修改角色
	public void modifyRole(Role role);
	
	//根据id查询单个角色
	public Role findRoleById(int id);
	
	//显示所有角色，不用分页
	public List<Role> findAll();
}
