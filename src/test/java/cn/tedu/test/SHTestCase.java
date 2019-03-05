package cn.tedu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.tedu.dao.UserDao;
import cn.tedu.entity.User;
import cn.tedu.service.UserService;

public class SHTestCase {
	
	ApplicationContext ctx;
	SessionFactory factory;
	
	@Before
	public void init() {
		String[] config= {"spring-orm.xml"};
		ctx=new ClassPathXmlApplicationContext(config);
		factory=ctx.getBean("sessionFactory",SessionFactory.class);
	}
	
	@Test
	public void testFactory() {
		Session session=null;
		try {
			session=factory.openSession();
			System.out.println(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@Test
	public void testHibernateTemplate() {
		HibernateTemplate template=ctx.getBean("hibernateTemplate",HibernateTemplate.class);
		User user=new User("200","John","321","..","");
		/*
		 * 相对于Session对象HibernateTemplate对象使用更加简便.
		 * HibernateTemplate由Spring提供的,是对Session的封装,是一个更加简便的API
		 */
		template.save(user);
	}
	
	@Test
	public void testUserDao() {
		UserDao userDao=ctx.getBean("userDao",UserDao.class);
		//增加数据
		int saveNum=userDao.saveUser(new User("200","John","321","..",""));
		System.out.println("saveUser():"+saveNum);
		//查询数据
		User user=userDao.findUserById("200");
		System.out.println("findUserById():"+user);
		//更新数据
		//user.setName("王某");
		//int updateNum=userDao.updateUser(user);
		//System.out.println("updateUser():"+updateNum);
		//删除数据
		//int deleteNum=userDao.deleteUser(user);
		//System.out.println("deleteUser()"+deleteNum);
	}
	
	@Test
	public void testUserService() {
		//测试事务是否正常提交或回滚
		UserService service=ctx.getBean("userService",UserService.class);
		service.deleteUsers("100","200");
		/*
		 * Spring底层自动的使用JDK动态代理或者CGLIB代理:
		 * 1.当对象有接口时使用JDK动态代理
		 * 2.当对象没有接口时使用CGLIB代理
		 */
	}
}
