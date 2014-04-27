package de.muc.tvd.fs.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

public class Controller implements Initializable {

	@FXML private Label folderLabel;
	@FXML private TextField extField;
	@FXML private TextField searchField;
	
	private File searchDir;
	
	@FXML
	protected void handleFolderButton(ActionEvent event) {
		DirectoryChooser dc = new DirectoryChooser();
		Window window = folderLabel.getScene().getWindow();
		searchDir = dc.showDialog(window);
		if (searchDir != null) {
			folderLabel.setText(searchDir.toString());
		}
	}
	
	@FXML
	protected void handleSearchButton(ActionEvent event) {
		if (searchDir == null) {
			return;
		}
		// Tokenize search string
		Collection<String> searchTokens = Arrays.asList(searchField.getText().split("\\*"));
		// Remove leading '.' if neccessary
		String fileExt = extField.getText();
		if (fileExt.charAt(0) == '.') {
			fileExt = fileExt.substring(1);
		}
		// Find all files with extension
		String[] e = {fileExt};
		Collection<File> files = FileUtils.listFiles(searchDir, e, true);
		try {
			Map<File, String> map = findLines(files, searchTokens);
			map.forEach((f,s)->{
				System.out.println(f.getName()+"\t\t"+s);
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private Map<File, String> findLines(Collection<File> files, Collection<String> tokens) throws IOException {
		Map<File, String> result = new HashMap<>();
		for (File f : files) {
			FileUtils.readLines(f).forEach(line -> {
				if (lineContentsAllTokens(line, tokens)) {
					result.put(f, line);
				}
			});
		}

		return result;
	}
	
	private boolean lineContentsAllTokens(String line, Collection<String> tokens) {
		for (String token : tokens) {
			if (!line.contains(token)) {
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}

}
