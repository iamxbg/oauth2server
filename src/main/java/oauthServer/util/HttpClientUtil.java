package oauthServer.util;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

@Component
public class HttpClientUtil {

	private  HttpClientConnectionManager manager=null;
	private String keystore_type="JKS";
	private HostnameVerifier verifier=SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	
	public HttpClientUtil() {
		// TODO Auto-generated constructor stub
			// TODO Auto-generated constructor stub
			
			KeyStore keystore;
			try {
				keystore = KeyStore.getInstance(keystore_type);
				SSLContext sslContext=SSLContexts.custom()
						.loadTrustMaterial(keystore, new TrustSelfSignedStrategy()).build();
	
				SSLConnectionSocketFactory ssl_csf=new SSLConnectionSocketFactory(sslContext, verifier);
				ConnectionSocketFactory p_csf=new PlainConnectionSocketFactory();
				
				
				Registry<ConnectionSocketFactory> registry=RegistryBuilder.<ConnectionSocketFactory>create().register("http", p_csf)
									.register("https", ssl_csf)
									.build();
				
			this.manager=new PoolingHttpClientConnectionManager(registry);
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
	
	public  HttpClientConnectionManager getManager(){
		return manager;
	}
	
	

	
}	
