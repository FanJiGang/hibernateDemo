package cn.tedu.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import cn.tedu.entity.User;

public class TestCase {
	
	SessionFactory factory;
	
	@Before
	public void init() {
		//读取配置文件
		Configuration cfg=new Configuration();
		cfg.configure("hibernate.cfg.xml");
		//创建Session的工厂
		factory=cfg.buildSessionFactory();
	}
	
	@Test
	public void testSession() {
		/*
		 * 创建Session对象
		 * Session的底层就是JDBC Connection
		 * 如果没有异常,就说明数据库连接成功
		 */
		Session session=factory.openSession();
		System.out.println(session);
		session.close();
	}
	
	@Test
	public void testCRUDUser() {
		User user1=new User("100","King","123","hahaha","Cat");
		Session session=null;
		Transaction ts=null;
		try {
			session=factory.openSession();
			ts=session.beginTransaction();//开启事务
			//1.测试增加功能
			//session.save(user1);//user1对象转换为持久状态
			//user1.setNick("Andy");//修改持久状态对象的属性,将影响数据库
			                        //不用调用session.update()也会更改数据库中的数据
			//2.测试根据主键查询数据
			User user2=(User)session.get(User.class, "100");
			System.out.println(user2);
			//3.测试更新数据
			user2.setToken("lalala!");
			user2.setName("Jim");
			session.update(user2);
			//4.测试删除数据
			session.delete(user2);
			ts.commit();//提交事务
		} catch (Exception e) {
			if(ts!=null) ts.rollback();//回滚事务 
			e.printStackTrace();//释放资源
		} finally {
			if(session!=null) session.close(); 
		}
	}
	
	@Test
	public void testUpdate() {
		Session session=null;
		Transaction ts=null;
		try {
			session=factory.openSession();
			ts=session.beginTransaction();
			//get方法返回的对象是持久状态的对象
			User user=(User)session.get(User.class, "100");
			//修改持久状态对象的属性,将影响数据库中的数据
			user.setName("老zhang");
			ts.commit();
		} catch (Exception e) {
			if(ts!=null) ts.rollback();
			e.printStackTrace();
		} finally {
			if(session!=null) session.close();
		}
	}
	
	@Test
	public void testEvict() {
		Session session=null;
		Transaction ts=null;
		try {
			session=factory.openSession();
			ts=session.beginTransaction();
			User user=(User)session.get(User.class, "100");
			System.out.println(user);
			//将对象user踢出session缓存,使其成为游离状态
			session.evict(user);
			//将所有对象踢出session缓存,使其成为游离状态
			//session.clear();
			//游离状态的对象,修改其属性不影响数据库
			user.setName("王老吉");
			user.setNick("Cat");
			//user成为持久状态
			session.update(user);
			ts.commit();
		} catch (Exception e) {
			if(ts!=null) ts.rollback();
			e.printStackTrace();
		} finally {
			if(session!=null) session.close();
		}
	}
}
