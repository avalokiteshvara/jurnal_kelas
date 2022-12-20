package com.app.jurnalkelas.ui.kehadiran;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KehadiranFragment extends Fragment {

    @BindView(R.id.etKehadiran_Tahun)
    EditText etKehadiranTahun;
    @BindView(R.id.spKehadiran_bulan)
    Spinner spKehadiranBulan;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    private Context mContext;
    private BaseApiService mBaseApiService;
    private SharedPrefManager sharedPrefManager;
    private ProgressDialog loading;
    private KehadiranAdapter kehadiranAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(
                Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_kehadiran, container, false);
        swipe = root.findViewById(R.id.kehadiran_swipeContainer);

        ButterKnife.bind(this, root);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Data Kehadiran");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.hide();
        }

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            loadData();
        });

        return root;
    }

    private void loadData() {

        loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);
        mBaseApiService.getKehadiran(sharedPrefManager.getSpId(), etKehadiranTahun.getText().toString(), spKehadiranBulan.getSelectedItem().toString())
                .enqueue(new Callback<KehadiranModelList>() {
                    @Override
                    public void onResponse(@NotNull Call<KehadiranModelList> call, @NotNull Response<KehadiranModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateKehadiranList(Objects.requireNonNull(response.body()).getKehadiranArrayList());

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<KehadiranModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }

    private void generateKehadiranList(ArrayList<KehadiranModelRecycler> kehadiranArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_kehadiran_list);
        kehadiranAdapter = new KehadiranAdapter(kehadiranArrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kehadiranAdapter);
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

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        loadData();
        hideSoftKeyboard(getActivity());
    }
}
