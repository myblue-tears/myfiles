package com.xunpoit.oa.manager;
/**
 * 权限相关的管理类
 * */

import java.util.List;

import com.xunpoit.oa.entity.Module;

public interface AclManager {

	//添加或者更新权限,角色
	public void addOrUpdateAcl(String mainType,int mainId,int moduleId,int permission,boolean yes);
	
	//删除权限，也就是不启用权限
	public void delAcl(String mianType,int mainId,int moduleId);
	
	//更新是否是继承关系，不继承（0） 继承（1）
	public void updateExtendState(int userId,int moduleId,boolean yes);
	
	//查询所有的权限通过mainType和mainId
	public List findAllAclByMainTypeMainId(String mainType,int mainId);
	
	//登录后，查询出该用户所有具有读取权限的模块
	public List<Module> findAllModuleByUserId(int userId);
	
	//即时认证
	public boolean getPermission(int userId,int moduleId,int permission);
	
	//即时认证的方法
	public boolean getPermission(int userId,String moduleSn,int permission);
		
}

