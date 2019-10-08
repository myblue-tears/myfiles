package com.xunpoit.oa.controller;

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
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.manager.PersonManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping(value="/person") //命名空间
public class PersonController {

	@Autowired
	private PersonManager personManager;
	
	@Autowired
	private OrgManager orgManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI() {
		return "person/person_add";
	}
	
	//在添加人员之前首先要选择所属机构
	@RequestMapping(value="/selectOrg",method=RequestMethod.GET)
	public String selectOrg(int pid,Pager pager,Model model) {
		PageModel<Org> pm = orgManager.findAll(pid,pager==null?0:pager.getOffset(),3);
		model.addAttribute("pid", pid);
		model.addAttribute("ppid", 0);//表示存储，这是首页目录，不需要返回
		model.addAttribute("pm", pm);
		if(pid>0) {
			Org parent = orgManager.findOrgById(pid);
			if(parent!=null&&parent.getParent()!=null) {
				model.addAttribute("ppid", parent.getParent().getId());
			}
		}
		return "person/select_org";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Person person,int orgId) {
		//返回org.index界面
		personManager.addPerson(person, orgId);
		return "common/pub_add_success";
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		personManager.delPerson(id);
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(Person person) {
		personManager.modifyPerson(person);
		return "common/pub_update_success";
	}
	
	@RequestMapping(value="/findPerson",method=RequestMethod.GET)
	public String findPerson(int id,Model model) {
		Person person = personManager.findPersonById(id);
		model.addAttribute("person", person);
		return "person_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Pager pager,Model model) {
		//由于在点击分页的时候会携带参数pager.offset，所以如果要获取后面的offset可以使用包装类
		PageModel<Person> pm = personManager.findAll(pager==null?0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		return "person/index";
	}
	
	//获取分页pager属性
	@InitBinder("pager")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
