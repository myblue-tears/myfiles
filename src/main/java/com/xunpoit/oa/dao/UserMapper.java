package com.xunpoit.oa.dao;

import java.util.List;

import com.xunpoit.oa.entity.Role;
import com.xunpoit.oa.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User login(User user);
	

}