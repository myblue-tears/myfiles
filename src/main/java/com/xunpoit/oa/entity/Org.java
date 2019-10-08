package com.xunpoit.oa.entity;

import java.util.List;

/**
 * 组织机构类：树形结构，有父机构和子机构
 * 
 * */
public class Org {
	//编号
    private Integer id;
    //名称
    private String name;

    //编码：如果没有父机构，那么编码=id，如果有子机构，那么编码=父机构编码+"_"+id
    private String sn;

    //描述
    private String description;

    //父机构，多对一，多个孩子对应
    private Org parent;
    
    //子机构
    private List<Org> childList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

	public Org getParent() {
		return parent;
	}

	public void setParent(Org parent) {
		this.parent = parent;
	}

	public List<Org> getChildList() {
		return childList;
	}

	public void setChildList(List<Org> childList) {
		this.childList = childList;
	}

   
}