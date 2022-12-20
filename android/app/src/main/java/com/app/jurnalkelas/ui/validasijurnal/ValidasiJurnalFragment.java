package com.app.jurnalkelas.ui.validasijurnal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.jurnalkelas.MainActivity;
import com.app.jurnalkelas.R;
import com.app.jurnalkelas.util.SharedPrefManager;
import com.app.jurnalkelas.util.api.BaseApiService;
import com.app.jurnalkelas.util.api.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidasiJurnalFragment extends Fragment {
    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;

    private ValidasiJurnalAdapter validasiJurnalAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_validasi_jurnal, container, false);
        swipe = root.findViewById(R.id.validasijurnal_swipeContainer);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Validasi Topik Jurnal");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            loadData();
        });

        loadData();
        return root;
    }

    private void loadData() {
        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);
        mBaseApiService.getValidasiJurnal(sharedPrefManager.getSpLevel(), sharedPrefManager.getSpId())
                .enqueue(new Callback<ValidasiJurnalModelList>() {
                    @Override
                    public void onResponse(@NotNull Call<ValidasiJurnalModelList> call, @NotNull Response<ValidasiJurnalModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generatealidasiJurnalList(Objects.requireNonNull(response.body()).getValidasiJurnalArrayList());

                        } else {
                            Toasty.info(mContext,"Jurnal Kelas telah tervalidasi semua",Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValidasiJurnalModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }

    private void generatealidasiJurnalList(ArrayList<ValidasiJurnalModelRecycler> validasijurnalArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_validasijurnal_list);
        validasiJurnalAdapter = new ValidasiJurnalAdapter(validasijurnalArrayList);

        validasiJurnalAdapter.onBindCallBack = (jenis, viewHolder, position) -> {
            if ("validasi_jurnal".equals(jenis)) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Menyimpan Data")
                        .setMessage("Apakah anda ingin menyimpan data jurnal kelas ini?")
                        .setPositiveButton("Simpan", (dialog, which) -> {
                            EditText etTopik = Objects.requireNonNull(recyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.rowValidasi_etTopik);
                            validasiJurnal(viewHolder.rowId, etTopik.getText().toString());
                        }).setNegativeButton("Batal", null).show();

            }
        };

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(validasiJurnalAdapter);
    }

    private void validasiJurnal(int rowId, String topik) {
        loading = ProgressDialog.show(mContext, null, "Menyimpan data, Mohon tunggu...", true, false);
        mBaseApiService.validasiJurnal(
                rowId, topik
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            Toast.makeText(mContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                            loadData();

                        } else {
                            String error_message = jsonObject.getString("error_msg");
                            Toasty.error(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });
    }

    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            return event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK;
        });
    }

}
