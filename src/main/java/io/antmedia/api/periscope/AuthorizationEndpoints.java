package io.antmedia.api.periscope;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;

import io.antmedia.api.periscope.response.AuthorizationResponse;
import io.antmedia.api.periscope.response.CheckDeviceCodeResponse;
import io.antmedia.api.periscope.response.CreateDeviceCodeResponse;

public class AuthorizationEndpoints extends BaseEndpoints{


	private static final String GRANT_TYPE = "grant_type";
	private static final String CODE = "code";
	private static final String REDIRECT_URI = "redirect_uri";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String DEVICE_CODE_KEY = "device_code";


	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

	/**
	 * Creates a user_code and device_code to use for connecting an encoder (or other device) to a
	 * user account. This endpoint is strongly rate limited by IP to prevent an attacker from exhausting
	 * all codes. 
	 * After creating a user_code and device_code pair, your user interface should display the
	 * user_code to the user along with the associate_url. The user should then navigate in a web
	 * browser to the associate_url and type in the user_code. This creates a link between a Periscope
	 * user account and your software.
	 * 
	 * @param client_id - Client ID unique to the hardware/software vendor. Currently these are available
	 *    by contacting Periscope support.
	 *    
	 * @return CreateDeviceCodeResponse class
	 * @throws Exception if operation is not successfull
	 */
	public CreateDeviceCodeResponse createDeviceCode(String client_id) throws Exception {

		String url = ROOT_URL + "/device_code/create";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CLIENT_ID_KEY, client_id);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		return gson.fromJson(result.toString(), CreateDeviceCodeResponse.class);
	}

	/**
	 * Checks to see if a device_code has been associated with a user account. This endpoint should
	 * be called at the interval returned from /device_code/create to see if the code has been associated
	 * with a user account. Calling this API more frequently, may result in rate-limiting. Returns
	 * an access_token once the device is associated with a user account. For security reasons, this API
	 * must be called from the same IP that called /device_code/create.
	 *
	 * @param deviceCode - a randomly generated 50 character code used by the encoder to poll to see if
	 *    the encoder has been associated with a user account
	 *    
	 * @param client_id - Client ID unique to the hardware/software vendor. Currently these are available
	 * 	  by contacting Periscope support.
	 * 
	 * @return CheckDeviceCodeResponse
	 * @throws Exception if operation is not successfull
	 */
	public CheckDeviceCodeResponse checkDeviceCode(String deviceCode, String client_id) throws Exception {

		String url = ROOT_URL + "/device_code/check";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(CLIENT_ID_KEY, client_id);
		jsonObject.put(DEVICE_CODE_KEY, deviceCode);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		return gson.fromJson(result.toString(), CheckDeviceCodeResponse.class);
	}

	/**
	 * Exchanges an OAuth2 authorization code or refresh token for an access_token.
	 * 
	 * @param grantType GRANT_TYPE_AUTHORIZATION_CODE or GRANT_TYPE_REFRESH_TOKEN
	 * 
	 * @param code - Required if grant_type=authorization_code. Authorization code passed to your service
	 * in the query params of your redirect_uri
	 * 
	 * @param redirect_uri - Required if grant_type=authorization_code. Redirect URI that should exactly
	 * match the redirect URI passed to www.periscope.tv/oauth
	 * 
	 * @param refresh_token - Required if grant_type=refresh_token. Refresh token passed to your service
	 * along with the first access_token retrieved.
	 * 
	 * @param client_id - Client ID of your application
	 * 
	 * @param client_secret - - Client secret for your application (Note: client_secret should only
	 * be sent from applications from which you can reasonably protect your client_secret.
	 * For implementations in purely in-browser applications, or in mobile apps, email
	 * jboiles@twitter.com to ask about our Implicit OAuth2 flow)
	 * 
	 * @return AuthorizationResponse
	 * @throws Exception if operation is not successfull
	 */
	public AuthorizationResponse authWithOauth2(String grantType, String code, String redirect_uri, String refresh_token, String client_id, String client_secret) throws Exception {
		String url = ROOT_URL + "/oauth/token";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();


		jsonObject.put(GRANT_TYPE, grantType);
		jsonObject.put(CODE, code);
		jsonObject.put(REDIRECT_URI, redirect_uri);
		jsonObject.put(REFRESH_TOKEN, refresh_token);
		jsonObject.put(CLIENT_SECRET_KEY, client_secret);
		jsonObject.put(CLIENT_ID_KEY, client_id);

		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		return gson.fromJson(result.toString(), AuthorizationResponse.class);
	}
}
