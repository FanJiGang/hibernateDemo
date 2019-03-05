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
		//��ȡ�����ļ�
		Configuration cfg=new Configuration();
		cfg.configure("hibernate.cfg.xml");
		//����Session�Ĺ���
		factory=cfg.buildSessionFactory();
	}
	
	@Test
	public void testSession() {
		/*
		 * ����Session����
		 * Session�ĵײ����JDBC Connection
		 * ���û���쳣,��˵�����ݿ����ӳɹ�
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
			ts=session.beginTransaction();//��������
			//1.�������ӹ���
			//session.save(user1);//user1����ת��Ϊ�־�״̬
			//user1.setNick("Andy");//�޸ĳ־�״̬���������,��Ӱ�����ݿ�
			                        //���õ���session.update()Ҳ��������ݿ��е�����
			//2.���Ը���������ѯ����
			User user2=(User)session.get(User.class, "100");
			System.out.println(user2);
			//3.���Ը�������
			user2.setToken("lalala!");
			user2.setName("Jim");
			session.update(user2);
			//4.����ɾ������
			session.delete(user2);
			ts.commit();//�ύ����
		} catch (Exception e) {
			if(ts!=null) ts.rollback();//�ع����� 
			e.printStackTrace();//�ͷ���Դ
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
			//get�������صĶ����ǳ־�״̬�Ķ���
			User user=(User)session.get(User.class, "100");
			//�޸ĳ־�״̬���������,��Ӱ�����ݿ��е�����
			user.setName("��zhang");
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
			//������user�߳�session����,ʹ���Ϊ����״̬
			session.evict(user);
			//�����ж����߳�session����,ʹ���Ϊ����״̬
			//session.clear();
			//����״̬�Ķ���,�޸������Բ�Ӱ�����ݿ�
			user.setName("���ϼ�");
			user.setNick("Cat");
			//user��Ϊ�־�״̬
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
