package com.app.jurnalkelas.ui.jadwal;

import com.google.gson.annotations.SerializedName;

public class JadwalGetMenuResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private JadwalData data;
    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JadwalData getData() {
        return data;
    }

    public void setData(JadwalData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
