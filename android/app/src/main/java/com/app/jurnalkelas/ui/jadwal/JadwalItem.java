package com.app.jurnalkelas.ui.jadwal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class JadwalItem {

    @SerializedName("nama_hari")
    private String nama_hari;
    @SerializedName("details")
    private List<JadwalDetailsItem> details;

    public String getNama_hari() {
        return nama_hari;
    }

    public void setNama_hari(String nama_hari) {
        this.nama_hari = nama_hari;
    }

    public List<JadwalDetailsItem> getDetails() {
        return details;
    }

    public void setDetails(List<JadwalDetailsItem> details) {
        this.details = details;
    }
}
