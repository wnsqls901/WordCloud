package edu.westga.cs1302.wordCloud;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


/**
 * The Class WordCloudGuiApplication.
 * @author Junbin Kwon
 * @version 2018-05-01
 */
public class WordCloudGuiApplication extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("view/WordCloudGui.fxml"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			primaryStage.setTitle("A Word Game by Junbin Kwon");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
