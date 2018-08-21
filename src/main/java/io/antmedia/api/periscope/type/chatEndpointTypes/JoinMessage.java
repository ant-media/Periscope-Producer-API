package io.antmedia.api.periscope.type.chatEndpointTypes;

import io.antmedia.api.periscope.type.User;

/**
 * {
  "id": "iwejfije42ji",
  "type": "join",
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
public class JoinMessage {
	
	/**
	 * id of the message
	 */
	public String id;
	
	/**
	 * Join message type
	 */
	public String type;
	
	/**
	 * User who joined
	 */
	public User user;
	
	/**
	 * Color of the message
	 */
	public String color;

}
