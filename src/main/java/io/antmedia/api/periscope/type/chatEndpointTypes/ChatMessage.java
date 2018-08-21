package io.antmedia.api.periscope.type.chatEndpointTypes;

import io.antmedia.api.periscope.type.User;

/**
 * {
  "id": "iwejfije42ji",
  "type": "chat",
  "text": "Hello",
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
public class ChatMessage {

	/**
	 * id of the message
	 */
	public String id;
	
	/**
	 * Type of the content which is chat
	 */
	public String type;
	
	/**
	 * The comment text
	 */
	public String text;
	
	/**
	 * The user who wrote this comment
	 */
	public User user;
	
	/**
	 * Color field in the chat message
	 */
	public String color;
}
