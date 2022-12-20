package com.app.jurnalkelas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.jurnalkelas.util.SharedPrefManager;
import com.app.jurnalkelas.util.api.BaseApiService;
import com.app.jurnalkelas.util.api.UtilsApi;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.kirana.e_presensi.util.SharedPrefManager;
//import com.kirana.e_presensi.util.api.BaseApiService;
//import com.kirana.e_presensi.util.api.UtilsApi;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    ProgressDialog loading;
    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;

    private AsyncTask mMyTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mContext = this;
        mBaseApiService = UtilsApi.getAPIService();

        sharedPrefManager = new SharedPrefManager(this);

        if (Boolean.TRUE.equals(sharedPrefManager.getSpSudahLogin())) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

            startActivity(new Intent(LoginActivity.this, HelloWorld.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();

        }
    }

    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Custom method to save a bitmap into internal storage
    protected Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Initialize ContextWrapper
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        // Create a file to save the image
        file = new File(file, "profile_picts.jpg");
        Log.e("debug", "File path:  " + file);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_FOTO, file.getAbsolutePath());
        try {
            // Initialize a new OutputStream
            OutputStream stream = null;
            // If the output file exists, it can be replaced or appended to it
            stream = new FileOutputStream(file);
            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            // Flushes the stream
            stream.flush();
            // Closes the stream
            stream.close();
        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Return the saved image Uri
        return Uri.parse(file.getAbsolutePath());
    }

    @OnClick(R.id.btnSignIn)
    public void requestLogin() {
        loading = ProgressDialog.show(mContext, null, "Mohon tunggu...", true, false);
        Log.i("LOGIN", "Mulai login");
        mBaseApiService.login(etUsername.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Log.i("LOGIN", "JSONObject :" + jsonObject.toString());
                                if (jsonObject.getString("error").equals("false")) {

                                    Log.i("LOGIN", "Login SUCCESS");
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                                    int id = jsonObject.getJSONObject("user").getInt("id");
                                    sharedPrefManager.saveSPInt(SharedPrefManager.SP_ID, id);

                                    String username = jsonObject.getJSONObject("user").getString("username");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, username);

                                    String nama_lengkap = jsonObject.getJSONObject("user").getString("nama_lengkap");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA_LENGKAP, nama_lengkap);

                                    String email = jsonObject.getJSONObject("user").getString("email");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_EMAIL, email);

                                    String level = jsonObject.getJSONObject("user").getString("level");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_LEVEL, level);

                                    String token_login = jsonObject.getJSONObject("user").getString("token_login");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_LOGIN_TOKEN, token_login);

                                    String foto = jsonObject.getJSONObject("user").getString("foto");
                                    mMyTask = new DownloadTask().execute(stringToURL(foto));


                                } else {
                                    String error_message = jsonObject.getString("error_msg");
                                    Toasty.error(mContext, error_message, Toast.LENGTH_LONG).show();
                                    Log.i("LOGIN", "Login GAGAL : " + error_message);
                                }
                            } catch (JSONException | IOException e) {
                                Log.i("LOGIN", "Login GAGAL " + e.getMessage());
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toasty.error(mContext, "ERROR:" + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

    }

    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        // Before the tasks execution
        protected void onPreExecute() {
            // Display the progress dialog on async task start
            // mProgressDialog.show();
            loading = ProgressDialog.show(mContext, null, "Mengambil foto profile...", true, false);
        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;

            try {
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();
                // Connect the http url connection
                connection.connect();
                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();
                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                // Return the downloaded bitmap
                return bmp;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Disconnect the http url connection
                Objects.requireNonNull(connection).disconnect();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            // Hide the progress dialog
            // mProgressDialog.dismiss();
            loading.dismiss();

            if (result != null) {
                // Display the downloaded image into ImageView
                // mImageView.setImageBitmap(result);

                // Save bitmap to internal storage
                Uri imageInternalUri = saveImageToInternalStorage(result);
                // Set the ImageView image from internal storage
                //mImageViewInternal.setImageURI(imageInternalUri);

                startActivity(new Intent(mContext, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            } else {
                // Notify user that an error occurred while downloading image
                // Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
