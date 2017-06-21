package io.antmedia.periscope.test;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import io.antmedia.periscope.BroadcastEndpoints;
import io.antmedia.periscope.PeriscopeEndpointFactory;
import io.antmedia.periscope.RegionEndpoints;
import io.antmedia.periscope.response.AuthorizationResponse;
import io.antmedia.periscope.response.CreateBroadcastResponse;
import io.antmedia.periscope.response.PublishBroadcastResponse;
import io.antmedia.periscope.type.Broadcast;

/**
 * 
 * First you need to get access and refresh token. 
 * Please take a look at README document.
 * @author mekya
 *
 */
public class EndpointsTests {
	
	public static final String CLIENT_ID = "WRITE YOUR DEV CLIENT ID";
	
	public static final String CLIENT_SECRET = "WRITE YOUR DEV CLIENT SECRET";
	
	public static final String DEV_ACCESS_TOKEN = "";
	public static final String DEV_REFRESH_TOKEN = "";
	
	public static final String TOKEN_TYPE = "Bearer";
	
	@Test
	public void testRegionGet() {
		RegionEndpoints regionEndpoints = new RegionEndpoints("Bearer", DEV_ACCESS_TOKEN);
		
		try {
			String region = regionEndpoints.get();
			// {"region":"us-west-1"} 
			// starts with two character ^[a-zA-Z]{2} then "-"  any number of characacter(.*) and ends "-" + integer([0-9]{1}$)
			Pattern p = Pattern.compile("^[a-zA-Z]{2}-.*-[0-9]{1}$");
			Matcher m = p.matcher(region);
			assertTrue(m.matches());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	@Test
	public void testCreateBroadcast() {
		BroadcastEndpoints endpoint = new BroadcastEndpoints("Bearer", DEV_ACCESS_TOKEN);
		RegionEndpoints regionEndpoints = new RegionEndpoints("Bearer", DEV_ACCESS_TOKEN);
		
		try {
			CreateBroadcastResponse createBroadcastResponse = endpoint.createBroadcast(regionEndpoints.get(), false);
			assertNotNull(createBroadcastResponse.broadcast);
			assertNotNull(createBroadcastResponse.encoder.display_name);
			assertNotNull(createBroadcastResponse.encoder.rtmp_url);
			assertNotNull(createBroadcastResponse.encoder.rtmps_url);
			assertNotNull(createBroadcastResponse.encoder.stream_key);
			assertNotNull(createBroadcastResponse.encoder.recommended_configuration);
			
			assertNotNull(createBroadcastResponse.video_access.hls_url);
			assertNotNull(createBroadcastResponse.video_access.https_hls_url);
			
			assertNotNull(createBroadcastResponse.share_url);
			

			String title = "test";
			PublishBroadcastResponse publishBroadcastResponse = endpoint.publishBroadcast(createBroadcastResponse.broadcast.id, title, true, new Locale("tr", "TR").toString());
		
			assertEquals(createBroadcastResponse.broadcast.id, publishBroadcastResponse.broadcast.id);
			assertEquals(publishBroadcastResponse.broadcast.state, "running");
			assertEquals(publishBroadcastResponse.broadcast.title, title);
			
			
			endpoint.stopBroadcast(publishBroadcastResponse.broadcast.id);
			
			
			Broadcast broadcast = endpoint.getBroadcast(publishBroadcastResponse.broadcast.id);
			assertEquals(broadcast.id, publishBroadcastResponse.broadcast.id);
			assertEquals(broadcast.state, "ended");
			assertEquals(broadcast.title, title);
			
			endpoint.deleteBroadcast(publishBroadcastResponse.broadcast.id);
			
			try {
				broadcast = endpoint.getBroadcast(publishBroadcastResponse.broadcast.id);
				fail("broadcast should have been deleted and function above threw an exception");
			}
			catch (Exception e) {
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		
	}
	
	
	
	public void testRefreshToken() {
		PeriscopeEndpointFactory factory = new PeriscopeEndpointFactory("Bearer", DEV_ACCESS_TOKEN, DEV_REFRESH_TOKEN);
		
		AuthorizationResponse response = factory.refreshToken(CLIENT_ID, CLIENT_SECRET);
		System.out.println(response);
		
	}
	
	
	

}
