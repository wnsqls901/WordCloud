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
import javafx.scene.control.CheckBox;
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


/**
 * The Class WordCloudGuiCodeBehind.
 * @author Junbin Kwon
 * @version 2018-05-01
 */
public class WordCloudGuiCodeBehind {

    /** The incrementing list. */
    @FXML
    private MenuItem incrementingList;

    /** The decrementing list. */
    @FXML
    private MenuItem decrementingList;
    
    /** The frequency error label. */
    @FXML
    private Label frequencyErrorLabel;
    
    /** The word error label. */
    @FXML
    private Label wordErrorLabel;
    
	/** The gui pane. */
	@FXML
	private AnchorPane guiPane;

    /** The file save menu item. */
    @FXML
    private MenuItem fileSaveMenuItem;
	
    /** The file load menu item. */
    @FXML
    private MenuItem fileLoadMenuItem;

    /** The word text field. */
    @FXML
    private TextField wordTextField;

    /** The frequency text field. */
    @FXML
    private TextField frequencyTextField;

    /** The canvas. */
    @FXML
    private Canvas canvas;

    /** The word list view. */
    @FXML
    private ListView<WordData> wordListView;

    /** The remove list item. */
    @FXML
    private MenuItem removeListItem;

    /** The add button. */
    @FXML
    private Button addButton;

    /** The update button. */
    @FXML
    private Button updateButton;

    /** The remove button. */
    @FXML
    private Button removeButton;

    /** The generate button. */
    @FXML
    private Button generateButton;
    
    /** The color combo box. */
    @FXML
    private ComboBox<String> colorComboBox;

    /** The sort combo box. */
    @FXML
    private ComboBox<String> sortComboBox;

    /** The check box. */
    @FXML
    private CheckBox checkBox;
    
    /** The list. */
    private ObservableList<String> list = FXCollections.observableArrayList("Hot", "Forest", "Cold", "Random");

    /** The sort list. */
    private ObservableList<String> sortList = FXCollections.observableArrayList("Default", "Frequency", "Frequency-mix");
    
    /** The viewmodel. */
    private WordCloudGuiViewModel viewmodel;
  
    /**
     * Instantiates a new word cloud gui code behind.
     */
    public WordCloudGuiCodeBehind() {
    	this.viewmodel = new WordCloudGuiViewModel();
    }
    
    /**
     * Initialize.
     */
    @FXML
	private void initialize() {
    	this.colorComboBox.setItems(this.list);
    	this.sortComboBox.setItems(this.sortList);
		WordCloudGuiCodeBehind.this.frequencyErrorLabel.setVisible(false);		
		WordCloudGuiCodeBehind.this.wordErrorLabel.setVisible(false);
		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
    	this.bindToViewModel();
    	this.setupListenersForValidation();
    	this.bindButtonsDisableProperty();
    	this.setupListenerForListView();
    }

	/**
	 * Setup listener for list view.
	 */
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

    /**
     * Bind to view model.
     */
    private void bindToViewModel() {
		this.wordTextField.textProperty().bindBidirectional(this.viewmodel.getWordProperty());
		this.frequencyTextField.textProperty().bindBidirectional(this.viewmodel.getFrequencyProperty());
		this.wordListView.itemsProperty().bindBidirectional(this.viewmodel.getWordsProperty());
		BooleanBinding menuItemEnabled = Bindings
				.isNull(this.wordListView.getSelectionModel().selectedItemProperty());
		this.removeListItem.disableProperty().bind(menuItemEnabled);
		this.incrementingList.disableProperty().bind(menuItemEnabled);
		this.decrementingList.disableProperty().bind(menuItemEnabled);
		this.checkBox.selectedProperty().bindBidirectional(this.viewmodel.getSelectProperty());

	}

    /**
     * Handle add.
     */
    @FXML
    void handleAdd() {
		this.viewmodel.addWord();
    }

    /**
     * Handle remove.
     *
     * @param event the event
     */
    @FXML
    void handleRemove(ActionEvent event) {
    	this.viewmodel.removeWord();
    }
    
    /**
     * Handle update.
     *
     * @param event the event
     */
    @FXML
    void handleUpdate(ActionEvent event) {
    	this.viewmodel.updateWord();
    }
    
    /**
     * Handle generate.
     *
     * @param event the event
     */
    @FXML
    void handleGenerate(ActionEvent event) {
    	try {
    		this.viewmodel.generateWords(this.canvas.getGraphicsContext2D());
    	} catch (IndexOutOfBoundsException e) {
    		
    	}
    }
    
    /**
     * Handle file load.
     *
     * @param event the event
     */
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

    /**
     * Handle file save.
     *
     * @param event the event
     */
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
    	    if (selectedFile != null) {
    	        this.viewmodel.saveWordsToFile(selectedFile);
    	    }
	
    	} 
    }
	
	/**
	 * Handle help about.
	 *
	 * @param event the event
	 */
	@FXML
	void handleHelpAbout(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Word Cloud");
		alert.setHeaderText("Word Cloud Manager by Junbin Kwon");
		alert.setContentText("Version 1.0");

		alert.showAndWait();
	}
	private void bindButtonsDisableProperty() {
		
		this.addButton.disableProperty().bind(this.wordTextField.textProperty().isEmpty()
				.or(this.frequencyTextField.textProperty().isEmpty()));
		this.updateButton.disableProperty().bind(this.wordTextField.textProperty().isEmpty()
				.or(this.frequencyTextField.textProperty().isEmpty()));
		this.removeButton.disableProperty().bind(this.wordTextField.textProperty().isEmpty()
				.or(this.viewmodel.isEmptyWordsProperty()));
	}

    
	/**
	 * Setup listeners for validation.
	 */
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
	
	/**
	 * Change color.
	 *
	 * @param event the event
	 */
	@FXML
	void changeColor(ActionEvent event) {
		this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth(),
				this.canvas.getGraphicsContext2D().getCanvas().getHeight());
		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		if (this.colorComboBox.getValue().toString().equals("Hot")) {
			this.viewmodel.changeColor(1);
		} else if (this.colorComboBox.getValue().toString().equals("Forest")) {
			this.viewmodel.changeColor(2);
		} else if (this.colorComboBox.getValue().toString().equals("Cold")) {
			this.viewmodel.changeColor(3);
		} else if (this.colorComboBox.getValue().toString().equals("Random")) {
			this.viewmodel.changeColor(4);
		}
	}
	
	/**
	 * Handle incrementing.
	 *
	 * @param event the event
	 */
	@FXML
    void handleIncrementing(ActionEvent event) {
		WordData selectedWord = this.wordListView.getSelectionModel().getSelectedItem();
		if (selectedWord != null) {

			selectedWord.setFrequency(selectedWord.getFrequency() + 1);

			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth(),
					this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}
    }

	/**
	 * Handle decrementing.
	 *
	 * @param event the event
	 */
	@FXML
    void handleDecrementing(ActionEvent event) {
		WordData selectedWord = this.wordListView.getSelectionModel().getSelectedItem();
		if (selectedWord != null) {
			if (selectedWord.getFrequency() != 1) {

				selectedWord.setFrequency(selectedWord.getFrequency() - 1);
			}
			
			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth(),
					this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}
    }
	
	/**
	 * Handle list remove.
	 *
	 * @param event the event
	 */
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

			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth(),
					this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
		}

    }
	
	/**
	 * Change sort.
	 *
	 * @param event the event
	 */
	@FXML
	void changeSort(ActionEvent event) {
		try {
			this.canvas.getGraphicsContext2D().clearRect(0, 0, this.canvas.getGraphicsContext2D().getCanvas().getWidth(),
					this.canvas.getGraphicsContext2D().getCanvas().getHeight());
			this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
			this.canvas.getGraphicsContext2D().setLineWidth(2);
			this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
			if (this.sortComboBox.getValue().toString().equals("Default")) {
				this.viewmodel.sortWords(0);
			} else if (this.sortComboBox.getValue().toString().equals("Frequency")) {
				this.viewmodel.sortWords(1);
			} else if (this.sortComboBox.getValue().toString().equals("Frequency-mix")) {
				this.viewmodel.sortWords(2);
			}
		} catch (IndexOutOfBoundsException e) {
			
		}
	}

    /**
     * Handle check selection.
     *
     * @param event the event
     */
    @FXML
    void handleCheckSelection(ActionEvent event) {
    }

}
