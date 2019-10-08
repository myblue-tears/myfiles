package com.xunpoit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.web.PageModel;
/**
 * 模块管理控制器
 * 
 * */
@Controller
@RequestMapping(value="/role") //命名空间
public class RoleController {

	@Autowired
	private RoleManager roleManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		return "role/role_add";
	}
	
	//真正的进行添加
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Role role) {
		//返回org.index界面
		roleManager.addRole(role);
		return "common/pub_add_success";
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		roleManager.delRole(id);
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(Role role) {
		roleManager.modifyRole(role);
		return "common/pub_update_success";
	}
	
	//查询
	@RequestMapping(value="/findRole",method=RequestMethod.GET)
	public String findRole(int id,Model model) {
		Role role = roleManager.findRoleById(id);
		model.addAttribute("role", role);
		return "role_info";
	}
	
	//查询所有角色不需要分页
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Model model) {
		List<Role> roleList = roleManager.findAll();
		model.addAttribute("roleList", roleList);
		return "role/index";
	}
	
	
	
}
