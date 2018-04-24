package edu.westga.cs1302.wordCloud.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

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
    private ListView<?> wordListView;

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

    @FXML
    void handleFileLoad(ActionEvent event) {

    }

    @FXML
    void handleRemoveOrUpdate(ActionEvent event) {

    }

}
