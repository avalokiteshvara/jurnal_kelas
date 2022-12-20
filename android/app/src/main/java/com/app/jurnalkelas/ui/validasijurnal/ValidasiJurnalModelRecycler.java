package com.app.jurnalkelas.ui.validasijurnal;

import com.google.gson.annotations.SerializedName;

public class ValidasiJurnalModelRecycler {

    @SerializedName("id")
    private int id;

    @SerializedName("matapelajaran")
    private String matapelajaran;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("topik")
    private String topik;

    public ValidasiJurnalModelRecycler(int id, String matapelajaran, String tanggal, String topik) {
        this.id = id;
        this.matapelajaran = matapelajaran;
        this.tanggal = tanggal;
        this.topik = topik;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatapelajaran() {
        return matapelajaran;
    }

    public void setMatapelajaran(String matapelajaran) {
        this.matapelajaran = matapelajaran;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }
}
