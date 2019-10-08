package com.xunpoit.oa.manager;

import java.util.List;
import com.xunpoit.oa.entity.Org;
import com.xunpoit.oa.web.PageModel;

public interface OrgManager {

	//就是一些增删改查的方法
	/**
	 * 在添加的时候要指定父机构
	 * */
	public void addOrg(Org org,int pid);
	
	/**
	 * 删除操作，如果有子结构，则不能直接而删除该机构
	 * 
	 * */
	public void delOrgById(int id);
	
	/**
	 * 修改
	 * */
	public void modifyOrg(Org org);
	
	/**
	 * 查询单个根据主键
	 * */
	public Org findOrgById(int id);
	
	/**
	 * 根据父机构来查询子机构
	 * 实现分页查询所有，改造方法
	 * 在提供的分页模型框架里面已经设置了起始位置，每页数也已经定义好
	 * */
	public PageModel<Org> findAll(int pid,int offset,int pageSize);
	
}
