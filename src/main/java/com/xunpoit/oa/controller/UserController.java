package com.xunpoit.oa.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xunpoit.oa.entity.Module;
import com.xunpoit.oa.entity.Pager;
import com.xunpoit.oa.entity.Person;
import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;
import com.xunpoit.oa.entity.UsersRoles;
import com.xunpoit.oa.manager.AclManager;
import com.xunpoit.oa.manager.RoleManager;
import com.xunpoit.oa.manager.UserManager;
import com.xunpoit.oa.web.PageModel;

@Controller
@RequestMapping(value="/user") //命名空间
public class UserController {

	//用户的查询实际上就是查询人员，所以要引入人员的管理类
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private AclManager aclManager;
	
	//从超链接访问的控制器，请求方式必须是get请求的方式，否则会报错
	@RequestMapping(value="/addUI",method=RequestMethod.GET)
	public String addUI(int personId,Model model) {
		model.addAttribute("personId", personId);
		System.out.println(personId);
		return "user/user_add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(User user,int personId) {
		//在添加的时候是给哪一个人员添加用户，所以需要制定personId
		userManager.addUser(user, personId);
		return "common/pub_add_success";
	}
	
	//删除
	@RequestMapping(value="/del",method=RequestMethod.GET)
	public String del(int id) {
		userManager.delUserById(id);
		return "common/pub_del_success";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(User user) {
		userManager.modifyUser(user);
		return "common/pub_update_success";
	}
	
	@RequestMapping(value="/findUser",method=RequestMethod.GET)
	public String findUser(int id,Model model) {
		User user = userManager.findUserById(id);
		model.addAttribute("user", user);
		return "user_info";
	}
	
	@RequestMapping(value="/findAll",method=RequestMethod.GET)
	public String findAll(Pager pager,Model model) {
		//由于在点击分页的时候会携带参数pager.offset，所以如果要获取后面的offset可以使用包装类
		PageModel<Person> pm = userManager.findAll(pager==null?0:pager.getOffset(),3);
		model.addAttribute("pm", pm);
		return "user/index";
	}
	
	/**
	 * 给用户分配角色，只能是用户才拥有角色，人员是不能直接分配角色的
	 * 第一步：查询用户已经拥有的角色
	 * */
	@RequestMapping(value="/findRoleList",method=RequestMethod.GET)
	public String findRoleList(int userId,Model model) {
		//要修改，因为还要设置优先级，角色role里面没有优先级的属性，UsersRoles里面才有
		List<UsersRoles> ursList = userManager.findUrsListByUser(userId);
		model.addAttribute("ursList",ursList);
		//分配角色的时候还要显示用户姓名，所以要根据userId查询出用户
		User user = userManager.findUserById(userId);
		model.addAttribute("user", user);
		return "user/role_list";
	}
	
	//给用户分配角色第二部，addUrsUI,查询所有角色，跳转到分配角色页面addUrsUI
	@RequestMapping(value="/addUrsUI",method=RequestMethod.GET)
	public String addUrsUI(int userId,Model model) {
		List<Role> roleList = roleManager.findAll();
		model.addAttribute("roleList", roleList);
		//要给哪个用户分配角色，用以也要将userId保存要在页面上用
		model.addAttribute("userId", userId);
		return "user/urs_addui";
	}
	
	//给用户分配角色第三步，addUrsUI,查询所有角色，跳转到分配角色页面addUrsUI
	@RequestMapping(value="/addUrs",method=RequestMethod.POST)
	public String addUrs(UsersRoles urs,Model model) {
		userManager.addUsersRoles(urs);
		return "common/pub_add_success";
	}
	
	//给用户分配角色的第四步，删除分配的 角色
	@RequestMapping(value="/delUrs",method=RequestMethod.GET)
	public String delUrs(int ursId,Model model) {
		userManager.delUsersRoles(ursId);
		return "common/pub_del_success";
	}
	
	//用户登录时要访问的控制器
	//给用户分配角色的第四步，删除分配的 角色
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(User user,HttpSession session) {
		User loginUser = userManager.login(user);
		//判断用户是否为空
		if(loginUser!=null) {//说明在数据库能够找到,保存user
			session.setAttribute("loginUser", loginUser);
			//根据用户的不同要显示不同的模块，所以要查询该用户所具有读取权限的 模块集合并保存
			List<Module> moduleList = aclManager.findAllModuleByUserId(loginUser.getId());
			session.setAttribute("moduleList", moduleList);
			//System.out.println("拥有模块个数："+moduleList.size());
			return "index";
		}
		//否则返回首页重新进行登录
		return "login";
	}
	
	//获取分页pager属性
	@InitBinder("pager")
	public void initBinder2(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("pager.");
	}
	
}
