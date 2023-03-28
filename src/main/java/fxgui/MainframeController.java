package fxgui;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import gen.Util;
import genuf2.UF2BufferFileChain;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainframeController extends MainframeControllerValues {

	private final GridPane rootNode;
	private final Stage stage;

	public MainframeController(final Stage stage) {
		this.stage = stage;
		rootNode = new MainframeLoader(this);

		//////////////////////////////
		/////////// Action Connections
//		transmitSpeed.addListener(this::onChangedSpeed);
//		onActionPropertyStartButton.setValue(this::onStart);
//		onActionPropertyStopButton.setValue(this::onStop);
		onLoadFile.setValue(this::onLoadFile);
		onNewFile.setValue(this::onNewFile);
	}

	public void onLoadFile(ActionEvent event) {
		uf2ChainList.getValue().clear();
		uf2ChainList.getValue().add("KLhduiwhfduiewhfuiw");

		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open UF2 File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("UF2 Files", "*.uf2"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
//		fileChooser.setInitialDirectory( Files.readAllBytes(null));
		fileChooser.setInitialDirectory( Util.getCurrentDir());
		final File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			UF2BufferFileChain uf2file = new UF2BufferFileChain();
			try {
				uf2file.readFile(selectedFile);
				System.out.println("");
				uf2file.dumpIt(uf2ChainList.getValue());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void onNewFile(ActionEvent event) {
		uf2ChainList.getValue().clear();
	}

	private void onTransmitt() {
	}

	public void onStop(ActionEvent event) {
	}

	void onNewWorkerState(ObservableValue<? extends State> observable, State oldValue, State newValue) {
	}

	public void onChangedSpeed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
	}

	public GridPane getRootNode() {
		return rootNode;
	}
}
