package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;


public class Controller {
    Temperature model;

    @FXML // fx:id="tableView";
    TableView<Temperature> tableView;
    @FXML // fx:id="chart";
    LineChart<String, Float> linechart;

    public void doLoad(){
        /* Draw as much as possible before going Async */
        this.preDraw();

        CompletableFuture<ObservableList<Temperature>> future = new CompletableFuture<>();
        future.supplyAsync(this::asyncLoadData).thenApply(this::setupChartValues).thenAccept(this::drawChart);

    }

    public void preDraw(){
        TableColumn<Temperature,String> yearCol
                = new TableColumn<Temperature, String>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory("year"));
        TableColumn<Temperature,String> valueCol
                = new TableColumn<Temperature, String>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory("value"));
        TableColumn<Temperature,String> monthCol
                = new TableColumn<Temperature, String>("Month");
        monthCol.setCellValueFactory(new PropertyValueFactory("month"));
        tableView.getColumns().setAll(yearCol, monthCol, valueCol);

        linechart.getXAxis().setLabel("Year");
        linechart.getYAxis().setLabel("Temp");

    }

    public ObservableList<Temperature> asyncLoadData() {
        ObservableList<Temperature> values =
                Temperature.getTemperatures(1880, 2018);
        tableView.setItems(values);
        return values;
    }

    public XYChart.Series<String, Float> setupChartValues(ObservableList<Temperature> values){

        XYChart.Series<String, Float> series = new XYChart.Series<>();
        String lastYear = "";
        float avg_value = 0;
        int number_of_items = 0;

        for(Temperature t : values){
            String thisYear = t.getYear();

            if (!thisYear.equals(lastYear)){

                if (number_of_items != 0){
                    avg_value = avg_value / number_of_items;
                } else {
                    avg_value = 0;
                }

                series.getData().add(new XYChart.Data<String, Float>( thisYear, avg_value));
                lastYear = thisYear;
                number_of_items = 0;
                avg_value = 0;
            }

            avg_value += t.getValue();
            number_of_items++;
        }

        return series;
    }

    public void drawChart(XYChart.Series<String, Float> v){
        Platform.runLater(()->{
            linechart.getData().setAll(v);
        });
    }

}
