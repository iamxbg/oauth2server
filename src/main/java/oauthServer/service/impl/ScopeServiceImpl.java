package oauthServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oauthServer.dao.ScopeDao;
import oauthServer.model.Scope;
import oauthServer.service.ScopeService;

@Service
@Transactional
public class ScopeServiceImpl implements ScopeService{
	
	@Autowired
	private ScopeDao scpDao;

	@Override
	public List<Scope> getScopesByClientId(int cid) {
		// TODO Auto-generated method stub
		return scpDao.findByClientId(cid);
	}

	@Override
	public List<Scope> getScopesBydServiceId(int serviceId) {
		// TODO Auto-generated method stub
		return scpDao.findByServiceId(serviceId);
	}

	@Override
	public Scope findById(int id) {
		// TODO Auto-generated method stub
		return scpDao.findById(id);
	}

}
