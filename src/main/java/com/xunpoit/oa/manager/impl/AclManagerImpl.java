package com.xunpoit.oa.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.AclMapper;
import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.dao.UsersRolesMapper;
import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.AclCustom;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.AclManager;
/**
 *权限管理类的实现类 
 * */
@Service("aclManager")
public class AclManagerImpl implements AclManager {
	
	@Autowired
	private AclMapper aclMapper;
	
	@Autowired
	private UsersRolesMapper ursMapper;
	
	@Autowired
	private ModuleMapper moduleMapper;

	public void addOrUpdateAcl(String mainType, int mainId, int moduleId, int permission, boolean yes) {
		
		Acl acl = this.findAcl(mainType, mainId, moduleId);
		//进行判断acl是否为空
		if(acl!=null) {//不为空，进行更新操作
			acl.setPermission(permission, yes);
			aclMapper.updateByPrimaryKeySelective(acl);
			//然后可以之后接返回不用进行下面的操作
			return;
		}
		
		//表示添加，new出acl的实例
		acl = new Acl();
		acl.setMainType(mainType);
		acl.setMainId(mainId);
		acl.setModuleId(moduleId);
		acl.setPermission(permission, yes);
		aclMapper.insertSelective(acl);
	}

	private Acl findAcl(String mainType, int mainId, int moduleId) {
		//首先得查询是否存在acl,如果存在就更新，不存在就添加
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mainType", mainType);
		paramMap.put("mainId", mainId);
		paramMap.put("moduleId", moduleId);
		Acl acl = aclMapper.selectAclByMain(paramMap);
		return acl;
	}

	//删除acl之前要先进行查询
	public void delAcl(String mainType, int mainId, int moduleId) {
		Acl acl = this.findAcl(mainType, mainId, moduleId);
		if(acl!=null) {
			//就是用查询出来的acl的主键删除
			aclMapper.deleteByPrimaryKey(acl.getId());
		}
	}

	//更新之前也要先进行查询
	public void updateExtendState(int userId, int moduleId, boolean yes) {
		Acl acl = this.findAcl(Acl.TYPE_USER, userId, moduleId);
		if(acl!=null) {
			acl.setExtendState(yes?0:1);
			aclMapper.updateByPrimaryKeySelective(acl);
		}
	}

	@SuppressWarnings("rawtypes")
	public List findAllAclByMainTypeMainId(String mainType, int mainId) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mainType", mainType);
		paramMap.put("mainId", mainId);
		List<AclCustom> aclCustomList = aclMapper.findAclByMainTypeMainId(paramMap);
		
		//二维数组，通过遍历赋值
		List list = new ArrayList();
		for(int i=0;i<aclCustomList.size();i++) {
			AclCustom aclCustom = aclCustomList.get(i);
			//每一次遍历就new出一个二维数组
			int[] array = new int[6];
			array[0] = aclCustom.getModuleId();
			array[1] = aclCustom.getCrudCreate();
			array[2] = aclCustom.getCrudRead();
			array[3] = aclCustom.getCrudUpdate();
			array[4] = aclCustom.getCrudDelete();
			array[5] = aclCustom.getExtState();
			//要将数组放在一个集合中
			list.add(array);
		}
		return list;
	}

	
	//查询该用户所有具有读取权限的模块
	public List<Module> findAllModuleByUserId(int userId) {
		Map<Integer, Acl> aclMap = new HashMap<Integer, Acl>();
		/**
		 *1、查询用户拥有的角色的所有授权,降序
			2、查询用户的所有授权，并与1中的授权进行合并
			3、去除那些没有读取权限的模块
			4、返回有读取权限的module集合
		 * 
		 * */
		//第一步
		Map<String,Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userId", userId);
		paramMap.put("desc", 1);//表示降序
		List<Role> roleList = ursMapper.selectRoleListByUser(paramMap);
		
		for(Role role:roleList) {
			Map<String, Object> map = new  HashMap<String, Object>();
			map.put("mainType", Acl.TYPE_ROLE);
			map.put("mainId", role.getId());
			List<Acl> aclList = aclMapper.finaAllAclListByMainTypeMainId(map);
			for(Acl acl:aclList) {
				aclMap.put(acl.getModuleId(), acl);
			}
		}
		
		//第二步:
		Map<String, Object> map = new  HashMap<String, Object>();
		map.put("mainType", Acl.TYPE_USER);
		map.put("mainId", userId);
		List<Acl> aclList = aclMapper.finaAllAclListByMainTypeMainId(map);
		for(Acl acl:aclList) {
			aclMap.put(acl.getModuleId(), acl);
		}
		
		//第三步
		Set<Entry<Integer, Acl>> entrySet = aclMap.entrySet();
		List<Integer> keyList = new ArrayList<Integer>();
		for(Entry<Integer,Acl> entry:entrySet) {
			int key = entry.getKey();
			Acl acl = entry.getValue();
			int permission = acl.getPermission(1);//1：表示读取
			if(permission==Acl.ACL_NO) {//没有读的权限
				keyList.add(key);//这里保存所有没有读取权限的
			}
		}
		for(int moduleId:keyList) {
			aclMap.remove(moduleId);//移除没有读取权限的模块
		}
		
		//第四步：返回具有读取权限的
		Set<Integer> keySet = aclMap.keySet();
		List<Integer> idList = new ArrayList<Integer>(keySet);
		
		List<Module> moduleList = moduleMapper.findAllModuleListByKey(idList);
		
		return moduleList;
	}
	
	//实时认证
	public boolean getPermission(int userId, int moduleId, int permission) {
		/**
		 *首先查询是否给用户授权了，如果授权了，则使用 
		 * 如果没有直接授权给用户，那么逐个查询该用户所拥有的 角色的授权（按照角色的优先级升序排列）
		 * 
		 * */
		Acl acl = this.findAcl(Acl.TYPE_USER, userId, moduleId);
		if(acl!=null) {
			//有单独授权
			if(acl.getPermission(permission)!=Acl.ACL_NEUTRAL) {//判断授权是否是不确定
				return acl.getPermission(permission)==1?true:false;
			}
		}
		
		//表示没有授权，查询该用户的拥有角色按优先级,重新定义查询所有角色的方法
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("userId", userId);
		paramMap.put("desc", 0);//0表示升序，1表示降序
		List<Role> roleList = ursMapper.selectRoleListByUser(paramMap);
		
		//将取到的角色逐一循环遍历，看是否有授权
		for(Role role:roleList) {
			acl = this.findAcl(Acl.TYPE_ROLE, role.getId(), moduleId);
			if(acl!=null) {
				return acl.getPermission(permission)==1?true:false;
			}
		}
		
		return false;
	}

	//即时认证的方法
	public boolean getPermission(int userId, String moduleSn, int permission) {
		//定义一个根据sn机构编码找到模块id的方法
		int moduleId = moduleMapper.findModuleIdBySn(moduleSn);
		
		return this.getPermission(userId, moduleId, permission);
	}

}
