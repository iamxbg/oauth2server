//package controllerTest;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.ws.rs.core.MediaType;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
//public class SSATestControllerTest {
//
//
//	private static Logger logger=LogManager.getLogger();
//	
//	private MockMvc mvc;
//	@Autowired
//	private WebApplicationContext context;
//	
//	//---------------------  Config  --------------------
//	String RIGHT_ACCESS_TOKEN="atk:1:1:1:7651ffc7-13b6-4f6a-9032-1887953b06da";
//	
//	
//	//------------------------------------------
//	
//	
//	String WRONG_ACCESS_TOKEN=RIGHT_ACCESS_TOKEN+"f";
//	
//	
//	
//	@Before
//	public void setup(){
//		
//		this.mvc=MockMvcBuilders.webAppContextSetup(context).build();
//	}
//	
//
//	@Test
//	public void testCheckToken_Success() throws Exception{
//		logger.info("@");
//		mvc.perform(MockMvcRequestBuilders.get("/ssa/check_token/access_token="+RIGHT_ACCESS_TOKEN))
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(content().contentType("application/json;charset=utf-8"));
//			
//			
//			
//			
//	}
//	
//
//	@Test
//	public void testCheckToken_Fail() throws Exception{
//		logger.info("@");
//		mvc.perform(MockMvcRequestBuilders.get("/ssa/check_token/access_token="+WRONG_ACCESS_TOKEN))
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(content().contentType("application/json;charset=utf-8"));
//	}
//	
//	@Ignore
//	@Test
//	public void testAuthorize_Success(){
//		
//		logger.info("@");
//		
//		
//	}
//
//	@Ignore
//	@Test
//	public void testAuthorize_Fail(){
//		
//	}
//	
//	@Ignore
//	@Test
//	public void testRefreshToken_Success(){
//		
//	}
//	
//	@Ignore
//	@Test
//	public void testRefreshToken_Fail(){
//		
//	}
//
//}
