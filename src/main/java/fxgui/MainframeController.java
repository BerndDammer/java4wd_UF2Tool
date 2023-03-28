package fxgui;

import java.io.File;
import java.io.IOException;

import gen.UF2BufferFileChain2;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class MainframeController extends MainframeControllerValues {

	private final GridPane rootNode;

	public MainframeController() {
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

		UF2BufferFileChain2 uf2file = new UF2BufferFileChain2();
		try {
			uf2file.readFile(new File("test.uf2"));
			System.out.println("");
			uf2file.dumpIt(uf2ChainList.getValue());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
