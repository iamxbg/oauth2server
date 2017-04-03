package oauthServer.dao;

import java.util.List;

import oauthServer.model.Scope;

public interface ScopeDao {

	public List<Scope> findByServiceId(int serviceId);
	
	public List<Scope> findByClientId(int clientId);
	
	public Scope findById(int id);
	
}
