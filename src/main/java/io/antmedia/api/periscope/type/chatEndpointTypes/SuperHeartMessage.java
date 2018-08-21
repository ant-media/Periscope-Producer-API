package io.antmedia.api.periscope.type.chatEndpointTypes;

import io.antmedia.api.periscope.type.User;

/**
 * {
  "type": "super_heart",
  "user": {
    "id": "1XMJvgKJRLBnR",
    "username": "bonjoiles",
    "display_name": "John Bon Joiles",
    "profile_image_urls": [
      {
        "url": "https://abs.twimg.com/sticky/default_profile_images/default_profile_reasonably_small.png"
      }
    ]
  },
  "color": "#99CE62",
  "amount": 111,
  "tier": 2
}
 * @author mekya
 *
 */
public class SuperHeartMessage {	
	/**
	 * Type of the message super_heart
	 */
	public String type;
	/**
	 * User who sent this message
	 */
	public User user;
	/**
	 * Color of the message
	 */
	public String color;
	/**
	 * Amount field
	 */
	public int amount;
	/**
	 * Tier field
	 */
	public int tier;
}
