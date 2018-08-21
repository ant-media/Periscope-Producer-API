package io.antmedia.api.periscope.type;

import java.util.List;

/**
 *  "user": {
 *   "id": "awiefieife",
 *   "twitter_id": "asdfeimfiein",
 *   "username": "johnboiles",
 *   "display_name": "John Boiles",
 *   "profile_image_urls": [
 *     {"url": "https://cdn.twitter.com/user/image.jpg"}
 *   ],
 *     "locale": "en",
       "languages": ["en", "fr", "es"],
       "superfan": true
 *   }
 * @author mekya
 *
 */
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
	
	/**
	 * Locale field, this field is defined in chat endpoint messages
	 */
	private String locale;
	
	/**
	 * This field is defined in chat endpoint messages
	 */
	private List<String> languages;
	
	/**
	 * this field is defined in chat endpoint message
	 */
	private boolean superfan;

}
