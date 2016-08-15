package com.xiaoxin.dao;

import java.util.List;



import com.xiaoxin.entity.User;

public class UserDao extends BaseDao {
	public User queryUserByOpenid(String openid){
		String hql = "from User where openid = ? ";
		List<User> list=hibernateTemplate.find(hql,openid);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

}
