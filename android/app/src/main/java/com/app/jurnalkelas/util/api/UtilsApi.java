package com.app.jurnalkelas.util.api;

public class UtilsApi {

    public static final String BASE_URL = "http://ta.poliwangi.ac.id/~ti17141/index.php/";
//    public static final String BASE_URL = "http://192.168.8.100:5000/jurnal_kelas/index.php/";
    public static final String BASE_URL_API = BASE_URL +  "restapi/";
    public static final String BASE_URL_WEBVIEW = BASE_URL + "webview/";

    // Mendeklarasikan Interface BaseApiService
    //@org.jetbrains.annotations.NotNull
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
