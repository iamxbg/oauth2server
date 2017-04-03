package oauthServer.util;

public interface OAuthConstants {
		
		/**
		 *  local paramters , may change later!
		 */
	
		 final String scheme_SSL="https";
		 final String scheme_plain="http";
		 final String hostname="127.0.0.1";
		 final String app_name="oauthServer";
		 final String port="8082";
		 final String ssl_port="8443";
		 final String auth_type_server_path="ssa";
		 final String auth_type_client_path="csa";
		 final String auth_view_uri="authView";
		 final String acesss_token_uri="token";
		 final String authorize_uri="authorize";
		 final String refresh_token_uri="refresh_token";
		 final String auth_page_uri="authorize.jsp";
	 
		 public static String MANAGE_SWITCHER_REGEN_OPENID="regen_openid";
		 public static char MANAGE_SWITCHER_IS_ON_YES='Y';
		 public static char MANAGE_SWITHCER_IS_ON_NO='N';
	 	/**
	 	 * oauth_nouns
	 	 */
	 	public static String ACCESS_TOKEN="access_token";
	 	public static String TOKEN_TYPE="token_type";
	 	public static String REFRESH_TOKEN="refresh_token";
	 	public static String AUTHORIZATION_CODE="authorization_code";
	 	public static String STATE="state";
	 	public static String ERROR="error";
	 	public static String ERROR_DESCRIPTION="error-description";
	 	public static String USER_ID="user_id";
	 	public static String USERNAME="username";
	 	public static String PASSWORD="password";
	 	public static String SERVICE_ID="service_id";
	 	public static String TEL="tel";
	 	public static String EXPIRES_IN="expires_in";
	 	public static String CLIENT_ID="client_id";
	 	public static String REDIRECT_URI="redirect_uri";
	 	public static String RESPONSE_TYPE="response_type";
	 	public static String CODE="code";
	 	
	 	
	 	public static String ERROR_CLIENT_NOT_EXISTS="應用未註冊!";
	 	public static String ERROR_SERVICE_NOT_EXISTS="未知服務!";
	 	public static String ERROR_AUTHORIZE_FAIL="驗證失敗!";
	 	public static String ERROR_WRONG_GRANT_TYPE="授權模式錯誤!";
	 	public static String ERROR_SEND_AUTHCODE_FAIL="發送授權碼失敗!";
	 	public static String ERROR_REFRESH_TOKEN_NOT_FOUND="未找到refreshToken,可能已經過期!";
	 	public static String ERROR_CLIENT_AUTH_FAIL="應用驗證失敗!";
	 	public static String ERROR_ExPIRED="已過期!";
		 /**
		  *  expires_in
		  */
		public static long AUTHORIZATION_CODE_EXPIRES_IN=3600;
		public static long ACCESS_TOKEN_CODE_EXPIRES_IN=3600;
		public static long ACCESS_TOKEN_TOKEN_EXPIRES_IN=3600;
		public static long REFRESH_TOKEN_EXPIRES_IN=1000000;
		public static long OPENID_TOKEN_EXPIRES_IN=5000;
		public static long OPENID_EXPIRES_IN=3000;
		/**
		 *  authorize_page
		 */
		public String AUTHORIZATION_PAGE=new StringBuilder(scheme_SSL)
							.append("://").append(hostname)
							.append(":").append(ssl_port)
							.append("/").append(app_name)
							.append("/").append(auth_page_uri).toString();
		
		//-----------------------server-side-authorize							
		
		
		/**
		 *  server-side authorize-view uri
		 */
		public static String SSA_AUTHORIZTION_VIEW_URI=new StringBuilder(scheme_plain)
				.append("://").append(hostname)
				.append(":").append(port)
				.append("/").append(app_name)
				.append("/").append(auth_type_server_path)
				.append("/").append(auth_view_uri).toString();
		
		/**
		 *  server-side token uri
		 */
		public static String SSA_TOKEN_URI=new StringBuilder(scheme_SSL)
								.append("://").append(hostname)
								.append(":").append(ssl_port)
								.append("/").append(app_name)
								.append("/").append(auth_type_server_path)
								.append("/").append(acesss_token_uri).toString();
		
		/**
		 *  server-side authorize uri
		 */
		public static String SSA_AUTHORIZE_URI=new StringBuilder(scheme_SSL)
									.append("://").append(hostname)
									.append(":").append(ssl_port)
									.append("/").append(app_name)
									
									.append("/").append(auth_type_server_path)
									.append("/").append(authorize_uri).toString();
		
		/**
		 *  server-side refresh token uri
		 */
		public static String SSA_REFRESH_TOKEN_URI=new StringBuilder(scheme_SSL)
								.append("://").append(hostname)
								.append(":").append(ssl_port)
								.append("/").append(app_name)
								.append("/").append(auth_type_server_path)
								.append("/").append(refresh_token_uri).toString();
		
		/**
		 *  client-side authorize-view
		 */
		
		public static String CSA_AUTHORIZTION_VIEW_URI=new StringBuilder(scheme_SSL)
				.append("://").append(hostname)
				.append(":").append(ssl_port)
				.append("/").append(app_name)
				.append("/").append(auth_type_client_path)
				.append("/").append(auth_view_uri).toString();
		
		/**
		 *  client-side authrize_uri
		 */
		public static String CSA_AUTHORIZE_URI=new StringBuilder(scheme_SSL)
				.append("://").append(hostname)
				.append(":").append(ssl_port)
				.append("/").append(app_name)		
				.append("/").append(auth_type_client_path)
				.append("/").append(authorize_uri).toString();


}
