package com.xunpoit.oa.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xunpoit.oa.dao.OrgMapper;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.manager.OrgManager;
import com.xunpoit.oa.web.PageModel;
/**
 * 组织机构Org的实现类，相当于服务类要调用数据类也就是mapper类
 * */
@Service
public class OrgManagerImpl implements OrgManager {
	
	/**首先得声明接口类，方便之后调用一系列接口mapper方法，
	 * mapper方法直接调用mapper.xml加载mybatis框架实现具体功能
	 * 采用注解显示自动注入,根据类型注入
	 * */
	@Autowired
	private OrgMapper orgMapper;
	
	//添加机构，判断是否有父机构
	public void addOrg(Org org, int pid) {
		if(pid>0){//有父节点，首先得查询出父节点
			Org parent = orgMapper.selectByPrimaryKey(pid);
			org.setParent(parent);
		}
		//最后进行插入
		orgMapper.insertSelective(org);
		//由于在配置文件里面设置了自动生成id的方法，在添加的时候id就有了值
		//设置sn,sn是字符类型，默认是如果没有子节点，那么sn编号就是自己的id编号，进行拼串
		String sn = ""+org.getId();
		if(pid>0) {//表示有父节点，此时先可以获取自己的id
			Org parent = orgMapper.selectByPrimaryKey(pid);
			sn = parent.getSn()+"_"+org.getId();
		}
		//最后设置sn并且更新
		org.setSn(sn);
		orgMapper.updateByPrimaryKeySelective(org);
		//不用自己手动开启事务管理，在配置文件里设置了声明式事务
	}

	//删除机构
	public void delOrgById(int id) {
		//如果有子结构不能直接删除，所以先要进行查询
		Org org = orgMapper.selectByPrimaryKey(id);
		//判断是否有子机构
		if(org.getChildList().size()>0) {//表示有子结构，可以抛出一个异常
			throw new RuntimeException("操作错误：有子机构，不能直接删除");
		}
		orgMapper.deleteByPrimaryKey(id);
	}

	public void modifyOrg(Org org) {
		orgMapper.updateByPrimaryKeySelective(org);
	}

	public Org findOrgById(int id) {
		return orgMapper.selectByPrimaryKey(id);
	}

	public PageModel<Org> findAll(int pid,int offset,int pageSize) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		//new出pageModel的实例
		PageModel<Org> pm = new PageModel<Org>();
		paramMap.put("pid", pid);
		paramMap.put("offset", offset);
		paramMap.put("pageSize", pageSize);
		
		List<Org> orgList = orgMapper.findAllByParent(paramMap);
		pm.setDataList(orgList);
		pm.setPageSize(pageSize);
		//还要设置总条数
		long items = orgMapper.selectCount(pid);
		pm.setItems((int)items);
		return pm;
	}

}
