package cn.tedu.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import cn.tedu.entity.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	HibernateTemplate hibernateTemplate;

	@Override
	public int saveUser(User user) {
		Object id=hibernateTemplate.save(user);
		if(id!=null) {
			return 1;
		}
		return 0;
	}

	@Override
	public int deleteUser(User user) {
		hibernateTemplate.delete(user);
		return 1;
	}

	@Override
	public int updateUser(User user) {
		hibernateTemplate.update(user);
		return 1;
	}

	@Override
	public User findUserById(String id) {
		return hibernateTemplate.get(User.class, id);
	}

}
