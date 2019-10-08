package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.ModuleMapper;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.web.PageModel;
/**
 * 服务层，调用mapperdao接口层
 * 
 * */
@Service
public class ModuleManagerImpl implements ModuleManager {

	//注入接口管理
	@Autowired
	private ModuleMapper moduleMapper;
	
	public void addModule(Module module, int pid) {

		//首先判断pid是否大于0
		if(pid>0) {
			//存在父模块，所以首先得查询出父模块,在页面点击的时候会存储隐藏文本域，在哪一级页面模块就是有当代你模块的pid
			Module parent = moduleMapper.selectByPrimaryKey(pid);
			module.setParent(parent);
		}
		//此时进行添加
		moduleMapper.insertSelective(module);
	}

	//根据主键id删除模型
	public void delModule(int id) {
		moduleMapper.deleteByPrimaryKey(id);
	}

	//修改模型
	public void modifyModule(Module module) {
		moduleMapper.updateByPrimaryKeySelective(module);
	}

	//根据主键查询单个模型
	public Module findModuleById(int id) {
		return moduleMapper.selectByPrimaryKey(id);
	}

	//分页查询
	public PageModel<Module> findAll(int pid, int offset, int pageSize) {
		PageModel<Module> pm = new PageModel<Module>();
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		//将值存储在map集合里面进行传递
		paramMap.put("pid", pid);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		List<Module> dataList = moduleMapper.findAll(paramMap);
		pm.setDataList(dataList);;
		pm.setPageSize(pageSize);
		
		long  items = moduleMapper.selectCount(pid);
		pm.setItems((int)items);
		
		return pm;
	}

}
