package com.app.jurnalkelas.ui.guru;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GuruModelList {

    @SerializedName("guruList")
    private ArrayList<GuruModelRecycler> guruList;

    public ArrayList<GuruModelRecycler> getGuruArrayList() {
        return guruList;
    }

    public void setGuruArraylList(ArrayList<GuruModelRecycler> guruArrayList) {
        this.guruList = guruArrayList;
    }
}
