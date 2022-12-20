package com.app.jurnalkelas.ui.guru;

import com.google.gson.annotations.SerializedName;

public class GuruModelRecycler {
    @SerializedName("id")
    private int id;
    @SerializedName("kode_guru")
    private String kode_guru;
    @SerializedName("nama_lengkap")
    private String nama_lengkap;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("telp")
    private String telp;
    @SerializedName("email")
    private String email;
    @SerializedName("mengajar")
    private String mengajar;
    @SerializedName("fotoUrl")
    private String fotoUrl;

    public GuruModelRecycler(int id, String kode_guru, String nama_lengkap,
                             String alamat, String telp, String email, String mengajar, String fotoUrl) {
        this.id = id;
        this.kode_guru = kode_guru;
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.telp = telp;
        this.email = email;
        this.mengajar = mengajar;
        this.fotoUrl = fotoUrl;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode_guru() {
        return kode_guru;
    }

    public void setKode_guru(String kode_guru) {
        this.kode_guru = kode_guru;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMengajar() {
        return mengajar;
    }

    public void setMengajar(String mengajar) {
        this.mengajar = mengajar;
    }


}
