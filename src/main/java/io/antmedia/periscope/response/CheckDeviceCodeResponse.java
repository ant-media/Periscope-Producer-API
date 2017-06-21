package io.antmedia.periscope.response;

import io.antmedia.periscope.AuthorizationEndpoints;
import io.antmedia.periscope.type.User;

public class CheckDeviceCodeResponse {

	/**
	 * Can either equal authorization_pending, expired, or associated or
	 * network_changed.
	 * 
	 * authorization_pending is sent for up to expires_in seconds from creation of the
	 * device_code or until the device_code is associated with a user account
	 * 
     * expired is sent after expires_in seconds from the creation of the device_code if the
	 * device_code was never associated with a user account.
	 * 
	 * associated is sent if the device_code has been successfully associated with a user account.
	 * 
	 * network_changed is sent if this request is coming from a different IP address than the
	 * IP used to call /device_code/create. In that case, a new request to /device_code/
	 * create should be issued and a new user code displayed. 
	 */
	public String state;
	
	/**
	 * Authentication credential. Included once an authenticated app inputs the
	 * user_code to associate a device with the user’s account.
	 */
	public String access_token;
	
	/**
	 * Number of seconds until this access_token expires
	 */
	public int expires_in;
	
	/**
	 * Token that can be used to request a new access_token once access_token expires
	 */
	public String refresh_token;
	
	/**
	 * User object. Contains information about the currently logged in user. Can be used
	 * for displaying a profile image or username. Necessary for disambiguation if your software
	 * supports multiple users.
	 */
	public User user;
	
	/**
	 * token type, its value is probably Bearer
	 */
	public String token_type;
	
	

	public String toString() {
		return "state: " + state + " access_token: " + access_token + " expires_in: " + expires_in
				+ " refresh_token: " + refresh_token + " token type: " + token_type +" user: " + user;
	}
}