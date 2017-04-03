//package oauthServer.config;
//
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import static oauthServer.service.OAuthService.*;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import oauthServer.service.OAuthService;
//
//public class TokenHandlerInterceptor implements HandlerInterceptor{
//	
//	@Autowired
//	private OAuthService oauthService;
//	
//	private static Logger logger=LogManager.getLogger();
//
//	
//
//	public TokenHandlerInterceptor() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
//			throws Exception {
//		// TODO Auto-generated method stub
//		PrintWriter pw=resp.getWriter();
//		
//		String access_token=req.getHeader("access_token");
//
//		logger.info("access_token:"+access_token);
//		
//		if(access_token!=null){
//			
//			int index=access_token.indexOf('#');
//			String key_part=access_token.substring(0, index);
//			String token_part=access_token.substring(index+1);
//			
//			Map<String, String> accessToken=oauthService.getAccessToken(key_part);
//
//			if(accessToken!=null){
//				
//					//check if access token is expires
//
//					long expiresAt=Long.parseLong(accessToken.get(REDIS_FIELD_EXPIRES_AT));
//					Date expires_date=new Date(expiresAt);
//					if(new Date().after(expires_date)){
//						// if access_token is expired, please use refresh_token to get a new token
//						resp.getWriter().write("WARNING ! tokn expired! Please use refresh token to get new one");
//						resp.setStatus(resp.SC_BAD_REQUEST);
//						return false;
//					}else{
//						// check authCode
//						if(accessToken.get(REDIS_FIELD_TOKEN).equals(token_part)){
//							//success , go deeper
//							return true;
//						}else{
//							// not equal , FATAL!
//							resp.setStatus(resp.SC_BAD_REQUEST);
//							pw.write( "FATAL ERROR ! token content valid fail!");
//							pw.write("	May be algorithm changed, please test with re_authorize");
//							pw.write("	If still get this error , please contact resource provider for help!  —_>— ");
//							
//							return false;
//						}
//					}
//
//			}else{
//				//token not exist! may be refresh token expires too!
//				resp.getWriter().write( "WARNING ! token not found,check if is refresh_token  expired too ?!");
//				resp.setStatus(resp.SC_BAD_REQUEST);
//				return false;
//			}
//		}
//		resp.getWriter().write( "ERROR ! neek contain \"access_token\" in request headers!");
//		resp.setStatus(resp.SC_BAD_REQUEST);
//		return false;
//		
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
