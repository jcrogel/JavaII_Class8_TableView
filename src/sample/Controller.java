package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Controller {
    Temperature model;

    @FXML // fx:id="tableView";
    TableView<Temperature> tableView;

    public void doLoad(){

        ObservableList<Temperature> values =
                Temperature.getTemperatures(1880, 1881);

        TableColumn<Temperature,String> yearCol
                = new TableColumn<Temperature, String>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory("year"));
        TableColumn<Temperature,String> valueCol
                = new TableColumn<Temperature, String>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory("value"));

        tableView.getColumns().setAll(yearCol, valueCol);
        tableView.setItems(values);
    }


}
