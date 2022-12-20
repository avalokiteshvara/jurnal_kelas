package com.app.jurnalkelas.ui.jadwal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JadwalData {

    @SerializedName("jadwal")
    private List<JadwalItem> jadwal;

    public List<JadwalItem> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<JadwalItem> jadwal) {
        this.jadwal = jadwal;
    }
}
