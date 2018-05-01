package edu.westga.cs1302.wordCloud.viewmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.model.WordManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * The Class WordCloudGuiViewModel.
 * @author Junbin Kwon
 * @version 2018-05-01
 */
public class WordCloudGuiViewModel {
	
	/** The word property. */
	private StringProperty wordProperty;
	
	/** The frequency property. */
	private StringProperty frequencyProperty;
	
	/** The words property. */
	private ListProperty<WordData> wordsProperty;
	
	/** The display property. */
	private StringProperty displayProperty;
	
	/** The select property. */
	private BooleanProperty selectProperty;
	
	/** The manage. */
	private WordManager manage;
	
	/** The colors. */
	private ArrayList<Color> colors;
	
	/** The max heights. */
	private ArrayList<Double> maxHeights;
	
	/** The max width. */
	private ArrayList<Double> maxWidth;
	
	/** The font height. */
	private double fontHeight;
	
	/**
	 * Instantiates a new word cloud gui view model.
	 */
	public WordCloudGuiViewModel() {
		
		this.fontHeight = 0;
		this.maxHeights = new ArrayList<Double>();
		this.maxWidth = new ArrayList<Double>();
		this.wordProperty = new SimpleStringProperty();
		this.frequencyProperty = new SimpleStringProperty();
		this.displayProperty = new SimpleStringProperty();

		this.selectProperty = new SimpleBooleanProperty();
		this.selectProperty.setValue(false);
		this.manage = new WordManager();
		this.wordsProperty = new SimpleListProperty<WordData>(FXCollections.observableArrayList(this.manage));
		this.colors = new ArrayList<Color>();

		for (int index = 0; index < 20; index++) {
			Random rand = new Random();
			Color newColor = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
			this.colors.add(index, newColor);
		}
		
	}

	/**
	 * Gets the select property.
	 *
	 * @return the select property
	 */
	public BooleanProperty getSelectProperty() {
		
		return this.selectProperty;
	}
	
	/**
	 * Gets the word property.
	 *
	 * @return the word property
	 */
	public StringProperty getWordProperty() {
		return this.wordProperty;
	}

	/**
	 * Gets the frequency property.
	 *
	 * @return the frequency property
	 */
	public StringProperty getFrequencyProperty() {
		return this.frequencyProperty;
	}

	/**
	 * Gets the words property.
	 *
	 * @return the words property
	 */
	public ListProperty<WordData> getWordsProperty() {
		return this.wordsProperty;
	}

	/**
	 * Gets the display property.
	 *
	 * @return the display property
	 */
	public StringProperty getDisplayProperty() {
		return this.displayProperty;
	}
	
	/**
	 * Adds the word.
	 */
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
				this.addData(data);
			}
		}
		this.wordProperty.set("");
		this.frequencyProperty.set("");
	}

	private void addData(WordData data) {
		if (this.manage.add(data)) {

			this.updateDisplay();
			this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
		}
	}
	
	/**
	 * Update word.
	 */
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
	
	/**
	 * Removes the word.
	 *
	 * @return true, if successful
	 */
	public boolean removeWord() {
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
		return true;
	}
	
	/**
	 * Update display.
	 */
	public void updateDisplay() {
		String display = "";
		for (WordData word : this.manage) {
			display += word + System.lineSeparator();
		}
		this.displayProperty.set(display);
	}
	
	/**
	 * Generate words.
	 *
	 * @param gc the gc
	 */
	public void generateWords(GraphicsContext gc) {
		this.createCanvas(gc);
		this.checkHeightBetweenLines(gc);
		double x = 0;
		double size;
		int index = 0;
		int count = 1;
		double y = this.maxHeights.get(0);
		for (WordData word : this.manage) {
			if (index == this.colors.size() - 1) {
				index = 0;
			}
			size = 10;
			Text text = new Text(word.getData());
			this.fontSetting(gc, size, index, word, text);
			if (x + text.getLayoutBounds().getWidth() >= gc.getCanvas().getLayoutBounds().getMaxX()) {
				x = 0;
				y += this.maxHeights.get(count);
				count++;
			}
			index++;
			gc.fillText(text.getText().toLowerCase(), x, y);
			x += text.getLayoutBounds().getWidth() + 3;
		}
		this.maxHeights = new ArrayList<Double>();
	}

	private void createCanvas(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
		gc.strokeRect(0, 0, 445, 277);
	}
	
	/**
	 * Check height between lines.
	 *
	 * @param gc the gc
	 */
	private void checkHeightBetweenLines(GraphicsContext gc) {
		double x = 0;
		double maxHeight = 0;
		int index = 0;
		double size;
		for (WordData word : this.manage) {
			
			size = 10;
			Text text = new Text(word.getData());
			this.fontSetting(gc, size, index, word, text);
			if (x + text.getLayoutBounds().getWidth() >= gc.getCanvas().getLayoutBounds().getMaxX()) {
				x = 0;
				this.maxHeights.add(index, maxHeight);
				maxHeight = 0;
				index++;
				
			}

			if (maxHeight <= text.getFont().getSize()) {
				maxHeight = text.getFont().getSize();
			}
			x += text.getLayoutBounds().getWidth() + 3;
		}
		this.maxHeights.add(index, maxHeight);
		
	}
	
	/**
	 * Adds the words from file.
	 *
	 * @param name the name
	 */
	public void addWordsFromFile(File name) {
		this.manage = new WordManager();
		try (Scanner in = new Scanner(name)) {
			while (in.hasNextLine()) {
				String word = in.next();
				if (word.matches("^[a-zA-Z-]*$") && word.length() >= 3) {	
					if (!this.manage.containsWord(word)) {
						WordData data = new WordData(word, 1);
						this.manage.add(data);
					} else if (this.manage.containsWord(word)) {
						this.manage.getFrequencyByWord(word).setFrequency(this.manage.getFrequencyByWord(word).getFrequency() + 1);
					}
				}
				this.updateDisplay();
				this.wordsProperty.set(FXCollections.observableArrayList(this.manage));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save words to file.
	 *
	 * @param selectedFile the selected file
	 */
	public void saveWordsToFile(File selectedFile) {
		try (FileWriter fileWriter = new FileWriter(selectedFile)) {
			for (WordData data : this.manage) {
	            fileWriter.write(data.toString() + System.lineSeparator());
			}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
	}
	
	/**
	 * Change color.
	 *
	 * @param number the number
	 */
	public void changeColor(int number) {

		this.colors = new ArrayList<Color>();
	
		if (number == 1) {
			for (int index = 0; index < 20; index++) {
				Random rand = new Random();
				Color newColor = Color.rgb(rand.nextInt(256), 0, 0);
				this.colors.add(index, newColor);
			}
		} else if (number == 2) {
			for (int index = 0; index < 20; index++) {
				Random rand = new Random();
				Color newColor = Color.rgb(0, rand.nextInt(256), 0);
				this.colors.add(index, newColor);
			}
		} else if (number == 3) {
			for (int index = 0; index < 20; index++) {
				Random rand = new Random();
				Color newColor = Color.rgb(0, 0, rand.nextInt(256));
				this.colors.add(index, newColor);
			}
		}
	}

	/**
	 * Sort words.
	 *
	 * @param number the number
	 */
	public void sortWords(int number) {
		if (number == 0) {
			this.wordsProperty.set(FXCollections.observableArrayList(this.manage.sortDefault().values()));
			this.updateDisplay();
			
		}
		if (number == 1) {
			this.wordsProperty.set(FXCollections.observableArrayList(this.manage.sortByFrequency().values()));
			this.updateDisplay();
		}
		if (number == 2) {
			this.wordsProperty.set(FXCollections.observableArrayList(this.manage.sortMixed().values()));
			this.updateDisplay();
		}
		
	}

	/**
	 * Check selection.
	 *
	 * @param gc the gc
	 */
	public void checkSelection(GraphicsContext gc) {
		if (this.selectProperty.getValue()) {
			this.createCanvas(gc);
			this.checkHeightBetweenLines(gc);
			this.findRatioOfCenterPoint(gc);
			double center = (gc.getCanvas().getLayoutBounds().getWidth() / 2);
			double x = center - (this.maxWidth.get(0) / 2);
			this.checkedGenerate(gc, center, x);
			this.maxHeights = new ArrayList<Double>();
			this.maxWidth = new ArrayList<Double>();
			this.fontHeight = 0;
		}
		this.selectProperty.setValue(false);
		
	}

	private void checkedGenerate(GraphicsContext gc, double center, double x) {
		double size;
		int index = 0;
		int count = 1;
		int widthCount = 1;
		int wordMax = 1;
		int wordCount = 0;
		double y = (this.fontHeight / 2);
		for (WordData word : this.manage) {		
			if (index == this.colors.size() - 1) {
				index = 0;
			}
			size = 10;
			Text text = new Text(word.getData());
			this.fontSetting(gc, size, index, word, text);
			if (wordCount == wordMax 
					|| x + text.getLayoutBounds().getWidth() >= gc.getCanvas().getLayoutBounds().getMaxX()
					|| x <= gc.getCanvas().getLayoutBounds().getMinX()) {
				x = center - (this.maxWidth.get(widthCount) / 2);
				y += this.maxHeights.get(count);
				wordMax++;
				wordCount = 1;
				widthCount++;
			} else {
				wordCount++;
			}
			index++;
			gc.fillText(text.getText().toLowerCase(), x, y);
			x += text.getLayoutBounds().getWidth() + 3;
		}
	}

	private void fontSetting(GraphicsContext gc, double size, int index, WordData word, Text text) {
		if (word.getFrequency() >= 5) {
			size = 50;
		} else {
			size *= word.getFrequency();
		}
		Font font = new Font(size);
		text.setFont(font);
		gc.setFont(text.getFont());
		gc.setFill(this.colors.get(index));
	}
	
	/**
	 * Find ratio of center point.
	 *
	 * @param gc the gc
	 */
	private void findRatioOfCenterPoint(GraphicsContext gc) {
		
		double size;
		int index = 0;
		int wordMax = 1;
		int wordCount = 0;
		double fontSize = 0;
		for (WordData word : this.manage) {

			size = 10;
			Text text = new Text(word.getData());
		
			if (index == this.colors.size() - 1) {
				index = 0;
			}
			this.fontSetting(gc, size, index, word, text);
			if (wordCount == wordMax) {
				this.maxWidth.add(fontSize);
				wordMax++;
				wordCount = 1;
				fontSize = 0;

				this.fontHeight += text.getLayoutBounds().getHeight();
			} else {
				wordCount++;
			}
			index++;
			fontSize += text.getLayoutBounds().getWidth();
			
		}
		this.maxWidth.add(fontSize);
	}
	
}
