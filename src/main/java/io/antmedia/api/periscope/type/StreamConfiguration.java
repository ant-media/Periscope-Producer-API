package io.antmedia.api.periscope.type;

/**
 * Object containing Periscope recommended encoder settings
 * @author mekya
 *
 */
public class StreamConfiguration {

	/**
	 * Required video codec
	 */
	public String video_codec;
	
	/**
	 * Recommended video bitrate in kbps
	 */
	public int video_bitrate;
	
	/**
	 * Recommended framerate for encoder settings
	 */
	public int framerate;
	
	/**
	 * Recommended keyframe interval in seconds
	 */
	public int keyframe_interval;
	
	/**
	 * Recommended resolution width
	 */
	public int width;
	
	/**
	 * Recommended resolution height
	 */
	public int height;
	
	/**
	 * Required audio codec
	 */
	public String audio_codec;
	
	/**
	 * Recommended audio sampling rate
	 */
	public int audio_sampling_rate;
	
	/**
	 * Recommended audio bitrate in kbps
	 */
	public int audio_bitrate;
	
	/**
	 * Recommended maximum number of audio channels
	 */
	public int audio_num_channels;
}
