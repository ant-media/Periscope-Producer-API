package io.antmedia.api.periscope.response;

import io.antmedia.api.periscope.type.Broadcast;
import io.antmedia.api.periscope.type.Encoder;
import io.antmedia.api.periscope.type.VideoAccess;

public class CreateBroadcastResponse {

	/**
	 * Broadcast object
	 */
	public Broadcast broadcast;

	/**
	 * Information for streaming the video
	 */
	public VideoAccess video_access;

	/**
	 * Public URL for watching this broadcast
	 */
	public String share_url;

	/**
	 * Encoder object representing the streaming configuration for this device / user
	 */
	public Encoder encoder;

}
