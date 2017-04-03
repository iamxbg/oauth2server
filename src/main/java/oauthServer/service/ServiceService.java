package oauthServer.service;

import java.util.List;

import oauthServer.model.Service;


public interface ServiceService {

	public List<Service> findAll();
	
	public Service findByService_id(String service_id);
	
	public Service findById(int id);
}
