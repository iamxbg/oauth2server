package oauthServer.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import oauthServer.dao.ScopeDao;
import oauthServer.model.Scope;

@Repository
public class ScopeDaoImpl implements ScopeDao{

	@Autowired
	private SessionFactory sf;
	
	@Override
	public List<Scope> findByServiceId(int serviceId) {
		// TODO Auto-generated method stub
		return
		sf.getCurrentSession().createQuery("from Scope scp where scp.sid=:sid")
		.setInteger("sid", serviceId).list();

	}

	@Override
	public List<Scope> findByClientId(int clientId) {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().createQuery("select scp from Scope scp left join ClientScope cs on scp.id=cs.csId.sid where cs.csId.cid=:cid")
				.setInteger("cid", clientId).list();
	}

	@Override
	public Scope findById(int id) {
		// TODO Auto-generated method stub
		return (Scope) sf.getCurrentSession().createQuery("from Scope scp where scp.id=:id")
				.setInteger("id", id).uniqueResult();
	}

}
