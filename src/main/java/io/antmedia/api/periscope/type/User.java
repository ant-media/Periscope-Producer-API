package io.antmedia.api.periscope.type;

import java.util.List;

public class User {
	
	
	/**
	 * Unique ID for the user
	 */
	public String id;
	
	/**
	 * username of the account on Periscope
	 */
	public String username;
	
	/**
	 * Unique ID corresponding to the user on Twitter
	 */
	public String twitter_id;
	
	/**
	 * Username of the account on Twitter
	 */
	public String twitter_username;
	
	/**
	 * User ’bio’
	 */
	public String description;
	
	/**
	 * Typically the user’s full name
	 */
	public String display_name;
	
	/**
	 * Array of profile images at different sizes
	 */
	public List<ProfileImageUrls> profile_image_urls;
	
	
	public class ProfileImageUrls {
		/**
		 * Width of the profile image
		 */
		public int width;
		
		/**
		 * Height of the profile image
		 */
		public int height;
		
		/**
		 * HTTPS  URL for this profile image
		 */
		public String ssl_url; 
		
		/**
		 *  HTTP URL for this profile image
		 */
		public String url;
	}

}
