package oauthServer.entity;

import java.util.Set;

public class Token {

	private String client_id;
	private String user_id;
	private Set<String> scopes;
	private String expires_in;
	private String token;
	
	public Token() {
		// TODO Auto-generated constructor stub
	}

	public Token(String client_id, String user_id, Set<String> scopes, String expires_in, String token) {
		super();
		this.client_id = client_id;
		this.user_id = user_id;
		this.scopes = scopes;
		this.expires_in = expires_in;
		this.token = token;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
