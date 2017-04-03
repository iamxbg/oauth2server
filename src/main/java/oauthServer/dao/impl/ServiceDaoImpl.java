package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ServiceDao;
import oauthServer.model.Service;

@Repository
public class ServiceDaoImpl implements ServiceDao{

	@Autowired
	private SessionFactory sf;
	
	@Override
	public List<Service> findAll() {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("from Service s").list();
	}

	@Override
	public Service findByService_id(String service_id) {
		// TODO Auto-generated method stub
		return (Service) sf.getCurrentSession().createQuery("from Service s where s.service_id=:service_id")
				.setString("service_id", service_id).uniqueResult();
	}

	@Override
	public Service findById(int id) {
		// TODO Auto-generated method stub
		return (Service) sf.getCurrentSession().createQuery("from Service s where s.id=:id")
					.setInteger("id",id).uniqueResult();
	}

}
