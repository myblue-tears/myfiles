package com.xunpoit.oa.entity;

public class Acl {
	
	// 标示类型的常量，表示类型为角色
	public static final String TYPE_ROLE = "Role";
	// 标示类型的常量，表示类型为用户
	public static final String TYPE_USER = "User";
	// 授权允许
	public static final int ACL_YES = 1;
	// 授权不允许
	public static final int ACL_NO = 0;
	// 授权不确定
	public static final int ACL_NEUTRAL= -1;

    private Integer id;

    private String mainType;

    private int mainId;

    private int moduleId;

    private int aclState;

    private int extendState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMainType() {
		return mainType;
	}

	public void setMainType(String mainType) {
		this.mainType = mainType;
	}

	public int getMainId() {
		return mainId;
	}

	public void setMainId(int mainId) {
		this.mainId = mainId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getAclState() {
		return aclState;
	}

	public void setAclState(int aclState) {
		this.aclState = aclState;
	}

	public int getExtendState() {
		return extendState;
	}

	public void setExtendState(int extendState) {
		this.extendState = extendState;
	}
	
	/**授权方法，0123.就是表示增删改查
	 * 例如：传值 0.true
	 * 		调用方法开始 1《《0   0001
	 * 		如果最初授权   0010
	 * 		在判断之后决定取反还是或   此时应该是0011
	 * 		如果取反的话，相同一样，不同就取反，
	 * 
	 * */
	public void setPermission(int permission,boolean yes){
		int temp = 1;
		temp = temp << permission;
		if(yes){
			aclState = aclState | temp; // 结果是授权位必然为1,有一个1就是1
		}else{
			temp = ~temp; // temp取反 11 11...1110 ，
			aclState = aclState & temp;
		}
	}

	//认证的方法
	public int getPermission(int permission){
		//如果extendState = 1，表示是继承，
		//使用角色的权限，所以返回不确定（因为要看角色的权限情况）
		if(extendState == 1){
			return ACL_NEUTRAL;
		}
		//计算某一位上的值是否为1
		int temp = 1;
		temp <<= permission; 
		temp &= aclState;
		if(temp > 0){
			return ACL_YES;
		}else{
			return ACL_NO;
		}
}

}