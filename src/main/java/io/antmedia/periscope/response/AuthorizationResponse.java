package io.antmedia.periscope.response;

import io.antmedia.periscope.type.User;

public class AuthorizationResponse {
	
	/**
	 * Authentication credential. 
	 */
	public String access_token;
	
	/**
	 * Number of seconds until this access_token expires
	 */
	public String expires_in;
	
	/**
	 * Token that can be used to request a new access_token once access_token
	 * expires
	 */
	public String refresh_token;
	
	/**
	 *  User object. Contains information about the currently logged in user. Can be used
	 *  for displaying a profile image or username. Necessary for disambiguation if your software
	 *  supports multiple users.
	 */
	public User user;
	
	/**
	 * Token type, "Bearer"
	 */
	public String token_type;
	
	
	public String toString() {
		return "access_token: " + access_token + " expires_in: " + expires_in + " refresh_token: " + refresh_token 
					+ " user: " + user;
		
	}
}
