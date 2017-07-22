package io.antmedia.api.periscope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

import com.google.gson.Gson;

public abstract class BaseEndpoints {
	
	public static final String ROOT_URL = "https://public-api.periscope.tv/v1";
	
	public static final String USER_AGENT = "AntMedia.AntMediaServer v1.0 Java";
	
	public static final String CLIENT_ID_KEY = "client_id";
	
	public static final String CLIENT_SECRET_KEY = "client_secret";

	private String tokenType;
	private String accessToken;

	protected Gson gson = new Gson();
	
	public BaseEndpoints() {
		
	}
	
	public BaseEndpoints(String tokenType, String accessToken) {
		this.tokenType = tokenType;
		this.accessToken = accessToken;
	}

	protected String getAccessToken() {
		return accessToken;
	}

	protected String getTokenType() {
		return tokenType;
	}

	protected StringBuffer readResponse(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
	
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result;
	}

}
