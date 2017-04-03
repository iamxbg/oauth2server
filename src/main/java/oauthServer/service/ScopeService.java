package oauthServer.service;

import java.util.List;

import oauthServer.model.Scope;

public interface ScopeService {

	public List<Scope> getScopesByClientId(int cid);
	
	public List<Scope> getScopesBydServiceId(int serviceId);
	
	public Scope findById(int id);
	
}
