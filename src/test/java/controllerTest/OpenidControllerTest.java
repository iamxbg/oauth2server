//package controllerTest;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.servlet.ModelAndView;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
//public class OpenidControllerTest {
//
//	@Autowired
//	private WebApplicationContext context;
//
//	private MockMvc mvc;
//	
//	@Before
//	public void setup(){
//		this.mvc=MockMvcBuilders.webAppContextSetup(context).build();
//	}
//	
//	
//	private String RIGHT_CODE="";
//	private String ERROR_CODE=""+"X";
//	
//	
//	@Test
//	public void testAuthenticationView_Success() throws Exception{
//		
//		String client_id="fake_client_id";
//		String service_id="fake_service_id";
//		String state="fake_state";
//		String redirect_uri="fake_redirect_uri";
//		String user_id="fake_user_id";
//
//		mvc.perform(
//				MockMvcRequestBuilders.post("/openid/authenticationView")
//				.param("client_id", client_id)
//				.param("service_id",service_id)
//				.param("state",state)
//				.param("redirect_uri",redirect_uri)
//				.param("user_id",user_id)
//				).andExpect(status().isOk())
//				//.andExpect(header().string("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//				//.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//				.andExpect(forwardedUrl("/ssaAuthenticationView.jsp"))
//				.andDo(print());
//
//	}
//
//	@Test
//	public void testAuthenticationView_Fail() throws Exception{
//		
//		String client_id="fake_client_id";
//		String service_id="fake_service_id";
//		String state="fake_state";
//		String redirect_uri="fake_redirect_uri";
//		String user_id="fake_user_id";
//	
//		mvc.perform(
//				MockMvcRequestBuilders.post("/openid/authenticationView")
//				.param("client_id", client_id)
//				.param("service_id",service_id)
//				//.param("state",state)
//				//.param("redirect_uri",redirect_uri)
//				.param("user_id",user_id)
//				).andExpect(status().isBadRequest())
//				//.andExpect(header().string("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//				//.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//				.andExpect(forwardedUrl("/ssaAuthenticationView.jsp"))
//				.andDo(print());
//	
//	}
//	
//	@Test
//	public void testRetriveOpenId_Success() throws Exception{
//		
//		String code="oit:1:1:1:8116d6f1-44b4-4b1d-9aec-8467bb221f5d";
//		
//		String user_id="1";
//		String client_id="chunyuyishen";
//		String service_id="tf02";
//		
//		String uri=new StringBuilder("/openid/retrive/code=").append(code).toString();
//		
//		mvc.perform(
//		MockMvcRequestBuilders.post(uri).
//						param("client_id", client_id)
//						.param("service_id",service_id)
//						.param("user_id",user_id)
//					).andExpect(status().isOk())
//					//.andExpect(content().encoding("utf-8"))
//					//.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//					.andDo(print());
//		
//	}
//	
//	
//	@Test
//	public void testRetriveOpenId_Fail() throws Exception{
//		
//		String code="oit:1:1:1:8116d6f1-44b4-4b1d-9aec-8467bb221f5d";
//		
//		String user_id="1";
//		String client_id="chunyuyishen";
//		String service_id="tf02";
//		
//		String uri=new StringBuilder("/openid/retrive/code=").append(code).toString();
//		
//		mvc.perform(
//		MockMvcRequestBuilders.post(uri).
//						param("client_id", client_id)
//						.param("service_id",service_id)
//						.param("user_id",user_id)
//					).andExpect(status().isNotFound())
//					//.andExpect(content().encoding("utf-8"))
//					//.andExpect(content().contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//					.andDo(print());
//		
//	}
//	
//
//	@Test
//	public void testLoginWithOpenId_Success() throws Exception{
//		
//		//add service not open alert!
//		
//		String openid="oid:1:1:794bc690-0420-42b4-baaf-914334f4b70d";
//		
//		mvc.perform(MockMvcRequestBuilders.get(new StringBuilder("/openid/login/openid=").append(openid)
//				.toString())
//				).andDo(print()).andExpect(status().isOk());
//	}
//	
//	
//	@Test
//	public void testLoginWithOpenId_Fail() throws Exception{
//		
//		//add service not open alert!
//		
//		String openid="oid:1:1:794bc690-0420-42b4-baaf-914334f4b70d";
//		
//		mvc.perform(MockMvcRequestBuilders.get(new StringBuilder("/openid/login/openid=").append(openid)
//				.toString())
//				).andDo(print()).andExpect(status().isOk());
//	}
//	
//	
//	
//	@Test
//	public void testAuthenticate_Fail() throws Exception{
//		
//		String account="";
//		String password="";
//		String service_id="";
//		String client_id="";
//		String state="";
//		String redirect_uri="";
//		String user_id="";
//		
//		mvc.perform(MockMvcRequestBuilders.post("/openid/authenticate")
//					.param("account", account)
//					.param("password", password)
//					.param("service_id", service_id)
//					.param("client_id", client_id)
//					.param("state", state)
//					.param("redirect_uri", redirect_uri)
//					.param("user_id", user_id)
//				).andDo(print())
//					.andExpect(forwardedUrl("/ssaAuthenticationView.jsp"))
//					.andExpect(status().isOk());
//
//	}
//	
//	@Test
//	public void testAuthenticate_Success() throws Exception{
//		String account="18702847091";
//		String password="dude";
//		String service_id="tf02";
//		String client_id="chunyuyishen";
//		String state="12345";
//		String redirect_uri="http://www.baidu.com";
//		String user_id="361";
//		
//		mvc.perform(MockMvcRequestBuilders.post("/openid/authenticate")
//					.param("account", account)
//					.param("password", password)
//					.param("service_id", service_id)
//					.param("client_id", client_id)
//					.param("state", state)
//					.param("redirect_uri", redirect_uri)
//					.param("user_id", user_id)
//				).andDo(print())
//					.andExpect(forwardedUrl("/ssaAuthorizationView.jsp"))
//					.andExpect(status().isOk());
//	}
//}
