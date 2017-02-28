package com.sentimentanalysis.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import com.googlecode.googleplus.GooglePlusFactory;
import com.googlecode.googleplus.Plus;
import com.googlecode.googleplus.core.OAuth2RefreshListener;
import com.googlecode.googleplus.model.activity.ActivityCollection;
import com.googlecode.googleplus.model.activity.ActivityFeed;
import com.google.gson.Gson;
import com.sun.java.util.*;

@Controller
public class SentimentAnalysisGooglePlusController {

	@RequestMapping("/googleplus")
	public String index(){
		return "gplus";
	}
	
	
	@RequestMapping("/connectgoogle")
	public String connectGoogle(Model model){
		
		
		GooglePlusFactory factory = new GooglePlusFactory("1008345157030-humvjo5a5gm5gepoep5kgkepqf349qmr.apps.googleusercontent.com", "2RpC8ZSwZBieO_cFZsU7Z9BP");
		OAuth2Parameters oAuthParams = new OAuth2Parameters();
		oAuthParams.setRedirectUri("http://localhost:8080/connectgoogle");
		oAuthParams.setScope("https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.moments.write");
		String url = factory.getOAuthOperations().buildAuthenticateUrl(GrantType.AUTHORIZATION_CODE, oAuthParams);
		//response.sendRedirect("/googleplus");
		
		AccessGrant accessGrant = factory.getOAuthOperations()
			    .exchangeForAccess("4/L5zUbV3PceKTSaBT5PBsM0xPAIW-UjO4Hx1RJPrGb2I#", oAuthParams.getRedirectUri(), null);
		

		// the refreshListener is notified in case a new access token is obtained after the old one expires
		Plus plus = factory.getApi(accessGrant.getAccessToken(), accessGrant.getRefreshToken(), null);
		ActivityCollection activitylist = null;
		//ActivityFeed activities = plus.getActivityOperations().list("11864714519250702001", activitylist);
		ActivityFeed activities = plus.getActivityOperations().search("obama", null);
		
		model.addAttribute("url",url);
		model.addAttribute("activities",activities.toString());
		return "gplus";
	}
	
	
		
}
