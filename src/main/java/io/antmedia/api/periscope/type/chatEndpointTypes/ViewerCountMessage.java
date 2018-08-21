package io.antmedia.api.periscope.type.chatEndpointTypes;

/**
 * {
 * "id": "iwejfije42ji",
 * "type": "viewer_count",
 * "live": 34,
 * "total": 139
 * }
 * @author mekya
 *
 */
public class ViewerCountMessage {
	
	/**
	 * id of the message
	 */
	public String id;
	
	/**
	 * Type of the message which is viewer_count
	 */
	public String type;
	
	/**
	 * Number of live viewers
	 */
	public int live;
	
	/**
	 * Number of total viewers
	 */
	public int total;

}
