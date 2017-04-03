package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.ClientDao;
import oauthServer.model.Client;

@Repository
public class ClientDaoImpl implements ClientDao{

	@Autowired
	private SessionFactory sf;
	
	public ClientDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Client findById(int id) {
		// TODO Auto-generated method stub
		return (Client) sf.getCurrentSession().createQuery("from Client c where c.id=:id")
				.setInteger("id", id)
				.uniqueResult();
	}

	@Override
	public Client findByClient_id(String client_id) {
		// TODO Auto-generated method stub
		return (Client) sf.getCurrentSession().createQuery("from Client c where c.client_id=:client_id")
				.setString("client_id", client_id)
				.uniqueResult();
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("from Client cli").list();

	}


}
