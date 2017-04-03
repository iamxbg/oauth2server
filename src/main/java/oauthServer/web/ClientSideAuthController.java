package oauthServer.web;
//package oauthServer.web.oauth2;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.view.RedirectView;
//import oauthServer.model.Client;
//import oauthServer.service.ClientService;
//import oauthServer.service.ServiceService;
//import oauthServer.util.OAuthConstants;
//import oauthServer.util.OAuthUtils;
//import oauthServer.util.ServiceType;
//
//import static oauthServer.util.OAuthConstants.*;
//
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//
//@Controller
//@RequestMapping("/csa")
//public class ClientSideAuthController {
//	
//	private Logger logger=LogManager.getLogger(ClientSideAuthController.class);
//
//	@Autowired
//	private ServiceService serService;
//	@Autowired
//	private ClientService cliService;
//
//	
//	public ClientSideAuthController() {
//		// TODO Auto-generated constructor stub
//	
//	}
//	
//	//toAuthView
//	/**
//	 *  request parameters:
//	 *  	response_type: token
//	 *  	client_id(required)
//	 *  	redirect_uri(optional)
//	 *  	scope(optional)
//	 *  	state(recommanded)
//	 *  	
//	 *  	e.g.:
//	 *  		GET /authorize?
//				response_type=token&
//				client_id=s6BhdRkqt3&
//				state=xyz&
//				redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
//				Host: server.example.com
//	 * @param req
//	 * @param mav
//	 * @return
//	 */
//	@GET
//	@RequestMapping(path="/authView")
//	public ModelAndView toAuthView(HttpServletRequest req,ModelAndView mav){
//		
//		try {
//			OAuthAuthzRequest authzReq=new OAuthAuthzRequest(req);
//				
//			
//				mav.addObject(CLIENT_ID, authzReq.getClientId());
//				mav.addObject(REDIRECT_URI, authzReq.getRedirectURI());
//				mav.addObject(STATE, authzReq.getState());
//				mav.addObject(RESPONSE_TYPE, CODE);
//				mav.setView(new RedirectView(AUTHORIZATION_PAGE));
//				return mav;
//				
//		} catch (OAuthSystemException|OAuthProblemException e) {
//			// TODO Auto-generated catch block
//			mav.addObject(ERROR_DESCRIPTION, e.getMessage());
//			mav.setView(new RedirectView(OAuthConstants.AUTHORIZATION_PAGE));
//			return mav;
//		} 
//		
//	}
//	
//	
//	//do auth work,return token
//	/**
//	 *  request parameters:
//	 *  	response_type: token
//	 *  	client_id(required)
//	 *  	redirect_uri(optional)
//	 *  	scope(optional)
//	 *  	state(recommanded)
//	 *  	
//	 *  	e.g.:
//	 *  		GET /authorize?
//				response_type=token&
//				client_id=s6BhdRkqt3&
//				state=xyz&
//				redirect_uri=https%3A%2F%Eexample%2Ecom%2Fcallback HTTP/1.1
//				Host: server.example.com
//				
//		response:
//				success:
//					HTTP/1.1 302 Found
//						Location: http://example.com/callback#
//						access_token=2YotnFZFEjr1zCsicMWpAA&
//						state=xyz&
//						token_type=bearer&
//						expires_in=3600
//				fail:
//					error
//					error_description(optional)
//					error_uri(optional)
//	*/
//	
//
//	@Consumes("application/x-www-form-urlencoded")
//	@RequestMapping(path="/authorize")
//	public ModelAndView authorize(HttpServletRequest req,ModelAndView mav){
//		
//		
//		try {
//			OAuthAuthzRequest request = new OAuthAuthzRequest(req);
//
//			String redirect_uri=request.getRedirectURI();
//
//			String state=request.getState();
//			
//			String service_id=request.getParam(SERVICE_ID);
//			String client_id=request.getParam(CLIENT_ID);
//
//			//-------------------------------------
//			
//			oauthServer.model.Service service=serService.findByService_id(service_id);
//			Client client=cliService.findByClient_id(client_id);
//			if(service==null) throw new OAuthAuthzException(ERROR_SERVICE_NOT_EXISTS);
//			if(client!=null) throw new OAuthAuthzException(ERROR_CLIENT_NOT_EXISTS);
//			 
//			int service_id_int=service.getId();
//			int client_id_int=client.getId();
//			
//			String user_id=request.getParam(USER_ID);
//			
//			
//			int user_id_int=Integer.parseInt(user_id);
//
//			
//			if(service_id==ServiceType.TF02){
//
//				
//				String tel=request.getParam(TEL);
//				String password=request.getParam(PASSWORD);
//
//				FamilyAccount fa=accService.getFamilyAccountByTelAndPassword(tel, password);
//				
//				List<FamilyMembers> miList=accService.findMembersInfoByFamilyId(fa.getId());
//				
//				boolean authSuccess=false;
//				
//				for(FamilyMembers fm:miList){
//					if(Integer.parseInt(fm.getMemberId())==user_id_int){
//						authSuccess=true;
//						break;
//					}
//				}
//			
//				if(authSuccess){
//
//					String access_token= oService.addAccessToken_token(service_id_int, client_id_int, user_id_int);
//					
//					mav.addObject(ACCESS_TOKEN, access_token);
//					mav.addObject(TOKEN_TYPE, TokenType.BEARER.name());
//					mav.addObject(EXPIRES_IN, ACCESS_TOKEN_TOKEN_EXPIRES_IN);
//					mav.addObject(STATE, state);
//					mav.setView(new RedirectView(redirect_uri, false));
//					
//					return mav;
//					
//				}else throw new OAuthAuthzException(ERROR_AUTHORIZE_FAIL);
//				
//			}
//
//			
//			
//		} catch (OAuthSystemException | OAuthProblemException |NoSuchAlgorithmException | OAuthAuthzException e) {
//
//			mav.addObject(ERROR_DESCRIPTION, e.getMessage());
//			mav.setView(new RedirectView(AUTHORIZATION_PAGE, false));
//			return mav;
//		}
//	
//			return null;
//			
//			
//		
//	}
//	
//
//}
