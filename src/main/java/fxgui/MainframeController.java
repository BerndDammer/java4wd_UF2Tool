package fxgui;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import RP2040.Flash;
import gen.Util;
import genuf2.MemoryRegion;
import genuf2.UF2BufferFileChain;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainframeController extends MainframeControllerValues {

	private final GridPane rootNode;
	private final Stage stage;

	public MainframeController(final Stage stage) {
		this.stage = stage;
		rootNode = new MainframeLoader(this);

		//////////////////////////////
		/////////// Action Connections
		onLoadFile.setValue(this::onLoadFile);
		onNewFile.setValue(this::onNewFile);
		onSaveFile.setValue(this::onSaveFile);
	}

	private void onLoadFile(ActionEvent event) {
		clearAll();

		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open UF2 File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("UF2 Files", "*.uf2"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(Util.getCurrentDir());
		final File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			try {
				final UF2BufferFileChain uf2file = UF2BufferFileChain.fromFile(selectedFile);
				uf2file.dumpIt(uf2ChainList.getValue());
			} catch (IOException e) {
				final Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.initModality(Modality.APPLICATION_MODAL);
				alert.initOwner(stage);
				alert.getDialogPane().setContentText(e.getMessage());
				alert.getDialogPane().setHeaderText("Problem by loading File");
				alert.showAndWait();
			}
		}
	}

	private void onNewFile(ActionEvent event) {
		clearAll();
	}

	private void onSaveFile(ActionEvent event) {
		
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Write UF2 File");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("UF2 Files", "*.uf2"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.*"));
		fileChooser.setInitialDirectory(Util.getCurrentDir());
		final File selectedFile = fileChooser.showOpenDialog(stage);
		
		final MemoryRegion mr = getMemFromPara();
		final UF2BufferFileChain u2c = UF2BufferFileChain.fromMemoryRegion(mr);
		try {
			u2c.writeFile(selectedFile);

			final Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.initOwner(stage);
			alert.getDialogPane().setContentText("File :  " + selectedFile + " written");
			alert.getDialogPane().setHeaderText("success");
			alert.showAndWait();
		} catch (IOException e) {
			final Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.initModality(Modality.APPLICATION_MODAL);
			alert.initOwner(stage);
			alert.getDialogPane().setContentText(e.getMessage());
			alert.getDialogPane().setHeaderText("Problem by writing File");
			alert.showAndWait();
		}
	}

	private final void clearAll() {
		uf2ChainList.getValue().clear();
		ssid.setValue("");
		pwd.setValue("");
	}
	
	private MemoryRegion getMemFromPara()
	{
		final MemoryRegion result = new MemoryRegion(Flash.PARA_BASE, Flash.PARA_SIZE);
		ByteBuffer b = result.getByteBuffer();
		b.clear();

		b.put(ssid.getValue().getBytes());
		while (b.position() < 64)
			b.put((byte) 0);

		b.put(pwd.getValue().getBytes());
		while (b.position() < 128)
			b.put((byte) 0);

		return result;
	}

	public GridPane getRootNode() {
		return rootNode;
	}
}
