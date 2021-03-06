package de.muc.tvd.fs.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("MainView.fxml"));
		
		Scene scene  = new Scene(root, 300, 200);
		scene.getStylesheets().add(getClass().getResource("MainView.css").toExternalForm());
		
		primaryStage.setTitle("FileSearcher");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
