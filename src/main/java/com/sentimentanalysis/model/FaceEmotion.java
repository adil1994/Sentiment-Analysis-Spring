package com.sentimentanalysis.model;


public class FaceEmotion {
	double anger;
	double contempt;
	double disgust;
	double fear;
	double happiness;
	double neutral;
	double sadness ;
	double surprise;
	
	public FaceEmotion() {
	}
	
	
	
	
	
	public FaceEmotion(double anger, double contempt, double disgust, double fear, double happiness, double neutral,
			double sadness, double surprise) {
		super();
		this.anger = anger;
		this.contempt = contempt;
		this.disgust = disgust;
		this.fear = fear;
		this.happiness = happiness;
		this.neutral = neutral;
		this.sadness = sadness;
		this.surprise = surprise;
	}





	public double getAnger() {
		return anger;
	}
	public void setAnger(double d) {
		this.anger = d;
	}

	public double getContempt() {
		return contempt;
	}

	public void setContempt(double contempt) {
		this.contempt = contempt;
	}

	public double getDisgust() {
		return disgust;
	}

	public void setDisgust(double disgust) {
		this.disgust = disgust;
	}

	public double getFear() {
		return fear;
	}

	public void setFear(double fear) {
		this.fear = fear;
	}

	public double getHappiness() {
		return happiness;
	}

	public void setHappiness(double happiness) {
		this.happiness = happiness;
	}

	public double getNeutral() {
		return neutral;
	}

	public void setNeutral(double neutral) {
		this.neutral = neutral;
	}

	public double getSadness() {
		return sadness;
	}

	public void setSadness(double sadness) {
		this.sadness = sadness;
	}

	public double getSurprise() {
		return surprise;
	}

	public void setSurprise(double surprise) {
		this.surprise = surprise;
	}
	
	
	
	
}
