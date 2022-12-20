package com.app.jurnalkelas.ui.kehadiran;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KehadiranModelList {
    @SerializedName("kehadiranGuru")
    private ArrayList<KehadiranModelRecycler> kehadiranList;

    public ArrayList<KehadiranModelRecycler> getKehadiranArrayList() {
        return kehadiranList;
    }

    public void setKehadiranArraylList(ArrayList<KehadiranModelRecycler> kehadiranArrayList) {
        this.kehadiranList = kehadiranArrayList;
    }
}
