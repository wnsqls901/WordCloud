package edu.westga.cs1302.wordCloud.view;

import edu.westga.cs1302.wordCloud.model.WordData;
import edu.westga.cs1302.wordCloud.viewmodel.WordCloudGuiViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class WordCloudGuiCodeBehind {

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
    private MenuItem ListViewMenuItem;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button generateButton;

    private WordCloudGuiViewModel viewmodel;
  
    public WordCloudGuiCodeBehind() {
    		this.viewmodel = new WordCloudGuiViewModel();
    }
    
    @FXML
	private void initialize() {

		this.canvas.getGraphicsContext2D().setStroke(Color.BLACK);
		this.canvas.getGraphicsContext2D().setLineWidth(2);
		this.canvas.getGraphicsContext2D().strokeRect(0, 0, 445, 277);
    		this.bindToViewModel();

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

    }

    @FXML
    void handleRemoveOrUpdate(ActionEvent event) {

    }

}
