package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Temperature {
    private String year;
    private Float value;

    public Temperature(String year, float value){
        this.year = year;
        this.value = value;
    }


    public static ObservableList<String> getTemperatures(int start, int end){
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

            ObservableList<String> values = FXCollections.observableArrayList();

            Set<Map.Entry<String, JsonElement>> entrySet = data.entrySet();
            for(Map.Entry<String,JsonElement> entry : entrySet){
                values.add(entry.getKey()+ " " + entry.getValue().getAsString());
            }

            return values;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

