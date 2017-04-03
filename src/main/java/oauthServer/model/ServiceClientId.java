package oauthServer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ServiceClientId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int sid;
	int cid;
	
	public ServiceClientId() {
		// TODO Auto-generated constructor stub
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public ServiceClientId(int sid, int cid) {
		super();
		this.sid = sid;
		this.cid = cid;
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
		if(!(obj instanceof ServiceClientId)) return false;
		
		ServiceClientId sci=(ServiceClientId)obj;
		
		if(sid!=sci.getSid()) return false;
		if(cid!=sci.getCid()) return false;

		return true;
	}

}
