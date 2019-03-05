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
		 * �����Session����HibernateTemplate����ʹ�ø��Ӽ��.
		 * HibernateTemplate��Spring�ṩ��,�Ƕ�Session�ķ�װ,��һ�����Ӽ���API
		 */
		template.save(user);
	}
	
	@Test
	public void testUserDao() {
		UserDao userDao=ctx.getBean("userDao",UserDao.class);
		//��������
		int saveNum=userDao.saveUser(new User("200","John","321","..",""));
		System.out.println("saveUser():"+saveNum);
		//��ѯ����
		User user=userDao.findUserById("200");
		System.out.println("findUserById():"+user);
		//��������
		//user.setName("��ĳ");
		//int updateNum=userDao.updateUser(user);
		//System.out.println("updateUser():"+updateNum);
		//ɾ������
		//int deleteNum=userDao.deleteUser(user);
		//System.out.println("deleteUser()"+deleteNum);
	}
	
	@Test
	public void testUserService() {
		//���������Ƿ������ύ��ع�
		UserService service=ctx.getBean("userService",UserService.class);
		service.deleteUsers("100","200");
		/*
		 * Spring�ײ��Զ���ʹ��JDK��̬�������CGLIB����:
		 * 1.�������нӿ�ʱʹ��JDK��̬����
		 * 2.������û�нӿ�ʱʹ��CGLIB����
		 */
	}
}
