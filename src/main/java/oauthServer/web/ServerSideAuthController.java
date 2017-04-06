package oauthServer.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static oauthServer.util.OAuthUtils.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;

import oauthServer.model.Client;
import oauthServer.service.ClientService;
import oauthServer.service.OAuth2Service;
import oauthServer.service.ServiceService;
import oauthServer.util.TicketType;
import oauthServer.util.TicketTypeChecker;


@Controller
@RequestMapping(path="/ssa")
public class ServerSideAuthController {
	
		private static Logger logger=LogManager.getLogger();
		
		@Autowired
		private ServiceService serService;
		@Autowired
		private ClientService cliService;

		@Autowired
		private OAuth2Service oauth2Service;

	
		/**
		 * get params in form, with account and password
		 * 	
		 * @param req
		 * @param mav
		 * @return
		 * @throws IOException 
		 * @throws ClientProtocolException 
		 */
		@RequestMapping(path="/authorize",method={RequestMethod.POST})
		@Consumes("application/x-www-form-urlencoded")   
		@Produces("application/x-www-form-urlencoded")
		public ModelAndView authorize(@RequestParam("ticket") String ticket
				,ModelAndView mav,HttpSession session){
		
			logger.info("@authorize");
			
			
			String trueTicket=(String) session.getAttribute("ticket");
			//check ticket
			if(trueTicket!=null && ticket.equals(ticket)){
				
				
				String client_id=(String) session.getAttribute("client_id");
				String service_id=(String) session.getAttribute("service_id");
				String user_id=(String) session.getAttribute("user_id");
				String state=(String) session.getAttribute("state");
				String redirect_uri=(String) session.getAttribute("redirect_uri");
				
				logger.info("service_id:"+service_id);
				logger.info("client_id:"+client_id);
				logger.info("user_id:"+user_id);
				logger.info("state:"+state);
				logger.info("redirect_uri:"+redirect_uri);
				
				int service_id_int=serService.findByService_id(service_id).getId();
				Client client=cliService.findByClient_id(client_id);
				int client_id_int=client.getId();

			try{
				
				logger.info("service_id_int: "+service_id_int);
				logger.info(" client_id_int:"+client_id_int);
				logger.info(" user_id:"+user_id);

				//genereate openid_authorization_code
				String openid_authorization_code_key=genereteKey_OpenId_AuthorizationCode(service_id_int, client_id_int, Integer.parseInt(user_id));
				//generate access_token_authorization_code
				String access_token_authorization_code_key=generateKey_AuthorizationCode(service_id_int, client_id_int, Integer.parseInt(user_id));
				
				//save openid_authorization_code in redis
				String openid_authorizationcode=oauth2Service.addTicket(openid_authorization_code_key,TicketType.OPEN_ID_AUTHORIZATION_CODE);
				//save access_toekn_authorization_code in redis
				String access_token_authorizationcode=oauth2Service.addTicket(access_token_authorization_code_key,TicketType.AUTHORIZATION_CODE);
				
					//send openid_authorization_code to client
					CloseableHttpClient httpClient=HttpClients.createDefault();
						
							//return authorization_code to client
							//contain openid_authorization_code
							//and contain access_token_authorization_code 
					
						logger.info("openid_authorization_code:"+openid_authorizationcode);
						logger.info("access_token_authorization_code:"+access_token_authorizationcode);
						logger.info("Receive_authcode_uri:"+client.getReceive_authcode_uri());
							HttpPost post=new HttpPost(client.getReceive_authcode_uri());
					
							Map<String, String> openIdMap=new HashMap<>();
								openIdMap.put("openid_authorization_code", openid_authorizationcode);
								openIdMap.put("access_token_authorization_code", access_token_authorizationcode);
								openIdMap.put("state", state);
						
							ObjectMapper mapper=new ObjectMapper();
							String openIdMap_str = mapper.writeValueAsString(openIdMap);
							
						
							
							HttpEntity entity=new StringEntity(openIdMap_str, Charset.forName("utf-8"));
					
							post.setEntity(entity);
							post.setHeader("Content-Type", "application/json;charset=utf-8");
							
							CloseableHttpResponse response=httpClient.execute(post);
							
							//logger.info("statusCode:"+response.getStatusLine().getStatusCode());
							
							logger.info("statusLIne:"+response.getStatusLine());
					// success send
					if(response.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK
							){
					
						logger.info("send success, redirect_uri:"+redirect_uri);

						mav.setView(new RedirectView(redirect_uri, false));
							
							mav.addObject("state",state);
							
							return mav;
			
					// send error
					}else{
							mav.setView(new JstlView("/WEB-INF/ssaAuthorizationView.jsp"));
							mav.addObject("error", "open票據發送錯誤!");
							mav.addObject("state", state);
							mav.addObject("error_description", "statusCode:"+response.getStatusLine().getStatusCode()+"reasonPhrase"+response.getStatusLine().getReasonPhrase());
						
							return mav;
					}
					
				}catch(IOException  e){
					e.printStackTrace();
				}
			
		}else{
			//ticket is not same, error!
			mav.setView(new JstlView("/WEB-INF/ssaAuthorizationView.jsp"));
			mav.addObject("error", "驗證失敗!");
			mav.addObject("error_description", "票據錯誤!");
			
			return mav;
		}
		
		return null;
		
	}
	

	
	
		/**
		 * Use authorization_code to retrieve access_token
		 *  
		 * @param client_id
		 * @param redirect_uri
		 * @param code
		 * @return
		 */
		@RequestMapping(path="/token",method=RequestMethod.POST)
		@Produces("application/json;charset=utf-8")
		public ResponseEntity<Map<String, String>> token(@RequestParam("grant_type") String grant_type,
				@RequestParam("client_id") String client_id,
				@RequestParam("service_id") String service_id,
				@RequestParam("user_id") String user_id,
				@RequestParam("redirect_uri") String redirect_uri,
				@RequestParam("code") String code,
				@RequestParam("client_secrect") String client_secrect){
			
				logger.info("token");
				
				Map<String, String> result=new HashMap<>();
				
				if("authorization_code".equals(grant_type)){
					
					int splitIndex=code.lastIndexOf(":");
					
					String keyPart=code.substring(0, splitIndex);
					String valPart=code.substring(splitIndex+1);
					
					if(TicketTypeChecker.isType(keyPart, TicketType.AUTHORIZATION_CODE)){
						
						int service_id_int=serService.findByService_id(service_id).getId();
						
						Client client=cliService.findByClient_id(client_id);
						int client_id_int=client.getId();
						
						String value=oauth2Service.getVal(keyPart);
						
						String trueSecrect=client.getClient_secrect();
						
						
						if(value!=null && value.equals(valPart) && trueSecrect.equals(client.getClient_secrect())){
							//create access_token
							
							logger.info("service_id_int:"+service_id_int);
							logger.info("client_id_int:"+client_id_int);
							logger.info("user_id:"+user_id);
							String access_token_key=generateKey_AccessToken(service_id_int, client_id_int, Integer.parseInt(user_id));
							//save in redis
							String access_token=oauth2Service.addTicket(access_token_key,TicketType.ACCESS_TOEKN);
							//expires ticket
							oauth2Service.expires(code);
							//return to client-server
							result.put("access_token", access_token);
							return new ResponseEntity<Map<String,String>>(result,HttpStatus.OK);
							
						}else{
							//not found
							result.put("error_description", "未找到，請確認是否過期!");
							return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
						}
						
					}else{
						result.put("error_description", "錯誤的access_token_authorization_code 格式");
						return new ResponseEntity<Map<String,String>>(result,HttpStatus.OK);
					}
	
				}else{
					//grant_type erorr
					result.put("error_description", "錯誤的授權模式，只接受authorization_code");
					return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
				}
							
		}
		
		
		@RequestMapping(path="/refreshToken",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
		public ResponseEntity<Map<String, String>> refreshToken(@RequestParam("grant_type") String grant_type
				,@RequestParam("client_id") String client_id
				,@RequestParam("client_secrect") String client_secrect
				,@RequestParam("refresh_token") String refresh_token){
			
			//grant_type must be refresh_token
			
			Map<String, String> respMap=new HashMap<>();
				
			if("refresh_token".equals(grant_type)){
				
				int splitIndex=refresh_token.lastIndexOf(":");
				
				String keyPart=refresh_token.substring(0, splitIndex);
				String valPart=refresh_token.substring(splitIndex+1);
				
				if(TicketTypeChecker.isType(keyPart, TicketType.REFRESH_TOEKN)){
					
					String value=oauth2Service.getVal(keyPart);
					
					Client client=cliService.findByClient_id(client_id);
					
					if(value!=null && value.equals(valPart) && client!=null && client.getClient_secrect().equals(client_secrect)){
						//check if old grant_type is exists, no action!
						String[] parts=refresh_token.split(":");

						String accessTokenKey=new StringBuilder(REDIS_KEY_ACCESS_TOKEN)
											.append(":").append(parts[1])
											.append(":").append(parts[2])
											.append(":").append(parts[3])
											.toString();
						
						Set<String> keys=oauth2Service.keys(accessTokenKey);
						if(keys!=null && keys.size()>1 ){
							// accesss_token is not expired, refuse refresh request!
							respMap.put("error_description", "AccessToken尚未過期，請求拒絕!");
							return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
						}
						
						//set new access_token
						String access_token=oauth2Service.addTicket(accessTokenKey, TicketType.ACCESS_TOEKN);
						//set new refresh_token
						 refresh_token=oauth2Service.addTicket(keyPart, TicketType.REFRESH_TOEKN);
						 
						 respMap.put("access_token", access_token);
						 respMap.put("refresh_token", refresh_token);
						 
						 return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
						
					}else{
						respMap.put("error_description", "驗證失敗，可能原因：refresh_token過期！請重新將用戶導向授權頁面!");
						return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
					}
					
				}else{
					
					respMap.put("error_description", "錯誤的grant_type,只接受refresh_token類型!");
					return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
					
				}
				
					
			}else{
				respMap.put("error_description", "錯誤的refresh_token格式!");
				return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
			}
				
				
				
			
			
		}
	


		@RequestMapping(path="/check_token/access_token={access_token}",method=RequestMethod.GET)
		public ResponseEntity<Map<String, Object>> checkToken(@PathVariable("access_token") String access_token){
			
			int splitIndex=access_token.lastIndexOf(':');
			
			String keyPart=access_token.substring(0, splitIndex);
			String valPart=access_token.substring(splitIndex+1);
			
			Map<String, Object> result=new HashMap<>();
			
			if(TicketTypeChecker.isType(keyPart, TicketType.ACCESS_TOEKN)){
				if(TicketTypeChecker.isType(keyPart, TicketType.ACCESS_TOEKN)){
					String value=oauth2Service.getVal(keyPart);
					
					
					if(value!=null && value.equals(valPart)){
						
						result.put("is_success",true);
				
					}else{
						result.put("is_success", false);
					}
				}else{
					result.put("error_description", "錯誤的access_token格式!");
				}
			}else{
				result.put("error_description", "錯誤的access_token格式!");
			}
			
			return new ResponseEntity<>(result,HttpStatus.OK);

			
		}
		
		
		@RequestMapping(path="/testReceiveAuthCode",method=RequestMethod.POST,consumes={"application/json;charset=utf-8"})
		public ModelAndView testReceiveAuthCodes(@RequestBody Map<String,String> result,ModelAndView mav){
			System.out.println("@Test received codes");
			
			logger.info("access_token_authcode:"+result.get("openid_authorization_code"));
			logger.info("openid_authcode:"+result.get("access_token_authorization_code"));
			logger.info("state:"+result.get("state"));
			
			mav.setView(new JstlView("/testReceiveAuthCodes.jsp"));
			
			mav.addObject("openid_authorization_code", result.get("openid_authorization_code"));
			mav.addObject("access_token_authorization_code", result.get("access_token_authorization_code"));
			mav.addObject("state", result.get("state"));
			return mav;
			
		}
}
