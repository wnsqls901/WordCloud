package edu.westga.cs1302.wordCloud.view;

import java.io.File;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.viewmodel.WordCloudGuiViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class WordCloudGuiCodeBehind {

    @FXML
    private MenuItem incrementingList;

    @FXML
    private MenuItem decrementingList;
    
    @FXML
    private Label frequencyErrorLabel;
    
    @FXML
    private Label wordErrorLabel;
    
	@FXML
	private AnchorPane guiPane;

    @FXML
    private MenuItem fileSaveMenuItem;
	
    @FXML
    private MenuItem fileLoadMenuItem;

    @FXML
    private TextField wordTextField;

    @FXML
    private TextField frequencyTextField;

    @FXML
    private Canvas canvas;

    @FXML
    private ListView<WordData> wordListView;

    @FXML
    private MenuItem removeListItem;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button generateButton;
    
    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private ComboBox<String> sortComboBox;
    
    private ObservableList<String> list = FXCollections.observableArrayList("Hot","Forest","Cold");

    private ObservableList<String> sortList = FXCollections.observableArrayList("Default","Frequency","Frequency-mix");
    
    private WordCloudGuiViewModel viewmodel;
  
    public WordCloudGuiCodeBehind() {
    		this.viewmodel = new WordCloudGuiViewModel();
    }
    
    @FXML
	private void initialize() {
    		this.colorComboBox.setItems(list);
    		this.sortComboBox.setItems(sortList);
		WordCloudGuiCodeBehind.this.frequencyErrorLabel.setVisible(false);		
		WordCloudGuiCodeBehind.this.wordErrorLabel.setVisible(false);
		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
    		this.bindToViewModel();
    		this.setupListenersForValidation();

    		this.setupListenerForListView();
    }

	private void setupListenerForListView() {
		this.wordListView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldWord, newWord) -> {
					if (newWord != null) {
						this.wordTextField.textProperty().set(newWord.getData());
						Integer count = newWord.getFrequency();
						this.frequencyTextField.textProperty().set(count.toString());
					}
				});
	}

    private void bindToViewModel() {
		this.wordTextField.textProperty().bindBidirectional(this.viewmodel.getWordProperty());
		this.frequencyTextField.textProperty().bindBidirectional(this.viewmodel.getFrequencyProperty());
		this.wordListView.itemsProperty().bindBidirectional(this.viewmodel.getWordsProperty());
		BooleanBinding MenuItemEnabled = Bindings
				.isNull(this.wordListView.getSelectionModel().selectedItemProperty());
		this.removeListItem.disableProperty().bind(MenuItemEnabled);
		this.incrementingList.disableProperty().bind(MenuItemEnabled);
		this.decrementingList.disableProperty().bind(MenuItemEnabled);

	}

    @FXML
    void handleAdd() {
		this.viewmodel.addWord();
    }

    @FXML
    void handleRemove(ActionEvent event) {
    		this.viewmodel.removeWord();
    }
    @FXML
    void handleUpdate(ActionEvent event) {
    		this.viewmodel.updateWord();
    }
    @FXML
    void handleGenerate(ActionEvent event) {
    		this.viewmodel.generateWords(canvas.getGraphicsContext2D());
    }
    
    @FXML
	void handleFileLoad(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load Image File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));

		Stage stage = (Stage) this.guiPane.getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			this.viewmodel.addWordsFromFile(selectedFile);
		}

	}

    @FXML
	void handleFileSave(ActionEvent event) {
    		if (this.viewmodel.getWordsProperty().isEmpty()) {
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("You cannot save");
    			alert.setHeaderText("There is no words in word bank");
    			alert.showAndWait();
    		} else {
    	   		FileChooser fileChooser = new FileChooser();
    	        fileChooser.setTitle("Save Image");
    	         
    	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    	        fileChooser.getExtensionFilters().add(extFilter);

    	 		Stage stage = (Stage) this.guiPane.getScene().getWindow();
    	        File selectedFile = fileChooser.showSaveDialog(stage);
    	        if(selectedFile != null){
    	           this.viewmodel.saveWordsToFile(selectedFile);
    	        }
	
    		}        
    }
	@FXML
	void handleHelpAbout(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Word Cloud");
		alert.setHeaderText("Word Cloud Manager by Junbin Kwon");
		alert.setContentText("Version 1.0");

		alert.showAndWait();
	}
    
	private void setupListenersForValidation() {
		this.wordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				WordCloudGuiCodeBehind.this.wordTextField.setText(newValue.toLowerCase());
				
				if (!newValue.matches("[a-zA-Z-]*")) {
					WordCloudGuiCodeBehind.this.wordTextField.setText(oldValue);
					WordCloudGuiCodeBehind.this.wordErrorLabel.setVisible(true);
				} else {
					WordCloudGuiCodeBehind.this.wordErrorLabel.setVisible(false);
				}
			}
		});
		this.frequencyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if (!newValue.matches("[0-9]*")) {
					WordCloudGuiCodeBehind.this.frequencyTextField.setText(oldValue);
					WordCloudGuiCodeBehind.this.frequencyErrorLabel.setVisible(true);
				} else {
					WordCloudGuiCodeBehind.this.frequencyErrorLabel.setVisible(false);						
				}
			}
		});
	}
	@FXML
	void changeColor(ActionEvent event) {
		this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth()
				, this.canvas.getGraphicsContext2D().getCanvas().getHeight());
		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		if (this.colorComboBox.getValue().toString().equals("Hot")) {
			this.viewmodel.changeColor(1);
		}else if (this.colorComboBox.getValue().toString().equals("Forest")) {
			this.viewmodel.changeColor(2);
		}else if (this.colorComboBox.getValue().toString().equals("Cold")) {
			this.viewmodel.changeColor(3);
		}
	}
	@FXML
    void handleIncrementing(ActionEvent event) {
		WordData selectedWord = this.wordListView.getSelectionModel().getSelectedItem();
		if (selectedWord != null) {

			selectedWord.setFrequency(selectedWord.getFrequency()+1);

			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth()
					, this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}
    }

	@FXML
    void handleDecrementing(ActionEvent event) {
		WordData selectedWord = this.wordListView.getSelectionModel().getSelectedItem();
		if (selectedWord != null) {
			if (selectedWord.getFrequency() != 1) {

				selectedWord.setFrequency(selectedWord.getFrequency()-1);
			}
			
			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth()
					, this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}
    }
	@FXML
    void handleListRemove(ActionEvent event) {
    		WordData selectedWord = this.wordListView.getSelectionModel().getSelectedItem();
		if (selectedWord != null) {
			try {
				if (!this.viewmodel.removeWord()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("About Remove");
					alert.setHeaderText("There is no word");

					alert.showAndWait();
				}
			} catch (IllegalArgumentException | NullPointerException ex) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("About Remove");
				alert.setHeaderText("Can't remove");

				alert.showAndWait();
			}

			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth()
					, this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}

    }
	@FXML
	void changeSort(ActionEvent event) {
		this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth()
				, this.canvas.getGraphicsContext2D().getCanvas().getHeight());
		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		if (this.sortComboBox.getValue().toString().equals("Default")) {
			this.viewmodel.sortWords(0);
		}
		if (this.sortComboBox.getValue().toString().equals("Frequency")) {
			this.viewmodel.sortWords(1);
		}
		if (this.sortComboBox.getValue().toString().equals("Frequency-mix")) {
			this.viewmodel.sortWords(2);
		}
	}

}
