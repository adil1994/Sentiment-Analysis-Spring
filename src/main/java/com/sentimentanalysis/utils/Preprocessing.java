package com.sentimentanalysis.utils;

import java.util.List;

import com.vdurmont.emoji.EmojiParser;

public class Preprocessing {
	
	public static String emojisToText(String tweet){
		return EmojiParser.parseToAliases(tweet);
	}
	
	public static String removeSpecialCharacters(String tweet){
		return tweet.replaceAll("[^a-zA-Z0-9]+"," ");
	}

	public static String[] splitTweet(String tweet){
		return tweet.split(" ");
	}
	
	
	
}
