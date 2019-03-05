package cn.tedu.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestDB {
	
	@Test
	public void testConn(){
		ClassPathXmlApplicationContext ac=new ClassPathXmlApplicationContext("spring-orm.xml");
		BasicDataSource bds=ac.getBean("dataSource",BasicDataSource.class);
		try {
			Connection conn=bds.getConnection();
			System.out.println(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
