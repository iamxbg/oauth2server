package oauthServer.util;
import static oauthServer.util.OAuthUtils.*;

public class TicketTypeChecker {

	public TicketTypeChecker() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static boolean isType(String key,TicketType type){
		
		boolean isType=true;
		
		String prefix=key.substring(0, key.indexOf(':'));
		
		switch(type){
		case ACCESS_TOEKN:
			if(!REDIS_KEY_ACCESS_TOKEN.equals(prefix) || key.split(":").length==3) isType=false;
			break;
		case AUTHORIZATION_CODE:
			if(!REDIS_KEY_AUTHORIZATION_CODE.equals(prefix)  || key.split(":").length==3) isType=false;
			break;
		case OPEN_ID:
			if(!REDIS_KEY_OPENID.equals(prefix) || key.split(":").length==2) isType=false;
			break;
		case OPEN_ID_AUTHORIZATION_CODE:
			if(!REDIS_KEY_AUTHORIZATION_CODE.equals(prefix) || key.split(":").length==3) isType=false;
			break;
		case REFRESH_TOEKN:
			if(!REDIS_KEY_REFRESH_TOKEN.equals(prefix) || key.split(":").length==3) isType=false;
			break;
		default:
			break;
		}
		
		
		return isType;
	}

}
