package com.sentimentanalysis.utils;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

public class RDFParsing {
	
	
	public static String banalizer(String banalizedText){
		String result = banalizedText.replace("\\n", "").replace("\t","");
		//System.out.println(result);
		return result;
	}

	
	public static String getPolarityFromXML(String polarityText){
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc;
		try {
			
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        InputSource is = new InputSource(new StringReader(banalizer(polarityText)));
        try {
			doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			return root.getFirstChild().getLastChild().getFirstChild().getTextContent();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "----";
    }
	

	
	
	
	public static String getIntensityFromXML(String IntensitText){
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc;
		try {
			
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        InputSource is = new InputSource(new StringReader(banalizer(IntensitText)));
        try {
			doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			return root.getFirstChild().getLastChild().getLastChild().getTextContent();
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "----";
    }
    
    
    
    public static void main(String[] args) {
    	System.out.println("Reading RDF Content .....");
    	//String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rdf:RDF xmlns:rdf=\"http://w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"http://sentic.net/api/en/concept/this/polarity\"><rdf:type rdf:resource=\"http://sentic.net/api/concept/polarity\"/><polarity xmlns=\"http://sentic.net\"><value xmlns=\"http://sentic.net\">negative</value><intensity xmlns=\"http://sentic.net\" rdf:datatype=\"http://w3.org/2001/XMLSchema#float\">-0.76</intensity></polarity></rdf:Description></rdf:RDF>";
    	String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rdf:RDF xmlns:rdf=\"http://w3.org/1999/02/22-rdf-syntax-ns#\"><rdf:Description rdf:about=\"http://sentic.net/api/en/concept/war/polarity\"><rdf:type rdf:resource=\"http://sentic.net/api/concept/polarity\"/><polarity xmlns=\"http://sentic.net\"><value xmlns=\"http://sentic.net\">negative</value><intensity xmlns=\"http://sentic.net\" rdf:datatype=\"http://w3.org/2001/XMLSchema#float\">-0.12</intensity></polarity></rdf:Description></rdf:RDF>";
    	
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        Document doc;
		try {
			
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        InputSource is = new InputSource(new StringReader(text.replace("\t", "")));
        try {
        	System.out.println("Building Document .....");
			doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			System.out.println(root.getAttribute("polarity"));
			System.out.println(root.getFirstChild().getLastChild().getFirstChild().getTextContent());
			
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
    


}
