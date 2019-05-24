package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.ListView;


public class Controller {
    Temperature model;

    @FXML // fx:id="listview";
            ListView<String> listview;

    public void doLoad(){

        ObservableList<String> values =
                Temperature.getTemperatures(
                        1880, 2018);

        listview.setItems(values);
    }


}
