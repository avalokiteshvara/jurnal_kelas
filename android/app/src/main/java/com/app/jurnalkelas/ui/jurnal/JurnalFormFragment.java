package com.app.jurnalkelas.ui.jurnal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.jurnalkelas.MainActivity;
import com.app.jurnalkelas.R;
import com.app.jurnalkelas.util.api.BaseApiService;
import com.app.jurnalkelas.util.api.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JurnalFormFragment extends Fragment {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.spJurnal)
    Spinner spJurnal;
    @BindView(R.id.etTopik)
    EditText etTopik;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private Context mContext;
    private BaseApiService mBaseApiService;
    private ProgressDialog loading;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_form_jurnal, container, false);
        ButterKnife.bind(this, root);

        mBaseApiService = UtilsApi.getAPIService();

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle("Form Jurnal Kelas");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        Picasso.get().load(getArguments().getString("logoUrl")).into(ivLogo);
        etTopik.setText(getArguments().getString("topik"));

        String compareValue = getArguments().getString("status");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext, R.array.status_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJurnal.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            spJurnal.setSelection(spinnerPosition);
        }

        return root;
    }

    private void updateJurnal() {
        loading = ProgressDialog.show(mContext, null, "Menyimpan data, Mohon tunggu...", true, false);
        mBaseApiService.updateJurnal(
                getArguments().getInt("id"),
                spJurnal.getSelectedItem().toString(),
                etTopik.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getString("error").equals("false")) {

                            Toast.makeText(mContext, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();

                            JurnalFragment mf = new JurnalFragment();
                            FragmentTransaction ft = getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.nav_host_fragment, mf, JurnalFragment.class.getSimpleName())
                                    .addToBackStack(null);
                            ft.commit();


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

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        new AlertDialog.Builder(mContext)
                .setTitle("Menyimpan Data")
                .setMessage("Apakah anda ingin menyimpan data jurnal kelas ini?")
                .setPositiveButton("Simpan", (dialog, which) -> {
                    updateJurnal();
                }).setNegativeButton("Batal", null).show();

    }


    @Override
    //Pressed return button
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                JurnalFragment mf = new JurnalFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.nav_host_fragment, mf, JurnalFragment.class.getSimpleName())
                        .addToBackStack(null);
                ft.commit();

                return true;
            }
            return false;
        });
    }

}
