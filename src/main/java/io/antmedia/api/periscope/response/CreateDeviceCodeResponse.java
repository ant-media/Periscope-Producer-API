package io.antmedia.api.periscope.response;

public class CreateDeviceCodeResponse{

	/**
	 *  a randomly generated 6-digit code presented to the user
	 */
	public String user_code;
	
	/**
	 *  a randomly generated 50 character code used by the encoder to poll to see if
	 *  the encoder has been associated with a user account
	 */
	public String device_code;
	
	/**
	 * a hint to the encoder regarding how long they have to use this code before
	 * generating a new device_code. Note: this value is time in seconds instead of a timestamp
	 * to ease implementation on embedded devices that may not have an accurate clock
	 */
	public int expires_in;
	
	/**
	 * a hint to the encoder regarding how often to poll to see if the encoder has been
	 * associated with a user account (likely 10s)
	 */
	public int interval;
	
	/**
	 * URL to present to the user where the user inputs the user_code
	 */
	public String associate_url;

	public String toString() {
		return "user code: " + user_code + " device_code: " + device_code + " expires_in: " + expires_in
				+ " interval: " + interval + " associate_url: " + associate_url;
	}
}