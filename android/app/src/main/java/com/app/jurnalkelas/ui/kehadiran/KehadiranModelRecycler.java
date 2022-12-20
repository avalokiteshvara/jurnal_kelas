package com.app.jurnalkelas.ui.kehadiran;

import com.google.gson.annotations.SerializedName;

public class KehadiranModelRecycler {
    @SerializedName("id")
    private int id;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("hari")
    private String hari;
    @SerializedName("nama_kelas")
    private String nama_kelas;
    @SerializedName("pelajaran")
    private String pelajaran;
    @SerializedName("status")
    private String status;
    @SerializedName("topik")
    private String topik;


    public KehadiranModelRecycler(int id, String tanggal, String hari, String nama_kelas,
                                  String pelajaran, String status, String topik) {
        this.id = id;
        this.tanggal = tanggal;
        this.hari = hari;
        this.nama_kelas = nama_kelas;
        this.pelajaran = pelajaran;
        this.status = status;
        this.topik = topik;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getPelajaran() {
        return pelajaran;
    }

    public void setPelajaran(String pelajaran) {
        this.pelajaran = pelajaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
