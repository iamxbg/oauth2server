package oauthServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ClientDao;
import oauthServer.model.Client;
import oauthServer.service.ClientService;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDao;
	
	public ClientServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Client findById(int id) {
		// TODO Auto-generated method stub
		return clientDao.findById(id);
	}

	@Override
	public Client findByClient_id(String client_id) {
		// TODO Auto-generated method stub
		return clientDao.findByClient_id(client_id);
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return clientDao.findAll();
	}

}
