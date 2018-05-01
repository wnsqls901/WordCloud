package edu.westga.cs1302.wordCloud.model;

public class WordData {
	private String data;
	private int frequency;
	
	public WordData(String word, int frequency) {
		if (word == null) {
			throw new IllegalArgumentException("word should not be null");
		}
		if (word.isEmpty()) {
			throw new IllegalArgumentException("word should not be empty");
		}
		if (frequency <= 0) {
			throw new IllegalArgumentException("frequency always should not be negative value");
		}
		this.data = word;
		this.frequency = frequency;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		if (data == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		if (data.isEmpty()) {
			throw new IllegalArgumentException("word should not be empty.");
		}
		this.data = data;
	}
	public int getFrequency() {

		if (frequency <= 0) {
			throw new IllegalArgumentException("frequency always should not be negative value.");
		}
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public String toString() {
		return this.getData().toLowerCase() + ": " + this.frequency;
	}

	
}
