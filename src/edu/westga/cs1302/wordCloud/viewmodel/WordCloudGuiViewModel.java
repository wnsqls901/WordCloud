package edu.westga.cs1302.wordCloud.viewmodel;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.model.WordManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class WordCloudGuiViewModel {
	
	private StringProperty wordProperty;
	private StringProperty frequencyProperty;
	private ListProperty<WordData> wordsProperty;
	private StringProperty displayProperty;
	private WordManager manage;
	
	public WordCloudGuiViewModel() {
		this.wordProperty = new SimpleStringProperty();
		this.frequencyProperty = new SimpleStringProperty();
		this.displayProperty = new SimpleStringProperty();

		this.manage = new WordManager();
		this.wordsProperty = new SimpleListProperty<WordData>(FXCollections.observableArrayList(this.manage));
		
	}

	public StringProperty getWordProperty() {
		return this.wordProperty;
	}

	public StringProperty getFrequencyProperty() {
		return this.frequencyProperty;
	}

	public ListProperty<WordData> getWordsProperty() {
		return this.wordsProperty;
	}

	public StringProperty getDisplayProperty() {
		return this.displayProperty;
	}
	
	public void addWord() {

		String word = this.wordProperty.getValue();
		int frequency = Integer.parseInt(this.frequencyProperty.get());

		WordData data = null;
		data = new WordData(word, frequency);
		
		if (this.manage.add(data)) {
			this.wordProperty.set("");
			this.frequencyProperty.set("");
			this.updateDisplay();
			this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			
		}
	}
	
	public void updateDisplay() {
		String display = "";
		for (WordData word : this.manage) {
			display += word + System.lineSeparator();
		}
		this.displayProperty.set(display);
	}
}
