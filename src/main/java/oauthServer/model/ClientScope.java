package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client_scope_t")
public class ClientScope {
	
	@Id
	private ClientScopeId csId; 

	public ClientScope() {
		// TODO Auto-generated constructor stub
	}

	public ClientScopeId getCsId() {
		return csId;
	}

	public void setCsId(ClientScopeId csId) {
		this.csId = csId;
	}

	public ClientScope(ClientScopeId csId) {
		super();
		this.csId = csId;
	}



}
