package fxgui;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class MainframeLoader extends GridPane {

	public class UF2List extends ListView<String> {

		public UF2List() {
			setEditable(false);
		}
	}

	public MainframeLoader(final MainframeControllerValues mainframeControllerValues) {
		final MenuBar menuBar = new MenuBar();
		final Menu menuFile = new Menu("File");
		final MenuItem newFile = new MenuItem("New");
		final MenuItem openFile = new MenuItem("Open");
		final MenuItem saveFile = new MenuItem("Save");

		menuFile.getItems().addAll(newFile, openFile, saveFile);
		menuBar.getMenus().add(menuFile);

		final UF2List uf2List = new UF2List();
		final TextField ssid = new TextField();
		final TextField pwd = new TextField();

		add(menuBar, 0, 0, GridPane.REMAINING, 1);

		add(uf2List, 0, 1, 1, GridPane.REMAINING);

		add(new Label("SSID"), 1, 1);
		add(ssid, 1, 2);

		add(new Label("PWD"), 1, 3);
		add(pwd, 1, 4);

		///////////////////////////////
		//// Make Property connections

		mainframeControllerValues.uf2ChainList = uf2List.itemsProperty();
		mainframeControllerValues.ssid = ssid.textProperty();
		mainframeControllerValues.pwd = pwd.textProperty();
		mainframeControllerValues.onNewFile = newFile.onActionProperty();
		mainframeControllerValues.onLoadFile = openFile.onActionProperty();
		mainframeControllerValues.onSaveFile = saveFile.onActionProperty();
	}

}
