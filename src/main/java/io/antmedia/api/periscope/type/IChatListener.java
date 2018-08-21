package io.antmedia.api.periscope.type;

import io.antmedia.api.periscope.type.chatEndpointTypes.ChatMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ErrorMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.HeartMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.JoinMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ScreenshotMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ShareMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.SuperHeartMessage;
import io.antmedia.api.periscope.type.chatEndpointTypes.ViewerCountMessage;

public interface IChatListener {

	/**
	 * Called when chat message received
	 * @param chatMessage
	 */
	void chatMessageReceived(ChatMessage chatMessage);

	/**
	 * Called when screenshot message received
	 * @param screenshotMessage
	 */
	void screenshotMessageReceived(ScreenshotMessage screenshotMessage);

	/**
	 * Called when viewer count message received
	 * @param viewerCountMessage
	 */
	void viewerCountMessageReceived(ViewerCountMessage viewerCountMessage);

	/**
	 * Called when error message received
	 * @param errorMessage
	 */
	void errorMessageReceived(ErrorMessage errorMessage);

	/**
	 * Called when heart message received
	 * @param heartMessage
	 */
	void heartMessageReceived(HeartMessage heartMessage);

	/**
	 * Called when super heart message received
	 * @param heartMessage
	 */
	void superheartMessageReceived(SuperHeartMessage heartMessage);

	/**
	 * Called when join message received
	 * @param joinMessage
	 */
	void joinMessageReceived(JoinMessage joinMessage);

	/**
	 * Called when share message received
	 * @param shareMessage
	 */
	void shareMessageReceived(ShareMessage shareMessage);

}
