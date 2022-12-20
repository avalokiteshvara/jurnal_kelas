package com.app.jurnalkelas.ui.jadwal;

import com.google.gson.annotations.SerializedName;

class JadwalDetailsItem {

    @SerializedName("jam")
    private String jam;

    @SerializedName("matapelajaran")
    private String matapelajaran;
    @SerializedName("guru")
    private String guru;

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getMatapelajaran() {
        return matapelajaran;
    }

    public void setMatapelajaran(String matapelajaran) {
        this.matapelajaran = matapelajaran;
    }

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }

}
