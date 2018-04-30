package edu.westga.cs1302.wordCloud.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.model.WordManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
				
				if (this.manage.add(data)) {
	
					this.updateDisplay();
					this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
				}
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
	public void generateWords(GraphicsContext gc) {
		
	
		
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
		gc.strokeRect(0, 0, 445, 277);
		
		int x = 0;
		int y = 50;
		int size;
		int color = 0;
		Paint colorName = null;
		for (WordData word : this.manage) {
			switch (color) {
				case 0:
					colorName = Color.INDIANRED;
					break;
				case 1:
					colorName = Color.LIGHTSEAGREEN;
					break;
				case 2:
					colorName = Color.BLUEVIOLET;
					break;
				case 3:
					colorName = Color.YELLOWGREEN;
					break;
				case 4: 
					colorName = Color.SADDLEBROWN;
					break;
				
			}
			
			size = 10;
			Text text = new Text(word.getData());
			if (word.getFrequency() >= 5) {
				size = 50;
			} else {
				size *= word.getFrequency();
			}

		
			
			Font font = new Font(size);
			text.setFont(font);
			gc.setFont(text.getFont());
			gc.setFill(colorName);
			if (x + text.getLayoutBounds().getWidth() >= gc.getCanvas().getLayoutBounds().getMaxX()) {
				x = 0;
				y += 50;
			}
			
			gc.fillText(text.getText().toLowerCase(), x, y);

			x += text.getLayoutBounds().getWidth() + 3;

			//System.out.println(x + " "+ gc.getCanvas().getLayoutBounds().getMaxX() + " " + 31);
		
			
			if (color >= 4) {
				color = 0;
			} else {
				color++;
			}
		
		}	
	}
	public void addWordsFromFile(File name) {
		this.manage = new WordManager();
		try (Scanner in = new Scanner(name)) {
			while (in.hasNextLine()) {
				String word = in.next();
				if (!this.manage.containsWord(word)) {
					WordData data = new WordData(word, 1);
					this.manage.add(data);
				} else if (this.manage.containsWord(word)) {
					this.manage.getFrequencyByWord(word).setFrequency(this.manage.getFrequencyByWord(word).getFrequency() + 1);
				}

				this.updateDisplay();
				this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
