//package configTest;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisOperations;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//
//@WebAppConfiguration
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class RedisConfigTest {
//
//	@Autowired
//	private JedisConnectionFactory connFactory;
//	
//	@Autowired
//	private  RedisOperations<String, String> ops;
//	
//	
//	@Test
//	public void testNotNull(){
//		Assert.assertNotNull(connFactory);
//		Assert.assertNotNull(ops);
//		
//		Assert.assertNotNull(connFactory.getConnection());
//	}
//
//}
