//package serviceTest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import oauthServer.config.RootConfig;
//import oauthServer.config.WebConfig;
//import oauthServer.service.OAuth2Service;
//import oauthServer.util.TicketType;
//import static oauthServer.util.OAuthUtils.*;
//
//@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={RootConfig.class,WebConfig.class})
//public class OAuth2ServiceTest {
//
//	@Autowired
//	private OAuth2Service oSerivce;
//	
//	private int service_id_int=1;
//	private int client_id_int=1;
//	private int user_id_int=1;
//	
//	@Test
//	public void addAllKindTicket(){
//		
//		oSerivce.addTicket(generateKey_AccessToken(service_id_int, client_id_int, user_id_int), TicketType.ACCESS_TOEKN);
//		
//		oSerivce.addTicket(generateKey_AuthorizationCode(service_id_int, client_id_int, user_id_int), TicketType.AUTHORIZATION_CODE);
//		
//		oSerivce.addTicket(generateKey_OpenId(service_id_int, user_id_int), TicketType.OPEN_ID);
//		
//		oSerivce.addTicket(generateKey_RefreshToken(service_id_int, client_id_int, user_id_int), TicketType.REFRESH_TOEKN);
//		
//		oSerivce.addTicket(genereteKey_OpenId_AuthorizationCode(service_id_int, client_id_int, user_id_int), TicketType.OPEN_ID_AUTHORIZATION_CODE);
//		
//
//	}
//	
//	@Test
//	public void addAuthorization_Codes(){
//		
//		oSerivce.addTicket(generateKey_AuthorizationCode(service_id_int, client_id_int, user_id_int), TicketType.AUTHORIZATION_CODE);
//		
//		oSerivce.addTicket(genereteKey_OpenId_AuthorizationCode(service_id_int, client_id_int, user_id_int), TicketType.OPEN_ID_AUTHORIZATION_CODE);
//		
//		
//	}
//
//}
