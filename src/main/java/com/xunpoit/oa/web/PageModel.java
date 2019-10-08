package com.xunpoit.oa.web;

import java.util.List;

/**分页模型
 * 
 * */
public class PageModel<E> {

	//总条数
	private int items;
	//每页多少条
	private int pageSize;
	
	//每页显示的数据
	private List<E> dataList;
	
	public int getItems() {
		return items;
	}
	public void setItems(int items) {
		this.items = items;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<E> getDataList() {
		return dataList;
	}
	public void setDataList(List<E> dataList) {
		this.dataList = dataList;
	}
	
}
