package com.sentimentanalysis.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.microsoft.projectoxford.emotion.EmotionServiceRestClient;
import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.rest.EmotionServiceException;
import com.sentimentanalysis.model.FaceEmotion;

@Service
public class EmotionImageService {
	
	public static void main(String[] args) {
		try {
			//getEmotion("https://upload.wikimedia.org/wikipedia/commons/2/27/Hillary_Clinton_official_Secretary_of_State_portrait_crop.jpg");
			System.out.println(getEmotion("http://media4.s-nbcnews.com/j/newscms/2015_33/1175671/150813-hillary-clinton-obama-mn-1657_b4d3a7caf6d1935dd5a772a8283bbea3.nbcnews-fp-1240-520.jpg"));
		} catch (EmotionServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	public static List<FaceEmotion> getEmotion(String imageURL) throws EmotionServiceException, IOException {
		
		List<FaceEmotion> results = new ArrayList<>();
		
		EmotionServiceClient client = null;
		if (client == null) {
            client = new EmotionServiceRestClient("cb0ac38a88c549f1823b08d40dd404d1 ");
        }

        Gson gson = new Gson();

        ByteArrayOutputStream output = new ByteArrayOutputStream();

//        File fi = new File("C:\\Users\\Public\\image.bmp");
//        byte[] fileContent = Files.readAllBytes(fi.toPath());
        
        
        BufferedImage img = ImageIO.read(new URL(imageURL));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

        long startTime = System.currentTimeMillis();

        List<RecognizeResult> result = null;
    
        result = client.recognizeImage(inputStream);

        String json = gson.toJson(result);
        //System.out.println("result ========================>>>>>>> " + json);
        
        
        JSONArray rootObj = new JSONArray(json);
        //System.out.println(" size ==========>>> "+rootObj.get(1).toString());
        for (int i = 0; i < rootObj.length(); i++)
		{
        	
        	FaceEmotion face = new FaceEmotion();
        	
			JSONObject obj = (JSONObject) rootObj.get(i);
			
			JSONObject scores = obj.getJSONObject("scores");
			
			//System.out.println(" size ==========>>> " + scores );
					
			
			face.setAnger(scores.getDouble("anger"));
			face.setContempt(scores.getDouble("contempt"));
			face.setDisgust(scores.getDouble("disgust"));
			face.setFear(scores.getDouble("fear"));
			face.setHappiness(scores.getDouble("happiness"));
			face.setNeutral(scores.getDouble("neutral"));
			face.setSadness(scores.getDouble("sadness"));
			face.setSurprise(scores.getDouble("surprise"));
			results.add(face);
		}
        

        return results;
    }
	
	
}
