package com.app.jurnalkelas.ui.jurnal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JurnalModelList {

    @SerializedName("jurnalkelasList")
    private ArrayList<JurnalModelRecycler> jurnalKelasList;

    public ArrayList<JurnalModelRecycler> getJurnalKelasArrayList() {
        return jurnalKelasList;
    }

    public void setJurnalKelasArraylList(ArrayList<JurnalModelRecycler> jurnalKelasArrayList) {
        this.jurnalKelasList = jurnalKelasArrayList;
    }
}
