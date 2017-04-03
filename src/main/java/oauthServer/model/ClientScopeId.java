package oauthServer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ClientScopeId implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int cid;
	int sid;
	
	
	public ClientScopeId() {
		// TODO Auto-generated constructor stub
	}


	public ClientScopeId(int cid, int sid) {
		super();
		this.cid = cid;
		this.sid = sid;
	}


	public int getCid() {
		return cid;
	}


	public void setCid(int cid) {
		this.cid = cid;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result=sid;
		return result*31+cid;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj) return true;
		if(!(obj instanceof ClientScopeId)) return false;
		
		ClientScopeId sci=(ClientScopeId)obj;
		
		if(sid!=sci.getSid()) return false;
		if(cid!=sci.getCid()) return false;

		return true;
	}



}
