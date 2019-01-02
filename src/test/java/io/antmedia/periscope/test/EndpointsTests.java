package io.antmedia.periscope.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import io.antmedia.api.periscope.AuthorizationEndpoints;
import io.antmedia.api.periscope.BroadcastEndpoints;
import io.antmedia.api.periscope.PeriscopeEndpointFactory;
import io.antmedia.api.periscope.RegionEndpoints;
import io.antmedia.api.periscope.response.AuthorizationResponse;
import io.antmedia.api.periscope.response.CheckDeviceCodeResponse;
import io.antmedia.api.periscope.response.CreateBroadcastResponse;
import io.antmedia.api.periscope.response.CreateDeviceCodeResponse;
import io.antmedia.api.periscope.response.PublishBroadcastResponse;
import io.antmedia.api.periscope.type.Broadcast;


/**
 * 
 * First you need to get access and refresh token. 
 * Please take a look at README document.
 * @author mekya
 *
 */


public class EndpointsTests {

/*	public static final String CLIENT_ID = "3uUjYUYH8XX8ikOhfo45s_slM4xDZ9fxMN32T3m5xIrTU2LyrO";
	//"WRITE YOUR DEV CLIENT ID";

	public static final String CLIENT_SECRET = "WRITE YOUR DEV CLIENT SECRET";
*/	
	
	public String CLIENT_ID = "5PXoLeNEcFEKBYOh2W-lJHTF_D584hF4XI-ENDIHCOCzArNaMx";
	public String CLIENT_SECRET = "tYHjmoe42iD1FX0wSLgF7-4kdnM9mabgznuSdaSkVDFFflYomK";

	public static String DEV_ACCESS_TOKEN = "NT1C4kWBDKUG8x4gEMPO-8MGpHl39KryIsEwTJHiOIDUuCgQKq";
	public static String DEV_REFRESH_TOKEN = "AO5Y63_EEAuwY1TgYcxgzYqn-G06RPhjXcVFxDS60VW7aS5-PI";

	public static final String TOKEN_TYPE = "Bearer";

	//@Test
	public void testAuthorizeWithDeviceCode() {
		AuthorizationEndpoints authorizationEndpoint = PeriscopeEndpointFactory.getAuthorizationEndpoint();
		CreateDeviceCodeResponse createDeviceCodeResponse;
		try {
			createDeviceCodeResponse = authorizationEndpoint.createDeviceCode(CLIENT_ID);

			assertNotNull(createDeviceCodeResponse);

			System.out.println("Go to this url: " + createDeviceCodeResponse.associate_url);
			System.out.println("Enter this user code: " + createDeviceCodeResponse.user_code);
			System.out.println("and come back here");

			CheckDeviceCodeResponse checkDeviceCode;
			do {
				System.out.println("Waiting for " + createDeviceCodeResponse.interval + " seconds to check device code");
				Thread.sleep(createDeviceCodeResponse.interval * 1000);

				checkDeviceCode = authorizationEndpoint.checkDeviceCode(createDeviceCodeResponse.device_code, CLIENT_ID);
				
				System.out.println("State: " + checkDeviceCode.state);
				

			} while(!checkDeviceCode.state.equals("associated"));
			
			assertTrue(checkDeviceCode.state.equals("associated"));
			DEV_ACCESS_TOKEN = checkDeviceCode.access_token;
			System.out.println("access token: " + DEV_ACCESS_TOKEN);
			DEV_REFRESH_TOKEN = checkDeviceCode.refresh_token;
			System.out.println("refresh token: " + DEV_REFRESH_TOKEN);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	
	
	public void testAuthorizeWithOauth2() {
		AuthorizationEndpoints authorizationEndpoint = PeriscopeEndpointFactory.getAuthorizationEndpoint();
		String code = "code returned by periscope";
		String redirect_uri = " redirect uri in your application";
		
		try {
			AuthorizationResponse authorizationResponse = authorizationEndpoint.authWithOauth2(AuthorizationEndpoints.GRANT_TYPE_AUTHORIZATION_CODE, 
					code, redirect_uri, null, CLIENT_ID, CLIENT_SECRET);
			
			assertNotNull(authorizationResponse);
			//save access token, refresh token variables
			DEV_ACCESS_TOKEN = authorizationResponse.access_token;
			DEV_REFRESH_TOKEN = authorizationResponse.refresh_token;
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}


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
	public void testHostName() {
		try {
			
			String hostAddress = Inet4Address.getByName("api-ws.pscp.tv").getHostAddress();
			System.out.println("host addr: " + hostAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testCreateBroadcast() {
		PeriscopeEndpointFactory periscopeEndpointFactory = new PeriscopeEndpointFactory(TOKEN_TYPE, DEV_ACCESS_TOKEN, DEV_REFRESH_TOKEN);
		BroadcastEndpoints endpoint = periscopeEndpointFactory.getBroadcastEndpoints();
		RegionEndpoints regionEndpoints = periscopeEndpointFactory.getRegionEndpoints();
		
		try {
			CreateBroadcastResponse createBroadcastResponse = endpoint.createBroadcast(regionEndpoints.get(), false, true);
			assertNotNull(createBroadcastResponse.broadcast);
			assertNotNull(createBroadcastResponse.encoder.display_name);
			assertNotNull(createBroadcastResponse.encoder.rtmp_url);
			assertNotNull(createBroadcastResponse.encoder.rtmps_url);
			assertNotNull(createBroadcastResponse.encoder.stream_key);
			assertNotNull(createBroadcastResponse.encoder.recommended_configuration);

			assertNotNull(createBroadcastResponse.video_access.hls_url);
			//assertNotNull(createBroadcastResponse.video_access.https_hls_url);

			assertNotNull(createBroadcastResponse.share_url);


			String title = "test";
			PublishBroadcastResponse publishBroadcastResponse = endpoint.publishBroadcast(createBroadcastResponse.broadcast.id, title, true, new Locale("tr", "TR").toString(), true);

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
