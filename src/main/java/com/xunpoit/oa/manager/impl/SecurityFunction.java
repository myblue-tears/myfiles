package com.xunpoit.oa.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.manager.AclManager;

@Service
public class SecurityFunction {

	private static AclManager aclManager;
	
	
	public AclManager getAclManager() {
		return aclManager;
	}

	@Autowired
	public void setAclManager(AclManager aclManager) {
		SecurityFunction.aclManager = aclManager;
	}


	//进行即时认证的方法(描述成一个JSTL函数)
	public static boolean getPermission(int userId,String sn,int permission) {
		//在acl表中sn机构编码也是唯一的
		boolean b = aclManager.getPermission(userId, sn, permission);
		System.out.println(b);
		return b;
	}
}
