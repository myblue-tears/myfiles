package com.xunpoit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Acl;
import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping(value="/acl") //命名空间
public class AclController {

	@Autowired
	private  ModuleManager moduleManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private UserManager userManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/initPage",method=RequestMethod.GET)
	public ModelAndView initPage(String mainType,int mainId) {
		
		ModelAndView mv = new ModelAndView("acl/index");
		//首先查询出所有的模块
		PageModel<Module> pm = moduleManager.findAll(0, 0, Integer.MAX_VALUE);
		mv.addObject("pm", pm);
		mv.addObject("mainType", mainType);
		mv.addObject("mainId", mainId);
		
		//查询是否是给用户还是juese授权，显示名字
		if(mainType.equals(Acl.TYPE_ROLE)) {//表示给角色授权
			Role role = roleManager.findRoleById(mainId);
			mv.addObject("role", role);
		}
		
		if(mainType.equals(Acl.TYPE_USER)) {//表示给用户授权
			User user = userManager.findUserById(mainId);
			mv.addObject("user", user);
		}
		
		return mv;
	}
	
}
