package oauthServer.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.quartz.impl.matchers.StringMatcher.StringOperatorName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import static oauthServer.util.OAuthUtils.*;
import oauthServer.service.OAuth2Service;
import oauthServer.util.OAuthUtils;
import oauthServer.util.TicketType;

@Service
public class OAuth2ServiceImpl implements OAuth2Service{

	@Autowired
	private RedisOperations<String, String> ops;

	
	@Override
	public String getVal(String key) {
		// TODO Auto-generated method stub
		ValueOperations<String, String> valOps=ops.opsForValue();
			return valOps.get(key);

	}



	@Override
	public String addTicket(String key,TicketType type) {
		// TODO Auto-generated method stub
		ValueOperations<String, String> valOps=ops.opsForValue();
			String value=OAuthUtils.generateEncryptHashValue();
			valOps.set(key, value);
			if(type==TicketType.ACCESS_TOEKN)
				ops.expire(key, EXPIRES_ACCESS_TOKEN,TimeUnit.SECONDS);
			else if(type==TicketType.AUTHORIZATION_CODE)
				ops.expire(key, EXPIRES_AUTHORIZATION_CODE,TimeUnit.SECONDS);
			else if(type==TicketType.OPEN_ID)
				ops.expire(key, EXPIRES_OPENID, TimeUnit.SECONDS);
			else if(type==TicketType.OPEN_ID_AUTHORIZATION_CODE)
				ops.expire(key, EXPIRES_OPENID_AUTHORIZATION_TOKEN, TimeUnit.SECONDS);
			else if(type==TicketType.REFRESH_TOEKN)
				ops.expire(key, EXPIRES_REFRESH_TOKEN, TimeUnit.SECONDS);
			
			return new StringBuilder(key).append(":").append(value).toString();
	}



	@Override
	public Set<String> keys(String pattern) {
		// TODO Auto-generated method stub
		Set<String> keys=ops.keys(pattern);
		return keys;
		
	}



	@Override
	public void expires(String key) {
		// TODO Auto-generated method stub
		ops.expire(key, 1l, TimeUnit.MILLISECONDS);
		
	}


	


}
