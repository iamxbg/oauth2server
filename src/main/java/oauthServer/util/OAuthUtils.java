package oauthServer.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OAuthUtils {
	
	private static Logger logger=LogManager.getLogger();
	
	//key labels
	public  static String REDIS_KEY_ACCESS_TOKEN="atk";
	public  static String REDIS_KEY_AUTHORIZATION_CODE="auc";
	public  static String REDIS_KEY_OPENID="oid"; 
	public  static String REDIS_KEY_OPENID_AUTHORIZATION_TOKEN="oit";
	public  static String REDIS_KEY_REFRESH_TOKEN="rfk";
	
	
	
	public  static long EXPIRES_ACCESS_TOKEN=604000l; //7 day
	public  static long EXPIRES_AUTHORIZATION_CODE=180l; //3 min
	public  static long EXPIRES_OPENID=62208000l; // 2 year
	public  static long EXPIRES_OPENID_AUTHORIZATION_TOKEN=180l; //3 min
	public  static long EXPIRES_REFRESH_TOKEN=2592000l; //30 day
	
	
	/*
	public  static long EXPIRES_ACCESS_TOKEN=1800l; 				//30 min
	public  static long EXPIRES_AUTHORIZATION_CODE=600l; 			//10 min
	public  static long EXPIRES_OPENID=7200l; 					//30 min
	public  static long EXPIRES_OPENID_AUTHORIZATION_TOKEN=1800l; 	//10 min
	public  static long EXPIRES_REFRESH_TOKEN=3600l; 			//1 hour
	*/

	
	public static String generateKey_AccessToken(int service_id_int,int client_id_int,int user_id_int){
		return generateKey(service_id_int, client_id_int, user_id_int, TicketType.ACCESS_TOEKN);
	}
	
	public static String generateKey_AuthorizationCode(int service_id_int,int client_id_int,int user_id_int){
		return generateKey(service_id_int,client_id_int,user_id_int,TicketType.AUTHORIZATION_CODE);
	}
	
	public static String generateKey_OpenId(int service_id_int,int user_id_int){
		return generateKey(service_id_int,0,user_id_int,TicketType.OPEN_ID);
	}
	
	public static String genereteKey_OpenId_AuthorizationCode(int service_id_int,int client_id_int,int user_id_int){
		return generateKey(service_id_int,client_id_int,user_id_int,TicketType.OPEN_ID_AUTHORIZATION_CODE);
	}
	
	public static String generateKey_RefreshToken(int service_id_int,int client_id_int,int user_id_int){
		return generateKey(service_id_int,client_id_int,user_id_int,TicketType.REFRESH_TOEKN);
	}
	
	
	 static String  generateKey(int service_id_int,int client_id_int,int user_id_int,TicketType type ){
		
		String prefix=null;
		
		switch (type) {
		
			case ACCESS_TOEKN:
				prefix=REDIS_KEY_ACCESS_TOKEN;
				break;
			case REFRESH_TOEKN:
				prefix=REDIS_KEY_REFRESH_TOKEN;
				break;
			case AUTHORIZATION_CODE:
				prefix=REDIS_KEY_AUTHORIZATION_CODE;
				break;
			case OPEN_ID:
				prefix=REDIS_KEY_OPENID;
				break;
			case OPEN_ID_AUTHORIZATION_CODE:
				prefix=REDIS_KEY_OPENID_AUTHORIZATION_TOKEN;
			default:
				break;
		}
		if(type!=TicketType.OPEN_ID)
			return new StringBuilder(prefix).append(":").append(service_id_int)
					.append(":").append(client_id_int)
					.append(":").append(user_id_int)
					//.append(":").append(generateEncryptHashValue())
					.toString();
		else
			return new StringBuilder(prefix).append(":").append(service_id_int)
						.append(":").append(user_id_int)
						//.append(":").append(generateEncryptHashValue())
						.toString();
	}
	
	/**
	 *  Need improvement
	 * @return
	 */
	public  static String  generateEncryptHashValue(){
		return UUID.randomUUID().toString();
	}
	
}
