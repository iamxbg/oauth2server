package oauthServer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="client_t")
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String client_id;
	private String name;
	private String client_secrect;
	private String description;
	private String provider;
	private String receive_authcode_uri;
	private String auth_type;
	private int del_flag=0;
	private String profile;

	
	public Client() {
		// TODO Auto-generated constructor stub
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient_secrect() {
		return client_secrect;
	}

	public void setClient_secrect(String client_secrect) {
		this.client_secrect = client_secrect;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getReceive_authcode_uri() {
		return receive_authcode_uri;
	}

	public void setReceive_authcode_uri(String receive_authcode_uri) {
		this.receive_authcode_uri = receive_authcode_uri;
	}

	public String getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public int getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(int del_flag) {
		this.del_flag = del_flag;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}



}
