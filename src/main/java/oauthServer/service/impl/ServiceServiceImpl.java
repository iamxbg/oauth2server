package oauthServer.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ServiceDao;
import oauthServer.model.Service;
import oauthServer.service.ServiceService;

@Transactional
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{

	@Autowired
	private ServiceDao sDao;
	
	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return sDao.findAll();
	}

	@Override
	public Service findByService_id(String service_id) {
		// TODO Auto-generated method stub
		return sDao.findByService_id(service_id);
	}

	@Override
	public Service findById(int id) {
		// TODO Auto-generated method stub
		return sDao.findById(id);
	}
	


}
