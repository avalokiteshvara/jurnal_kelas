package com.app.jurnalkelas.util.api;

import com.app.jurnalkelas.ui.guru.GuruModelList;
import com.app.jurnalkelas.ui.jadwal.JadwalGetMenuResponse;
import com.app.jurnalkelas.ui.jurnal.JurnalModelList;
import com.app.jurnalkelas.ui.kehadiran.KehadiranModelList;
import com.app.jurnalkelas.ui.validasijurnal.ValidasiJurnalModelList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(@Field("username") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("send_tokenid")
    Call<ResponseBody> sendTokenId(
            @Field("user_level") String user_level,
            @Field("user_id") int user_id,
            @Field("token_id") String token_id);

    @GET("get_guru")
    Call<GuruModelList> getGuru();

    @GET("get_jurnalkelas")
    Call<JurnalModelList> getJurnalKelas(@Query("user_level") String user_level,
                                         @Query("user_id") int user_id);

    @GET("get_jadwal")
    Call<JadwalGetMenuResponse> getJadwalPelajaran(
            @Query("user_level") String user_level,
            @Query("user_id") int user_id

    );

    @GET("get_kehadiran_guru")
    Call<KehadiranModelList> getKehadiran(@Query("pegawai_id") int pegawai_id,
                                          @Query("tahun") String tahun,
                                          @Query("bulan") String bulan);

    @FormUrlEncoded
    @POST("update_jurnal")
    Call<ResponseBody> updateJurnal(
            @Field("id") int id,
            @Field("status") String status,
            @Field("topik") String topik);

    @GET("get_validasijurnal")
    Call<ValidasiJurnalModelList> getValidasiJurnal(@Query("user_level") String user_level,
                                                    @Query("user_id") int user_id);

    @FormUrlEncoded
    @POST("validasi_jurnal")
    Call<ResponseBody> validasiJurnal(
            @Field("id") int id,
            @Field("topik") String topik);
}