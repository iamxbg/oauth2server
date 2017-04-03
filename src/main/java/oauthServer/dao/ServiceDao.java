package oauthServer.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import oauthServer.model.Service;

public interface ServiceDao {
	
	
	public List<Service> findAll();
	
	
	public Service findByService_id(String service_id);
	
	public Service findById(int id);
	
}
