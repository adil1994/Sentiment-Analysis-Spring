package com.sentimentanalysis.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ImageProcessing {
	
	/*
	public static void main(String[] args) {
		getImageUrlFromXML();
	}
	*/
	
	public static List<String> getImageUrlFromXML(String tweet){

		JSONObject rootObj = new JSONObject(tweet);
		JSONArray mediaObj = rootObj.getJSONObject("entities").getJSONArray("media");

		List<String> urls = new ArrayList<String>() ;
		
		if(mediaObj.length() > 0 )
		{
			for (int i = 0; i < mediaObj.length(); i++)
			{
				JSONObject obj = (JSONObject) mediaObj.get(i);
				urls.add(obj.getString("media_url"));
			}
		}
		return urls;
		
    }
	

}
