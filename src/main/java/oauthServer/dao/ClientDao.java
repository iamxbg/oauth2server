package oauthServer.dao;

import java.util.List;

import oauthServer.model.Client;

public interface ClientDao {
	
	public Client findById(int id);
	
	public Client findByClient_id(String client_id);

	public List<Client> findAll();
	
}
