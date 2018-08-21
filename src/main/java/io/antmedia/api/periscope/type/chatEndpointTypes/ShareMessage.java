package io.antmedia.api.periscope.type.chatEndpointTypes;

import io.antmedia.api.periscope.type.User;

/**
 * id": "iwejfije42ji",
  "type": "share",
  "service": "twitter",
  "user": {
    "id": "awiefieife",
    "twitter_id": "asdfeimfiein",
    "username": "johnboiles",
    "display_name": "John Boiles",
    "profile_image_urls": [
      {"url": "https://cdn.twitter.com/user/image.jpg"}
    ],
    "locale": "en",
    "languages": ["en", "fr", "es"],
    "superfan": true
  },
  "color": "#44eeff"
}
 * @author mekya
 *
 */
public class ShareMessage {
	/**
	 * id of the message
	 */
	public String id;
	
	/**
	 * Type of the message share
	 */
	public String type;
	
	/**
	 * Name of the service that stream shared
	 */
	public String service;
	
	/**
	 * User who sent this message
	 */
	public User user;
	
	/**
	 * Color of the message
	 */
	public String color;
}
