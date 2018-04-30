package edu.westga.cs1302.wordCloud.viewmodel;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.model.WordManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
		WordData data = null;
		int frequency;
		String word;
		if (this.wordProperty.getValue() != null && this.frequencyProperty.getValue() != null
				&& !this.wordProperty.getValue().isEmpty() && !this.frequencyProperty.getValue().isEmpty()) {
			word = this.wordProperty.getValue();
			frequency = Integer.parseInt(this.frequencyProperty.get());
	
			if (this.manage.containsWord(word)) {
	
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Already exist");
				alert.setHeaderText("Error");
				alert.setContentText("You cannot add the word since it already exists");
	
				alert.showAndWait();
			} else {
				data = new WordData(word, frequency);
			}
			if (this.manage.add(data)) {
				this.updateDisplay();
				this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			}
		}
		this.wordProperty.set("");
		this.frequencyProperty.set("");
	}
	public void updateWord() {
		if (this.wordProperty.getValue() != null && this.frequencyProperty.getValue() != null
				&& !this.wordProperty.getValue().isEmpty() && !this.frequencyProperty.getValue().isEmpty()) {
			String word = this.wordProperty.getValue();
			int frequency = Integer.parseInt(this.frequencyProperty.get());
			if (this.manage.containsWord(word)) {
				this.manage.getFrequencyByWord(word).setFrequency(frequency);
				this.updateDisplay();
				this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			} else {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Word doesn't exist");
				alert.setHeaderText("Error");
				alert.setContentText("You cannot update the word since it doesn't exists");
	
				alert.showAndWait();
			}
		}
		this.wordProperty.set("");
		this.frequencyProperty.set("");
	}
	public void removeWord() {
		if (this.wordProperty.getValue() != null && !this.wordProperty.getValue().isEmpty()) {
			String word = this.wordProperty.getValue();
			if (this.manage.containsWord(word)) {
				this.manage.removeByWord(word);
				this.updateDisplay();
				this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			}
		}

		this.wordProperty.set("");
		this.frequencyProperty.set("");
	}
	public void updateDisplay() {
		String display = "";
		for (WordData word : this.manage) {
			display += word + System.lineSeparator();
		}
		this.displayProperty.set(display);
	}
}
