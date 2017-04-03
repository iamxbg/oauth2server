//package configTest;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
//@WebAppConfiguration
//public class JdbcConnectionTest {
//
//	
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	
//	@Test
//	public void testConnectionNotNull(){
//		
//		Assert.assertNotNull(sessionFactory);
//		
//		Session session=null;
//		try{
//		session=sessionFactory.openSession();
//		
//		//Assert.assertNull(session);
//		Assert.assertNotNull(session);
//		}finally{
//			if(session!=null) session.close();
//		}
//		
//	}
//}
