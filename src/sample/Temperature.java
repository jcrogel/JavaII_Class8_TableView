package sample;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Temperature {
    private StringProperty year;
    private StringProperty month;
    private FloatProperty value;

    public Temperature(String year, String month, float value){
        this.year = new SimpleStringProperty(this, "year");
        this.month = new SimpleStringProperty(this, "month");
        this.value = new SimpleFloatProperty(this, "value");
        this.setYear(year);
        this.setValue(value);
        this.setMonth(month);
    }

    public String getMonth() {  return month.get(); }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public String getYear() {  return year.get(); }

    public void setYear(String _year) {
        this.year.set(_year);
    }

    public float getValue() {
        return value.get();
    }

    public void setValue(float value) {
        this.value.set(value);
    }


    public static ObservableList<Temperature> getTemperatures(int start, int end){
        String addr = "https://www.ncdc.noaa.gov/cag/global/time-series/globe/land_ocean/all/1/%d-%d.json";
        addr = String.format(addr, start, end);

        try {
            URL address = new URL(addr);
            JsonReader reader = new JsonReader(
                    new InputStreamReader(address.openStream()
                    )
            );

            Gson gson = new Gson();
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            JsonObject data = root.getAsJsonObject("data");

            ObservableList<Temperature> values = FXCollections.observableArrayList();

            Set<Map.Entry<String, JsonElement>> entrySet = data.entrySet();
            for(Map.Entry<String,JsonElement> entry : entrySet){
                String raw_year = entry.getKey();
                String year = raw_year.substring(0, 4);
                String month = raw_year.substring(4, 6);
                Float value = entry.getValue().getAsFloat();
                values.add(new Temperature(year, month, value));
            }

            return values;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

