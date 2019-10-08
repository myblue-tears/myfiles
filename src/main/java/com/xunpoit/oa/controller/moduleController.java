package com.xunpoit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.ModuleManager;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.web.PageModel;
/**
 * 模块管理控制器
 * 
 * */
@Controller
@RequestMapping(value="/module") //命名空间
public class moduleController {

	@Autowired
	private ModuleManager moduleManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public ModelAndView addUI(int pid) {
		ModelAndView mv = new ModelAndView("module/module_add");
		mv.addObject("pid", pid);
		return mv;
	}
	
	//真正的进行添加
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Module module,int pid) {
		System.out.println(12);
		//返回org.index界面
		moduleManager.addModule(module, pid);
		System.out.println(23);
		return "common/pub_add_success";
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		moduleManager.delModule(id);
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(Module module) {
		moduleManager.modifyModule(module);
		return "common/pub_update_success";
	}
	
	//查询
	@RequestMapping(value="/findModule",method=RequestMethod.GET)
	public String findPerson(int id,Model model) {
		Module module = moduleManager.findModuleById(id);
		model.addAttribute("module", module);
		return "module_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(int pid,Pager pager,Model model) {
		//由于在点击分页的时候会携带参数pager.offset，所以如果要获取后面的offset可以使用包装类
		PageModel<Module> pm = moduleManager.findAll(pid,pager==null?0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		model.addAttribute("pid", pid);
		return "module/index";
	}
	
	//获取分页pager属性
	@InitBinder("pager")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
