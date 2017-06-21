package io.antmedia.periscope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import io.antmedia.periscope.response.CheckDeviceCodeResponse;

public class RegionEndpoints extends BaseEndpoints {
	
	public RegionEndpoints(String tokenType, String accessToken) {
		super(tokenType, accessToken);
	}

	/**
	 * Gets an estimation of the user’s current region using latency-based routing. 
	 * Useful for determining a sane default for the region in /broadcast/create. 
	 * Note that this endpoint redirects to another service. 
	 * Clients calling this endpoint should handle redirects gracefully.
	 *
	 * @throws Exception 
	 * @returns region
	 */
	public String get() throws Exception {
		
		String url = ROOT_URL + "/region";

		HttpClient client = HttpClients.custom()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build();
		
		HttpUriRequest post = RequestBuilder.get()
		  .setUri(url)
		  .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		  .setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
		  .setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
		  .build();
		
		HttpResponse response = client.execute(post);
				
		StringBuffer result = readResponse(response);
		
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}
		
		JSONParser parser = new JSONParser();
		
		JSONObject jsonObject = (JSONObject) parser.parse(result.toString());
		if (!jsonObject.containsKey("region")) {
			throw new Exception("Region field does not exist");
		}
		return jsonObject.get("region").toString();
	}
}
