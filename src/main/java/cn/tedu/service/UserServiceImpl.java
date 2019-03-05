package cn.tedu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.dao.UserDao;
import cn.tedu.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see cn.tedu.service.UserService#deleteUsers(java.lang.String)
	 */
	@Override
	@Transactional
	public void deleteUsers(String... ids) {
		for(String id:ids) {
			User user=userDao.findUserById(id);
			if(user==null) {
				throw new RuntimeException("id´íÎó:"+id);
			}
			userDao.deleteUser(user);
		}
	}
}
