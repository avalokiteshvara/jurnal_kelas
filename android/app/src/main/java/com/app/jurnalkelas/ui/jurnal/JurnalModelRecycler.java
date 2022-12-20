package com.app.jurnalkelas.ui.jurnal;

import com.google.gson.annotations.SerializedName;

public class JurnalModelRecycler {
    @SerializedName("id")
    private int id;
    @SerializedName("logoUrl")
    private String logoUrl;
    @SerializedName("matapelajaran")
    private String matapelajaran;
    @SerializedName("pengajar")
    private String pengajar;
    @SerializedName("status")
    private String status;
    @SerializedName("jam")
    private String jam;
    @SerializedName("mulai")
    private String mulai;
    @SerializedName("selesai")
    private String selesai;
    @SerializedName("topik")
    private String topik;
    @SerializedName("diff")
    private String diff;


    public JurnalModelRecycler(int id, String logoUrl, String matapelajaran, String pengajar, String status, String jam, String mulai, String selesai, String topik, String diff) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.matapelajaran = matapelajaran;
        this.pengajar = pengajar;
        this.status = status;
        this.jam = jam;
        this.mulai = mulai;
        this.selesai = selesai;
        this.topik = topik;
        this.diff = diff;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMatapelajaran() {
        return matapelajaran;
    }

    public void setMatapelajaran(String matapelajaran) {
        this.matapelajaran = matapelajaran;
    }

    public String getPengajar() {
        return pengajar;
    }

    public void setPengajar(String pengajar) {
        this.pengajar = pengajar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public String getTopik() {
        return topik;
    }

    public void setTopik(String topik) {
        this.topik = topik;
    }


}
