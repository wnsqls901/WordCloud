package edu.westga.cs1302.wordCloud.model;

/**
 * The Class WordData.
 * @author Junbin Kwon
 * @version 2018-05-01
 */
public class WordData {
	
	/** The data. */
	private String data;
	
	/** The frequency. */
	private int frequency;
	
	/**
	 * Instantiates a new word data.
	 *
	 * @param word the word
	 * @param frequency the frequency
	 */
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
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return this.data;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String data) {
		if (data == null) {
			throw new IllegalArgumentException("word should not be null.");
		}
		if (data.isEmpty()) {
			throw new IllegalArgumentException("word should not be empty.");
		}
		this.data = data;
	}
	
	/**
	 * Gets the frequency.
	 *
	 * @return the frequency
	 */
	public int getFrequency() {

		if (this.frequency <= 0) {
			throw new IllegalArgumentException("frequency always should not be negative value.");
		}
		return this.frequency;
	}
	
	/**
	 * Sets the frequency.
	 *
	 * @param frequency the new frequency
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * Display word and frequency 
	 * @return word and frequency
	 */
	public String toString() {
		return this.getData().toLowerCase() + ": " + this.frequency;
	}

	
}
