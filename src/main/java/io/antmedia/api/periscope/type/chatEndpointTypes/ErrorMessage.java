package io.antmedia.api.periscope.type.chatEndpointTypes;

/**
 * {
 * "id": "iwejfije42ji",
 * "type": "error",
 * "description": "Invalid chat message"
 * }
 * @author mekya
 *
 */
public class ErrorMessage {

	/**
	 * id of the message
	 */
	public String id;
	
	/**
	 * Type of the message which is error
	 */
	public String type;
	
	/**
	 * Description of the error
	 */
	public String description;
}
