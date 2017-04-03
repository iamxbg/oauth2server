package oauthServer.service;
import java.util.Map;
import java.util.Set;

import oauthServer.util.TicketType;

public interface OAuth2Service {

	public String getVal(String key); 
		
	public String addTicket(String key,TicketType type);
	
	public Set<String> keys(String pattern);
	
	public void expires(String key);
}
