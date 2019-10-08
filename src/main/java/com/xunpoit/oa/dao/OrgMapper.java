package com.xunpoit.oa.dao;

import java.util.List;
import java.util.Map;

import com.xunpoit.oa.entity.Org;

public interface OrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Org record);

    int insertSelective(Org record);

    Org selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Org record);

    int updateByPrimaryKey(Org record);

    //也要传递三个参数，offset，pageSize，pid，给mybatis传递多个值，可以都放在map集合里面
	List<Org> findAllByParent(Map<String, Integer> paramMap);

	//查询总条数，根据pid查询
	long selectCount(int pid);
}