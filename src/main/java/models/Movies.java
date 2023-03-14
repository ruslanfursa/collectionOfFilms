package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Movies {
    @SerializedName("items")
    private ArrayList<Film> items;
    public ArrayList<Film> getItems() {
        return items;
    }

    public void setItems(ArrayList<Film> items) {
        this.items = items;
    }
}
