package oauthServer.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.context.annotation.ComponentScan.Filter;

@ComponentScan(basePackages={"oauthServer"}
,excludeFilters={@Filter(type=FilterType.ANNOTATION,value={EnableWebMvc.class})})
@Configuration
@EnableTransactionManagement
public class RootConfig {
	
	//config  properties for mysql
	private static String DRIVER_CLASS_NAME="com.mysql.jdbc.Driver";
	//private static String PASSWORD="";
	//private static String USERNAME="root";
	private static String USERNAME="tfdb_w";
	private static String PASSWORD="tfdbw88.";
	//private static String URI="jdbc:mysql://127.0.0.1:3306/oauth_server";
	private static String URI="jdbc:mysql://10.244.134.129:3306/tfdb_t";

	// config  properties for redis
	private static String REDIS_PASSWORD="";
	//private static String REDIS_HOSTNAME="10.244.171.37";
	private static String REDIS_HOSTNAME="localhost";
	
		

	
	public RootConfig() {
		// TODO Auto-generated constructor stub
	}

	/**
	 *  mysql datasource
	 * @return
	 */
	@Bean
	public DataSource dataSource(){
		BasicDataSource ds=new BasicDataSource();
			ds.setDriverClassName(DRIVER_CLASS_NAME);
			ds.setPassword(PASSWORD);
			ds.setUsername(USERNAME);
			ds.setUrl(URI);
			return ds;
	}
	
	/**
	 *  hibernate sessionFactory
	 * @return
	 */
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean lsfb=new LocalSessionFactoryBean();
			lsfb.setDataSource(dataSource());
			
			Properties ps=new Properties();
				ps.setProperty("hibernate.show_sql", "true");
				ps.setProperty("hibernate.format_sql", "true");
				ps.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			lsfb.setHibernateProperties(ps);
			lsfb.setPackagesToScan("oauthServer.model");
			//lsfb.setAnnotatedPackages("oauthServer.model");
		return lsfb;
	}
	
	/**
	 *  hibernate Transaction Manager
	 * @param sessionFactory
	 * @return
	 */
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
		HibernateTransactionManager tm=new  HibernateTransactionManager(sessionFactory);
		return tm;
	}
	
	
	/**
	 *  JediConnectionFactory
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		
		JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxIdle(10);
			config.setMinIdle(5);
		
		// change to cluster later...
		//JedisConnectionFactory cf=new JedisConnectionFactory(poolConfig)
		JedisConnectionFactory cf=new JedisConnectionFactory(config);
			//cf.setUsePool(true);
			cf.setTimeout(3000);
			//cf.setDatabase(REDIS_DATABASE_INDEX);
			//cf.setPort(REDIS_PORT);
			cf.setHostName(REDIS_HOSTNAME);
			cf.setPassword(REDIS_PASSWORD);
			
		return cf;
	}
	
	/**
	 *  RedisTemplate 
	 * @return
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate(){
		RedisTemplate< String, String> template=new StringRedisTemplate(jedisConnectionFactory());
		return template;
	}
	

}
