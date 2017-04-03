package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service_client_t")
public class ServiceClient {

	@Id
	private ServiceClientId scId;
	
	
	public ServiceClient(){
		
	}
	
	public ServiceClient(ServiceClientId scId){
		this.scId=scId;
	}

	public ServiceClientId getScId() {
		return scId;
	}

	public void setScId(ServiceClientId scId) {
		this.scId = scId;
	}
	

}
