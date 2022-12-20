package com.app.jurnalkelas.ui.validasijurnal;

import com.app.jurnalkelas.ui.jurnal.JurnalModelRecycler;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ValidasiJurnalModelList {

    @SerializedName("validasiJurnalList")
    private ArrayList<ValidasiJurnalModelRecycler> validasiJurnalModelList;

    public ArrayList<ValidasiJurnalModelRecycler> getValidasiJurnalArrayList() {
        return validasiJurnalModelList;
    }

    public void setValidasiJurnalArraylList(ArrayList<ValidasiJurnalModelRecycler> validasiJurnalArraylList) {
        this.validasiJurnalModelList = validasiJurnalArraylList;
    }
}
