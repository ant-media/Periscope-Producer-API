package io.antmedia.api.periscope;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import io.antmedia.api.periscope.type.IChatListener;
import io.antmedia.api.periscope.type.chatEndpointTypes.ChatMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ErrorMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.HeartMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.JoinMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ScreenshotMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ShareMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.SuperHeartMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ViewerCountMessage;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.impl.headers.VertxHttpHeaders;

public class ChatEndpoints extends BaseEndpoints {

	private static final String ERROR_TYPE = "error";
	private static final String VIEWER_COUNT_TYPE = "viewer_count";
	private static final String SCREENSHOT_TYPE = "screenshot";
	private static final String CHAT_TYPE = "chat";
	public static final String CHAT_SCOPE = "chat";
	private static final String HEART_TYPE = "heart";
	private static final String SUPER_HEART_TYPE = "super_heart";
	private static final String JOIN_TYPE = "join";
	private static final String SHARE_TYPE = "share";
	
	private static Vertx vertx = Vertx.vertx();
	private Map<String, WebSocket> websocketMap = new HashMap<>();
	private JSONParser jsonParser = new JSONParser();

	public ChatEndpoints(String tokenType, String accessToken) {
		super(tokenType, accessToken);
	}

	public void connect(String broadcastId, IChatListener listener) {

		HttpClient client = vertx.createHttpClient();
		RequestOptions reqOptions;
		reqOptions = new RequestOptions()
				.setHost("api-ws.pscp.tv")
				.setSsl(true)
				.setPort(443)
				.setURI("/v1/chat/connect?broadcast_id=" + broadcastId);


		VertxHttpHeaders headers = new VertxHttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken());


		client.websocket(reqOptions, headers, websocketTmp -> {
			System.out.println("Websockket connected for " + broadcastId);
			
			websocketMap.put(broadcastId, websocketTmp);

			websocketTmp.frameHandler(frame -> {

				String textData = frame.textData();
				try {
					JSONObject jsonObject = (JSONObject) jsonParser.parse(textData);

					String type = (String) jsonObject.get("type");
					if (type.equals(CHAT_TYPE)) {
						ChatMessage chatMessage = gson.fromJson(textData, ChatMessage.class);
						listener.chatMessageReceived(chatMessage);
					}
					else if (type.equals(SCREENSHOT_TYPE)) {
						ScreenshotMessage screenshotMessage = gson.fromJson(textData, ScreenshotMessage.class);
						listener.screenshotMessageReceived(screenshotMessage);
					}
					else if (type.equals(VIEWER_COUNT_TYPE)) {
						ViewerCountMessage viewerCountMessage = gson.fromJson(textData, ViewerCountMessage.class);
						listener.viewerCountMessageReceived(viewerCountMessage);
					}
					else if (type.equals(ERROR_TYPE)) {
						ErrorMessage errorMessage = gson.fromJson(textData, ErrorMessage.class);
						listener.errorMessageReceived(errorMessage);
					}
					else if (type.equals(HEART_TYPE)) {
						HeartMessage heartMessage = gson.fromJson(textData, HeartMessage.class);
						listener.heartMessageReceived(heartMessage);
					}
					else if (type.equals(SUPER_HEART_TYPE)) {
						SuperHeartMessage heartMessage = gson.fromJson(textData, SuperHeartMessage.class);
						listener.superheartMessageReceived(heartMessage);
					}
					else if (type.equals(JOIN_TYPE)) {
						JoinMessage joinMessage = gson.fromJson(textData, JoinMessage.class);
						listener.joinMessageReceived(joinMessage);
					}
					else if (type.equals(SHARE_TYPE)) {
						ShareMessage shareMessage = gson.fromJson(textData, ShareMessage.class);
						listener.shareMessageReceived(shareMessage);
					}

					

				} catch (ParseException e) {
					e.printStackTrace();
				}

			});
		}, failureHandler -> {
			failureHandler.printStackTrace();
		});


	}

	public void disconnect(String broadcastId) {
		WebSocket webSocket = websocketMap.remove(broadcastId);
		if (webSocket != null) {
			webSocket.close();
		}
	}
}
