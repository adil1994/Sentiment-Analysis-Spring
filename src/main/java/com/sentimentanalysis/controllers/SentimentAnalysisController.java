package com.sentimentanalysis.controllers;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.MediaEntity;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;
import com.sentimentanalysis.model.FaceEmotion;
import com.sentimentanalysis.services.EmotionImageService;
import com.sentimentanalysis.utils.Preprocessing;
import com.sentimentanalysis.utils.RDFParsing;
import com.vdurmont.emoji.EmojiParser;

import javassist.expr.NewArray;

import com.sentimentanalysis.utils.ImageProcessing;

@Controller
public class SentimentAnalysisController {

	@Autowired
	private Twitter twitter;
	
	@Autowired
	public EmotionImageService serviceEI;

	@RequestMapping("/senticnet")
	@ResponseBody
	public String index() {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		String result;
		
		try {
			return http.sendGet("dog","polarity");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("ERROR => " + e.getMessage());
		}
		return "no data";
	}
	
	@RequestMapping("/emoji")
	@ResponseBody
	public String emojis() {
		String str = "🕋🕌🕳️🇲🇦";
		String result = EmojiParser.parseToAliases(str);
		return result;
	}
	

	@RequestMapping("/")
	public String hello(@RequestParam(defaultValue = "trump") String search, Model model) {
		HttpURLConnectionExample http = new HttpURLConnectionExample();
		
		TwitterProfile profile = twitter.userOperations().getUserProfile("hamadvsolutions");
		List<Tweet> tweets = twitter.timelineOperations().getUserTimeline("hamadvsolutions");

		
		SearchResults searchResults = twitter.searchOperations().search(search,2);
		//List<Tweet> tweets = searchResults.getTweets().stream().collect(Collectors.toList());
		model.addAttribute("tweets", tweets);

		
		List<Float> polarities = new ArrayList<>();
		ArrayList<String> tweetUrls = new ArrayList<String>();
		List<List<FaceEmotion>> tweetImageEmotion = new ArrayList<>();
		for (Tweet tweet : tweets) {
			
			
			if(tweet.getEntities().getMedia().size()  == 0  ){
				
				tweetUrls.add("http://pbs.twimg.com/profile_images/2284174872/7df3h38zabcvjylnyfe3.png");
				ArrayList<FaceEmotion> faces = new ArrayList<FaceEmotion>();
				faces.add(new FaceEmotion(0,0,0,0,0,0,0,0));
				tweetImageEmotion.add(faces);
			}
				
				
			for(int i = 0 ; i< tweet.getEntities().getMedia().size() ; i++ ){
				if(tweet.getEntities().getMedia().size() > 0){
					
					tweetUrls.add(tweet.getEntities().getMedia().get(i).getMediaUrl());
					try {
						
						List<FaceEmotion> faceEmotion = serviceEI.getEmotion(tweet.getEntities().getMedia().get(i).getMediaUrl());
						if(!faceEmotion.isEmpty())
							tweetImageEmotion.add(faceEmotion);
						else{
							ArrayList<FaceEmotion> faces = new ArrayList<FaceEmotion>();
							faces.add(new FaceEmotion(0,0,0,0,0,0,0,0));
							tweetImageEmotion.add(faces);
						}
						
						
					} catch (EmotionServiceException e) {
						System.out.println("error : " + e.getMessage());
					} catch (IOException e) {
						System.out.println("error : " + e.getMessage());
					}			
					
				}
			}
				
			
			
			
			
			String[] words;
			String mtweet = Preprocessing.removeSpecialCharacters(Preprocessing.emojisToText(tweet.getText()));
			words = Preprocessing.splitTweet(mtweet);
			
			float sum = 0;
			int i = 0;
			for (String wd : words) {
				
				try {
					
					String polarity_result = http.sendGet(wd,"polarity");
					
					if(!polarity_result.startsWith("E")){
						//String polarity = RDFParsing.getPolarityFromXML(polarity_result);
						String intensity =RDFParsing.getIntensityFromXML(polarity_result);
						sum += Float.valueOf(intensity);
						i++;
					}
					
					
					
				} catch (Exception e) {
					//System.out.println("Error : " + e.getMessage()); th:text="${tweetsPolaritie[index]}"
				}
			}
			sum /= i ;
			polarities.add(sum);
			
			
		}
		model.addAttribute("tweetsPolaritie", polarities);
		model.addAttribute("tweetUrls", tweetUrls);
		model.addAttribute("tweetImageEmotion",tweetImageEmotion);
		
		System.out.println("Size :  " + polarities.size());
		
		
		return "resultpage";
	}
	
	@RequestMapping("/emotion")
	public String getImageEmotion(){
		
		
		
		return null;
	}
	
	

}
