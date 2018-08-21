package io.antmedia.api.periscope;

import io.antmedia.api.periscope.response.AuthorizationResponse;

public class PeriscopeEndpointFactory {
	
	private static AuthorizationEndpoints authorizationEndpoint;
	
	private RegionEndpoints regionEndpoints;
	
	private UserEndpoints userEndpoints;
	
	private BroadcastEndpoints broadcastEndpoints;
	
	private String tokenType;
	
	private String accessToken;
	
	private String refreshToken;

	private ChatEndpoints chatEndpoints;
	
	public PeriscopeEndpointFactory(String tokenType, String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.tokenType = tokenType;
	}
	 
	public static AuthorizationEndpoints getAuthorizationEndpoint() {
		if (authorizationEndpoint == null) {
			authorizationEndpoint = new AuthorizationEndpoints();
		}
		return authorizationEndpoint;
	}
	
	public RegionEndpoints getRegionEndpoints() {
		if (regionEndpoints == null) {
			regionEndpoints = new RegionEndpoints(tokenType, accessToken);
		}
		return regionEndpoints;
		
	}
	
	public UserEndpoints getUserEndpoints() {
		if (userEndpoints == null) {
			userEndpoints = new UserEndpoints(tokenType, accessToken);
		}
		return userEndpoints;
	}
	
	public BroadcastEndpoints getBroadcastEndpoints() {
		if (broadcastEndpoints == null) {
			broadcastEndpoints = new BroadcastEndpoints(tokenType, accessToken);
		}
		return broadcastEndpoints;
	}
	
	public ChatEndpoints getChatEndpoints() {
		if (chatEndpoints == null) {
			chatEndpoints = new ChatEndpoints(tokenType, accessToken);
		}
		return chatEndpoints;
	}
	
	
	public AuthorizationResponse refreshToken(String client_id, String client_secret) {
		AuthorizationEndpoints authEndpoint = getAuthorizationEndpoint();
		
		AuthorizationResponse response = null;
		try {
			response = authEndpoint.authWithOauth2(AuthorizationEndpoints.GRANT_TYPE_REFRESH_TOKEN, "", "", refreshToken, client_id, client_secret);
		
			this.accessToken = response.access_token;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
