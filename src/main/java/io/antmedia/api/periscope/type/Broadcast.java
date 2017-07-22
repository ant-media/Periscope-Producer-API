package io.antmedia.api.periscope.type;

/**
 * Information about a Periscope Broadcast
 * @author mekya
 *
 */
public class Broadcast {
	
	/**
	 * Unique ID for this broadcast
	 */
	public String id; 
	
	/**
	 * One of not_started, running, timed_out, ended
	 */
	public String state;
	
	/**
	 * User visible title for this broadcast
	 */
	public String title;

}
