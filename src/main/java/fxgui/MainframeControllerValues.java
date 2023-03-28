package fxgui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainframeControllerValues {
	public ObjectProperty<EventHandler<ActionEvent>> onNewFile;
	public ObjectProperty<EventHandler<ActionEvent>> onLoadFile;
	public ObjectProperty<EventHandler<ActionEvent>> onSaveFile;
	public ObjectProperty<ObservableList<String>> uf2ChainList;
	public StringProperty ssid;
	public StringProperty pwd;
}
