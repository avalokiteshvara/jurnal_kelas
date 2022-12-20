package com.app.jurnalkelas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.jurnalkelas.util.SharedPrefManager;
import com.app.jurnalkelas.util.api.BaseApiService;
import com.app.jurnalkelas.util.api.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fab;
    SharedPrefManager sharedPrefManager;
    BaseApiService mBaseApiService;
    Context mContext;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);

        sharedPrefManager = new SharedPrefManager(this);

        requestMultiplePermissions();

        mContext = this;
        mBaseApiService = UtilsApi.getAPIService();

        CheckTypesTask loading = new CheckTypesTask();

        loading.execute();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        visibilityMenuItem(R.id.nav_data_guru,false);
        visibilityMenuItem(R.id.nav_data_jadwal,false);
        visibilityMenuItem(R.id.nav_jurnal_kelas,false);
        visibilityMenuItem(R.id.nav_rekapitulasi_kehadiran,false);
        visibilityMenuItem(R.id.nav_validasi_jurnal,false);
        //hide menu item
        String userlevel = sharedPrefManager.getSpLevel();
        if("SISWA-JURNAL".equals(userlevel)){
            visibilityMenuItem(R.id.nav_data_guru,true);
            visibilityMenuItem(R.id.nav_data_jadwal,true);
            visibilityMenuItem(R.id.nav_jurnal_kelas,true);
        }else if("GURU".equals(userlevel)){
            visibilityMenuItem(R.id.nav_data_jadwal,true);
            visibilityMenuItem(R.id.nav_rekapitulasi_kehadiran,true);
            visibilityMenuItem(R.id.nav_validasi_jurnal,true);
        }else if("SISWA".equals(userlevel)){
            visibilityMenuItem(R.id.nav_data_guru,true);
            visibilityMenuItem(R.id.nav_data_jadwal,true);
        }

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.navheader_tvName);
        navUsername.setText(sharedPrefManager.getSpNamaLengkap());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_data_guru,
                R.id.nav_data_jadwal,
                R.id.nav_jurnal_kelas,
                R.id.nav_validasi_jurnal)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void visibilityMenuItem(int menu, boolean visible )
    {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(menu).setVisible(visible);
    }

    private void requestMultiplePermissions() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "Selamat datang kembali!", Toast.LENGTH_SHORT).show();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(error -> Toast.makeText(getApplicationContext(), String.format("Some Error! %s", error.toString()), Toast.LENGTH_SHORT).show())
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    public class CheckTypesTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);
        String typeStatus;

        @Override
        protected void onPreExecute() {
            //set message of the dialog
            asyncDialog.setMessage(getString(R.string.mengambil_data));
            //show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(MainActivity.class.getSimpleName(), "getInstanceId failed", task.getException());
                            return;
                        }
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        Log.d(MainActivity.class.getSimpleName(), token);

                        sendRegistrationToServer(token);
                    });

            return null;
        }

        private void sendRegistrationToServer(String token_id) {
            mBaseApiService.sendTokenId(sharedPrefManager.getSpLevel(), sharedPrefManager.getSpId(), token_id)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getString("error").equals("true")) {
                                        String error_message = jsonObject.getString("error_msg");
                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                            } else {
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                        }
                    });
        }

        @Override
        protected void onPostExecute(Void result) {
            //hide the dialog
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }

    }
}
