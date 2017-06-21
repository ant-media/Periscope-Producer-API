package io.antmedia.periscope;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import io.antmedia.periscope.response.CreateBroadcastResponse;
import io.antmedia.periscope.response.CreateDeviceCodeResponse;
import io.antmedia.periscope.response.PublishBroadcastResponse;
import io.antmedia.periscope.type.Broadcast;

public class BroadcastEndpoints extends BaseEndpoints {

	private static final String REGION_KEY = "region";
	private static final String IS_360_KEY = "is_360";
	private static final String BROADCAST_ID_KEY = "broadcast_id";
	private static final String TITLE_KEY = "title";
	private static final String SHOULD_NOT_TWEET_KEY = "should_not_tweet";
	private static final String LOCALE_KEY = "locale";
	
	public BroadcastEndpoints(String tokenType, String accessToken) {
		super(tokenType, accessToken);
	}

	/**
	 * Create a new broadcast. If a broadcast already exists and has not been published, this endpoint
	 * may reuse the broadcast that was created previously.
	 * @throws Exception 
	 */
	public CreateBroadcastResponse createBroadcast(String region, boolean is_360) throws Exception {

		String url = ROOT_URL + "/broadcast/create";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(REGION_KEY, region);
		jsonObject.put(IS_360_KEY, is_360);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}

		
		return gson.fromJson(result.toString(), CreateBroadcastResponse.class);

	}


	/**
	 * 
	 * @param broadcast_id - Id of the broadcast to publish
	 * 
	 * @param title - Title for the broadcast
	 * 
	 * @param should_not_tweet - Whether the broadcast should not be Tweeted on publish, default is for
	 *   the broadcast to be Tweeted (should be a JSON bool e.g. true or false)
	 *   
     * @param locale - (Optional) locale of the user. Used for broadcast classification and discovery. Locale
	 *   should be a 2-letter ISO 639-1 language code, followed by an optional 2-letter ISO 3166-1
	 *   region code, separated by an underscore (e.g. en_US)
	 *   
	 * @return
	 * @throws Exception
	 */
	public PublishBroadcastResponse publishBroadcast(String broadcast_id, String title, boolean should_not_tweet, String locale) throws Exception {

		String url = ROOT_URL + "/broadcast/publish";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(BROADCAST_ID_KEY, broadcast_id);
		jsonObject.put(TITLE_KEY, title);
		jsonObject.put(SHOULD_NOT_TWEET_KEY, should_not_tweet);
		jsonObject.put(LOCALE_KEY, locale);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}

		return gson.fromJson(result.toString(), PublishBroadcastResponse.class);
	}
	
	
	/**
	 * @param broadcast_id - Id of the broadcast to stop
	 *
	 * @throws Exception
	 */
	public void stopBroadcast(String broadcast_id) throws Exception {

		String url = ROOT_URL + "/broadcast/stop";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(BROADCAST_ID_KEY, broadcast_id);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}
	}
	
	/**
	 * id - Id of the broadcast to retrieve
	 * @return
	 * @throws Exception
	 */
	public Broadcast getBroadcast(String id) throws Exception {

		
		String url = ROOT_URL + "/broadcast?id="+id;

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
		
		return gson.fromJson(result.toString(), Broadcast.class);
	}
	
	
	public void deleteBroadcast(String broadcast_id) throws Exception {

		String url = ROOT_URL + "/broadcast/delete";

		HttpClient client = HttpClients.custom().build();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(BROADCAST_ID_KEY, broadcast_id);
		StringEntity params =new StringEntity(jsonObject.toJSONString());

		HttpUriRequest post = RequestBuilder.post()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
				.setEntity(params)
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}
	}
	
	

}
