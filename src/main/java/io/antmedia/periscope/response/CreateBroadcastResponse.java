package io.antmedia.periscope.response;

import io.antmedia.periscope.type.Broadcast;
import io.antmedia.periscope.type.Encoder;
import io.antmedia.periscope.type.VideoAccess;

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
