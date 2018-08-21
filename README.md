
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.antmedia.api.periscope/PeriscopeAPI/badge.svg)](https://search.maven.org/search?q=g:io.antmedia.api.periscope)

ChatEndpoint is implemented according to documentation in version 1.2.0

# Periscope Producer API
Java implementation of Periscope Producer API

This api lets you use Periscope Producer API which is announced [with this post on twitter blog](https://blog.twitter.com/developer/en_us/topics/tools/2017/introducing-the-periscope-producer-api.html)
and lots of tech sites like [Variety](http://variety.com/2017/digital/news/twitter-periscope-producer-api-1202012751/),
[VentureBeat](https://venturebeat.com/2017/03/21/twitters-producer-api-lets-you-stream-live-videos-without-connecting-to-periscope/), etc.

## Installation
 
 ```
<dependency>
  <groupId>io.antmedia.api.periscope</groupId>
  <artifactId>PeriscopeAPI</artifactId>
  <version>1.2.0</version>
</dependency>
```



## How To Use


### Authorize the App
Firstly fill the [Periscope API Private Beta Waitlist Form](https://docs.google.com/forms/d/e/1FAIpQLSetUfieM1ymWbS5XOYPo0DC1smyHDa8jfi4sL-LtJNSa_FE8Q/viewform) to get **client id** and **client secret**.
It may take some time to have a reply from support. Btw, they are sometimes very quick to respond.  

Two methods to authorize your application.
1. **Device Code**

    Useful for hardware and software devices that don’t have access to a web browser. 
    
    ```java
    //create an AuthorizationEndPoint instance
    AuthorizationEndpoints authorizationEndpoint = PeriscopeEndpointFactory.getAuthorizationEndpoint();
    
    //make a create device code request
    CreateDeviceCodeResponse createDeviceCodeResponse = authorizationEndpoint.createDeviceCode(CLIENT_ID);
    
    //Follow the instructions 
    System.out.println("Go to this url: " + createDeviceCodeResponse.associate_url);
    System.out.println("Enter this user code: " + createDeviceCodeResponse.user_code);
    System.out.println("and come back here");
    
    CheckDeviceCodeResponse checkDeviceCode;
    do {
        System.out.println("Waiting for " + createDeviceCodeResponse.interval + " seconds to check device code");
        
        //wait for interval seconds to check device code
        Thread.sleep(createDeviceCodeResponse.interval * 1000);

        //make a check device code to test if user code is entered to associated url as mentioned above
        checkDeviceCode = authorizationEndpoint.checkDeviceCode(createDeviceCodeResponse.device_code, CLIENT_ID);
				
    } while(!checkDeviceCode.state.equals("associated"));
    // save access and refresh token fields to use in later requests.
			
    ```

2. **OAuth2 Authorization Code**

    Useful for software that has access to a web browser. 
    * Go to link and client id and redirect uri (https://www.periscope.tv/oauth?client_id=YOUR_COMPANY_CLIENT_ID&redirect_uri=https%3A%2F%2Fwww.yoursite.com%2Foauth%)
    * Authenticate the application
    * Periscope redirects to redirect_uri with **code** variable. 
    * Use below code to get access and refresh token with code variable
    
    ```java
    AuthorizationEndpoints authorizationEndpoint = PeriscopeEndpointFactory.getAuthorizationEndpoint();
    String code = "code returned by periscope";
    String redirect_uri = " redirect uri in your application";

    AuthorizationResponse authorizationResponse = authorizationEndpoint.authWithOauth2(AuthorizationEndpoints.GRANT_TYPE_AUTHORIZATION_CODE, 
					code, redirect_uri, null, CLIENT_ID, CLIENT_SECRET);
    //save access token, refresh token variables
    ```
### Create and Publish Broadcast
 ```java
 //Initialize Periscope Endpoint Factory by giving access and refresh token
 PeriscopeEndpointFactory periscopeEndpointFactory = 
     new PeriscopeEndpointFactory(TOKEN_TYPE, ACCESS_TOKEN, REFRESH_TOKEN);
        
//get broadcast end point     
BroadcastEndpoints endpoint = periscopeEndpointFactory.getBroadcastEndpoints();
     
// get region end point
RegionEndpoints regionEndpoints = periscopeEndpointFactory.getRegionEndpoints();
    
//create broadcast
CreateBroadcastResponse createBroadcastResponse = 
       endpoint.createBroadcast(regionEndpoints.get(), false);
          
//createBroadcastResponse has all the required information you need for your encoder and player
//rtmp, hls, configuration etc.
    
//right now it is time to publish
String title = "test";
PublishBroadcastResponse publishBroadcastResponse = 
             endpoint.publishBroadcast(createBroadcastResponse.broadcast.id, title, true, 
                                        new Locale("tr", "TR").toString());

 ```
 
After you publish broadcast, start your encoder
     
     
### Stop Broadcast
Just call BroadcastEndpoints' stopBroadcast function with the broadcast id that is returned in creating and publishing broadcast

```java

endpoint.stopBroadcast(publishBroadcastResponse.broadcast.id);

```    

### Delete Broadcast
Just call BroadcastEndpoints' deleteBroadcast function with the broadcast id that is returned in creating and publishing broadcast

```java

endpoint.deleteBroadcast(publishBroadcastResponse.broadcast.id);

```  

### Get Broadcast
Just call BroadcastEndpoints' getBroadcast function with the broadcast id to query the broadcast

```java

Broadcast broadcast = endpoint.getBroadcast(publishBroadcastResponse.broadcast.id);
assertEquals(broadcast.id, publishBroadcastResponse.broadcast.id);
assertEquals(broadcast.state, "ended");
assertEquals(broadcast.title, title);

```  


## Build

Clone the repository
```
git clone https://github.com/ant-media/Periscope-Producer-API.git

cd Periscope-Producer-API/

mvn install
```

Then the created jar will be on target directory.






