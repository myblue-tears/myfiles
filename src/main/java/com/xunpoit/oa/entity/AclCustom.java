package com.xunpoit.oa.entity;

public class AclCustom {

	private int moduleId;
	
	private int crudCreate;
	
	private int crudRead;
	
	private int crudUpdate;
	
	private int crudDelete;
	
	private int extState;

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getCrudCreate() {
		return crudCreate;
	}

	public void setCrudCreate(int crudCreate) {
		this.crudCreate = crudCreate;
	}

	public int getCrudRead() {
		return crudRead;
	}

	public void setCrudRead(int crudRead) {
		this.crudRead = crudRead;
	}

	public int getCrudUpdate() {
		return crudUpdate;
	}

	public void setCrudUpdate(int crudUpdate) {
		this.crudUpdate = crudUpdate;
	}

	public int getCrudDelete() {
		return crudDelete;
	}

	public void setCrudDelete(int crudDelete) {
		this.crudDelete = crudDelete;
	}

	public int getExtState() {
		return extState;
	}

	public void setExtState(int extState) {
		this.extState = extState;
	}
	
}
