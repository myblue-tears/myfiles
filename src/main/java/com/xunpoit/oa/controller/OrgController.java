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

import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping(value="/org") //命名空间
public class OrgController {

	@Autowired
	private OrgManager orgManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public ModelAndView addUI(int pid) {
		ModelAndView mv = new ModelAndView("org/org_add");
		mv.addObject("pid", pid);
		return mv;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Org org,int pid) {
		//返回org.index界面
		ModelAndView mv = new ModelAndView("common/pub_add_success");
		orgManager.addOrg(org, pid);
		return mv;
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		orgManager.delOrgById(id);
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(Org org) {
		orgManager.modifyOrg(org);
		return "update_success";
	}
	
	@RequestMapping(value="/findOrg",method=RequestMethod.GET)
	public String findOrg(int id,Model model) {
		Org org = orgManager.findOrgById(id);
		model.addAttribute("org", org);
		return "org_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(int pid,Pager pager,Model model) {
		//由于在点击分页的时候会携带参数pager.offset，所以如果要获取后面的offset可以使用包装类
		PageModel<Org> pm = orgManager.findAll(pid,pager==null?0:pager.getOffset(),3);
		//在进行添加操作的时候在哪一级页面上就在哪一个页面添加，所以也就是在当前页面的父id下面进行添加，所以也要保存pid
		model.addAttribute("pid", pid);
		//要返回的的话，就要查询到父父id，不然就只会查询到一个，无法获取上一级整级的目录
		model.addAttribute("ppid", 0);//表示存储，这是首页目录，不需要返回
		//此时查询父父id,
		if(pid>0) {
			Org parent = orgManager.findOrgById(pid);
			if(parent!=null&&parent.getParent()!=null) {
				model.addAttribute("ppid", parent.getParent().getId());
			}
		}
		model.addAttribute("pm", pm);
		return "org/index";
	}
	
	//获取分页pager属性
	@InitBinder("pager")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
