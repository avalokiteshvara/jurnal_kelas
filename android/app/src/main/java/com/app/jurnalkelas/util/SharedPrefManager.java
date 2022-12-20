package com.app.jurnalkelas.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_APP_NAME = "SP_JURNALKELAS_APP";
    public static final String SP_SUDAH_LOGIN = "SP_SUDAH_LOGIN";

    public static final String SP_ID = "SP_ID";
    public static final String SP_USERNAME = "SP_USERNAME";
    public static final String SP_NAMA_LENGKAP = "SP_NAMA_LENGKAP";
    public static final String SP_EMAIL = "SP_EMAIL";
    public static final String SP_LEVEL = "SP_LEVEL";
    public static final String SP_FOTO = "SP_FOTO";
    public static final String SP_LOGIN_TOKEN = "SP_LOGIN_TOKEN";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_APP_NAME, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public String getSpLoginToken() {
        return sp.getString(SP_LOGIN_TOKEN,"");
    }

    public String getSpFoto() {
        return sp.getString(SP_FOTO,"");
    }
    public String getSpAppName() {
        return sp.getString(SP_APP_NAME,"");
    }
    public Boolean getSpSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN,false);
    }
    public Integer getSpId() {
        return sp.getInt(SP_ID,0);
    }
    public String getSpUsername() {
        return sp.getString(SP_USERNAME,"");
    }
    public String getSpNamaLengkap() {
        return sp.getString(SP_NAMA_LENGKAP,"");
    }
    public String getSpEmail() {
        return sp.getString(SP_EMAIL,"");
    }
    public String getSpLevel() {
        return sp.getString(SP_LEVEL,"");
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }


}