package oauthServer.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.RedirectView;
import com.fasterxml.jackson.databind.ObjectMapper;
import oauthServer.model.Client;
import oauthServer.model.Scope;
import oauthServer.model.Service;
import oauthServer.service.ClientService;
import oauthServer.service.OAuth2Service;
import oauthServer.service.ScopeService;
import oauthServer.service.ServiceService;
import oauthServer.util.TicketType;
import oauthServer.util.TicketTypeChecker;

import static oauthServer.util.OAuthUtils.*;


@Controller
@RequestMapping(path="/openid")
public class OpenIDController {
	
	private static Logger logger=LogManager.getLogger();
	
	@Autowired
	private ServiceService serService;
	@Autowired
	private ClientService cliService;
	@Autowired
	private OAuth2Service oauth2Service;
	@Autowired
	private ScopeService scpService;


	/**
	 * 
	 * Login with openId
	 * 
	 * @param openid
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */

	@RequestMapping(path="/login/openid={openid}",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	public ResponseEntity<Map<String, String>> loginWithOpenId(@PathVariable("openid") String openid
								) throws ClientProtocolException, IOException{
		
		logger.info("@openid:"+openid);
		
		

		int splitIndex=openid.lastIndexOf(":");
		String valPart=openid.substring(splitIndex+1);
		String keyPart=openid.substring(0, splitIndex);
		
		if(TicketTypeChecker.isType(keyPart, TicketType.OPEN_ID)){
			String value=oauth2Service.getVal(keyPart);
			
			
			
			if(value!=null && value.equals(valPart)){
				//get user's information through httpClient Request
				//Service service=serService.findByService_id(service_id);
				
				int service_id_int=Integer.parseInt(openid.split(":")[1]);
				int user_id_int=Integer.parseInt(openid.split(":")[2]);
				
				logger.info("uservice_id_int:"+service_id_int);
				logger.info("user_id_int:"+user_id_int);
				
				Service service=serService.findById(service_id_int);
				
				HttpGet getUserInfo=new HttpGet(service.getUserinfo_uri()+user_id_int);
				
				logger.info("#get auth:"+service.getUserinfo_uri()+user_id_int);
					//getUserInfo.setEntity(new StringEntity("user_id="+user_id_int, Charset.forName("utf-8")));
				
				CloseableHttpClient httpClient=HttpClients.createDefault();
					CloseableHttpResponse response=httpClient.execute(getUserInfo);

					//if get successful , return user's infomation to client-app
					if(response.getStatusLine().getStatusCode()==HttpServletResponse.SC_OK){

						ObjectMapper mapper=new ObjectMapper();
						
						HttpEntity entity=response.getEntity();

						String respVal=EntityUtils.toString(entity);
						
						logger.info("respVal:"+respVal);
						
						//Map<String, Object> result=mapper.convertValue(respVal, HashMap.class);
						
						Map<String, String> map=new HashMap<>();
							map.put("user_info", respVal);
		
						return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
					}else{
						Map<String, String> errMap=new HashMap<>();
							errMap.put("error_description", response.getStatusLine().getReasonPhrase());
						return new ResponseEntity<Map<String,String>>(errMap, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				//else return error_description
			}else{
				//openid authorization failed, maybe openid expired or gone, please 
				//redirect user to authentication flow!
				Map<String, String> errResult=new HashMap<>();
					errResult.put("error_description", "未找到openid！可能已過期，或升級刪除!請重新將用戶導向登陸授權頁面!");
				return new ResponseEntity<>(errResult,HttpStatus.OK);
				
			}
		}else{
			
			Map<String, String> errResult=new HashMap<>();
				errResult.put("error_description", "錯誤的openid格式!");
				return new ResponseEntity<Map<String,String>>(errResult, HttpStatus.OK);
			
		}
		


	}
	
	@RequestMapping(path="/retrive/code={code}",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ResponseBody
	public ResponseEntity<Map<String, String>> openid(@PathVariable("code") String code
			,@RequestParam("service_id") String service_id
			,@RequestParam("client_id") String client_id
			,@RequestParam("user_id") String user_id
			,@RequestParam("client_secrect") String client_secrect
			,HttpServletResponse resp){
		
		logger.info("@Retrive OpenId");
		
		Map<String, String> respMap=new HashMap<>();
		logger.info("code:"+code);
		
		int splitIndex=code.lastIndexOf(":");
		String keyPart=code.substring(0, splitIndex);
		String valPart=code.substring(splitIndex+1);
		
		if(TicketTypeChecker.isType(keyPart, TicketType.OPEN_ID_AUTHORIZATION_CODE)){
			String value=oauth2Service.getVal(keyPart);
			logger.info("value:"+value);
			
			Service service=serService.findByService_id(service_id);
			Client client=cliService.findByClient_id(client_id);
			
			if(value!=null && value.equals(valPart) && client!=null && client.getClient_secrect().equals(client_secrect)){
				
				//check if openId is exist!
				
				
				
				int service_id_int=service.getId();
				int client_id_int=client.getId();
			
				String patten=new StringBuilder(REDIS_KEY_OPENID).append(":")
							.append(service_id_int)
							.append(":").append(client_id_int).append(":*").toString();
			
				String openid=null;
				
				logger.info("PATTERN:"+patten);
				Set<String> keys=oauth2Service.keys(patten);
				logger.info("KEY-SIZE:"+keys.size());
				if(keys!=null && keys.size()>0){
					//return old openid
					 logger.info("FOUND:"+keys.size());
					 openid=keys.iterator().next();
					 logger.info("OPENID::"+openid);
				}else{
					//create new openid
					  String openidKey=generateKey_OpenId(service_id_int, Integer.parseInt(user_id));
					 //save in redis
					   openid=oauth2Service.addTicket(openidKey,TicketType.OPEN_ID);
					  //expire autho-code
					  oauth2Service.expires(keyPart);
				}
				//send to client-server
					respMap.put("openid", openid);
				
					return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);			
			}else{
				respMap.put("error_description", "未找到 authorization code,請檢查格式或著已經過期?");
				return new ResponseEntity<>(respMap,HttpStatus.NOT_FOUND);
			}
		}else{
			
			respMap.put("error_description", "錯誤的openid驗證碼格式,請檢查! ");
			return new ResponseEntity<Map<String,String>>(respMap, HttpStatus.OK);
		}
		
		
			
				
	}
	
	
	/**
	 * 
	 * Authentication View
	 * 
	 * @param client_id
	 * @param service_id
	 * @param state
	 * @param redirect_uri
	 * @param mav
	 * @return
	 */
	@RequestMapping(path="/authenticationView" ,method=RequestMethod.GET,produces={MediaType.APPLICATION_FORM_URLENCODED})
	@Produces(MediaType.APPLICATION_FORM_URLENCODED)
	public ModelAndView authenticationView(@RequestParam("client_id") String client_id
			,@RequestParam("service_id") String service_id
			,@RequestParam("state") String state
			,@RequestParam("redirect_uri") String redirect_uri
			,@RequestParam("user_id") String user_id
			,ModelAndView mav,HttpServletResponse resp){
		
			logger.info("@AuthenticationView@");
			
			JstlView view=new JstlView("/WEB-INF/ssaAuthenticationView.jsp");
			mav.setView(view);
			
			mav.addObject("client_id", client_id);
			mav.addObject("service_id", service_id);
			mav.addObject("state", state);
			mav.addObject("redirect_uri", redirect_uri);
			mav.addObject("user_id", user_id);
			
			resp.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				
			return mav;
	}
	
	
	

	
	/**
	 * 
	 * Authenticate
	 * 
	 * @param account
	 * @param password
	 * @param service_id
	 * @param client_id
	 * @param state
	 * @param redirect_uri
	 * @param mav
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(path="/authenticate",method=RequestMethod.POST,
	consumes={"application/x-www-form-urlencoded;charset=utf-8"},	
	produces={"application/x-www-form-urlencoded;chatset=utf-8"})
	public ModelAndView anthenticate(@RequestParam("account") String account,
								@RequestParam("password") String password,
								@RequestParam("service_id") String service_id,
								@RequestParam("client_id") String client_id,
								@RequestParam("state") String state,
								@RequestParam("redirect_uri") String redirect_uri,
								@RequestParam("user_id") String user_id,
								ModelAndView mav,HttpSession session) throws IOException {
		
			logger.info("@Authenticate");
			
			boolean authenticatePass=false;
			
			Map<String, Object> resultMap=null;
			
			Service service=serService.findByService_id(service_id);
			
			
			CloseableHttpClient httpClient=null;
			try {

				httpClient=HttpClients.createDefault();
			
				String authURI=service.getAuthentication_uri();
					
				HttpPost post=new HttpPost(new URI(authURI));
					post.setHeader("Content-Type", "application/json;charset=utf-8");
				
				Map<String, String> map=new HashMap<>();
					map.put("account", account);
					map.put("password", password);
					map.put("user_id", user_id);
				
				ObjectMapper mapper=new ObjectMapper();
					
				String value=mapper.writeValueAsString(map);
				
				HttpEntity entity=new StringEntity(value, Charset.forName("utf-8"));
					post.setEntity(entity);
		
					ResponseHandler<String> handler=new BasicResponseHandler();
		
					String respString=httpClient.execute(post, handler);
					
						resultMap=mapper.readValue(respString, HashMap.class);
					
					logger.info("result:"+resultMap.get("result"));
					logger.info("reason:"+resultMap.get("reason"));
					
					if("success".equals(resultMap.get("result"))) authenticatePass=true;
					else if("password_error".equals(resultMap.get("result"))) mav.addObject("password_error", resultMap.get("reason"));
					else if("account_error".equals(resultMap.get("result"))) mav.addObject("account_error",resultMap.get("reason"));
					else if("fail".equals(resultMap.get("result"))) mav.addObject("fail", resultMap.get("reason"));

			}  catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				e.printStackTrace();
				logger.error("IOEXCEPTION:++++++++++++++++++++++");
				logger.error(e.getMessage());
				logger.error(e);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("IOEXCEPTION:++++++++++++++++++++++");
				logger.error(e.getMessage());
				logger.error(e);
			}finally{
				if(httpClient!=null) 
						httpClient.close();
			}
			
			if(authenticatePass){
				
				//get user's profile infos, tmp save in session
				session.setAttribute("user_info", resultMap.get("user_info"));
				
				//prepare view infos
				
				Client client=cliService.findByClient_id(client_id);
				
				if(service!=null && client!=null){
					List<Scope> scpList=scpService.getScopesByClientId(client.getId());

					client.setClient_secrect(null);
					
					mav.addObject("scpList", scpList);
					mav.addObject("client", client);
					mav.addObject("service_name", service.getName());
					
					String ticket=UuidUtil.getTimeBasedUuid().toString();
					session.setAttribute("ticket", ticket);
					
					mav.addObject("ticket", ticket);
					session.setAttribute("client_id", client_id);
					session.setAttribute("service_id", service_id);
					session.setAttribute("user_id", user_id);
					session.setAttribute("state", state);
					session.setAttribute("redirect_uri", redirect_uri);
					mav.setView(new JstlView("/WEB-INF/ssaAuthorizationView.jsp"));		
				}else{
					mav.setView(new RedirectView("/err_01.jsp",true));
				}
				//redirect to authorizationView
			}else{
				mav.setView(new JstlView("/WEB-INF/ssaAuthenticationView.jsp"));
				mav.addObject("client_id", client_id);
				mav.addObject("service_id",service_id);
				mav.addObject("redirect_uri", redirect_uri);
				mav.addObject("state", state);
				
			}
			
			return mav;
	}
	
	

}
