package cn.tedu.dao;

import cn.tedu.entity.User;

public interface UserDao {
	int saveUser(User user);
	int deleteUser(User user);
	int updateUser(User user);
	User findUserById(String id);
}
