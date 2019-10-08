package com.xunpoit.oa.manager;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.web.PageModel;

/**
 * 模块的增删改查
 * */
public interface ModuleManager {

	//增加模块，要指定在哪一个层级下添加模块，所以要指定pid
	public void addModule(Module module,int pid);
	
	//删除模块,根据id主键来删除
	public void delModule(int id);
	
	//修改模块
	public void modifyModule(Module module);
	
	//查询单个模块
	public Module findModuleById(int id);
	
	//分页显示所有模块
	public PageModel<Module> findAll(int pid,int offset,int pageSize);
}
