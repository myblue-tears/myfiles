package com.xunpoit.oa.manager;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.util.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AclManagerTest {

	@Autowired
	private AclManager aclManager;
	
	//测试添加或者更新授权
	@Test
	public void testAddOrUpdateAcl() {
		//这表示用户
		//aclManager.addOrUpdateAcl(Acl.TYPE_USER, 1, 1, Permission.CRUD_D, false);
		//表示角色:角色5（管理员） 对模块30可读
		aclManager.addOrUpdateAcl(Acl.TYPE_ROLE, 5, 30, Permission.CRUD_R, true);;
		System.out.println("ok...");
	}
	
	//测试删除权限
	@Test
	public void testDelAcl() {
		aclManager.delAcl(Acl.TYPE_USER, 1, 2);
		System.out.println("del....ok");
	}
	
	//测试继承状态：哪一个用户对哪一个模块的继承状态（只能是用户，角色不可以继承）
	@Test
	public void testUpdateExtState() {
		//false：表示继承，true：表示不继承，跟页面的显示有关
		aclManager.updateExtendState(1, 1, true);
		System.out.println("updateExtState ...ok");
	}
	
	//查询某一角色对模块的所有权限
	@Test
	public void testFindAllAclByMainTypeMainId() {
		//查询系统管理员的所有权限
		List aclList = aclManager.findAllAclByMainTypeMainId(Acl.TYPE_ROLE, 1);
		for(int i=0;i<aclList.size();i++) {
			int a[] = (int[])aclList.get(i);
			System.out.println(a[0]+"  "+a[1]+"  "+a[2]+"  "+a[3]+"  "+a[4]+"  "+a[5]);
		}
		System.out.println("findAllAclByMainTypeMainId...ok");
	}
	
	//查询根据用户id查询所有模块
	@Test
	public void testFindAllModuleByUserId() {
		//12对应角色是系统管理员，查询这个对于哪个模块有读取权限，此时应该是每个模块都有12
		List<Module> moduleList = aclManager.findAllModuleByUserId(3);
		for(Module module:moduleList) {
			//打印该用户对应的角色所有具有读取权限的模块
			System.out.println(module.getName()+"  "+module.getId());
		}
		
	}
	
	//测试某一个用户对于某一模块是否拥有某一个具体的权限
	@Test
	public void testGetPermission() {
		boolean b = aclManager.getPermission(3, 30,Permission.CRUD_U);
		if(b) {
			System.out.println("允许操作");
		}else {
			System.out.println("不允许");
		}
	}
	
}
