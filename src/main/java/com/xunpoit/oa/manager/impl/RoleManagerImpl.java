package com.xunpoit.oa.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.RoleMapper;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.RoleManager;
/**
 * 角色类的服务层
 * */
@Service
public class RoleManagerImpl implements RoleManager {

	//声明接口
	@Autowired
	private RoleMapper roleMapper;
	
	public void addRole(Role role) {
		roleMapper.insertSelective(role);
	}

	public void delRole(int id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	public void modifyRole(Role role) {
		roleMapper.updateByPrimaryKeySelective(role);
	}

	public Role findRoleById(int id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	//查询所有角色，不用进行分页
	public List<Role> findAll() {
		return roleMapper.findAll();
	}

}
