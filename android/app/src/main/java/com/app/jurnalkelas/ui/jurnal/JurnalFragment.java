package com.app.jurnalkelas.ui.jurnal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JurnalFragment extends Fragment {

    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;

    private JurnalAdapter jurnalAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_jurnal, container, false);
        swipe = root.findViewById(R.id.jurnalkelas_swipeContainer);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Matapelajaran Hari Ini");
        FloatingActionButton floatingActionButton = ((MainActivity) Objects.requireNonNull(getActivity())).getFloatingActionButton();
        if (floatingActionButton != null) {
            floatingActionButton.show();

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
        mBaseApiService.getJurnalKelas(sharedPrefManager.getSpLevel(), sharedPrefManager.getSpId())
                .enqueue(new Callback<JurnalModelList>() {
                    @Override
                    public void onResponse(@NotNull Call<JurnalModelList> call, @NotNull Response<JurnalModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateJurnalKelasList(Objects.requireNonNull(response.body()).getJurnalKelasArrayList());

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<JurnalModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }

    private void generateJurnalKelasList(ArrayList<JurnalModelRecycler> jurnalKelasArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_jurnalkelas_list);
        jurnalAdapter = new JurnalAdapter(jurnalKelasArrayList);

        jurnalAdapter.onBindCallBack = (jenis, viewHolder, position) -> {
            if ("isi_jurnal".equals(jenis)) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", viewHolder.rowId);
                bundle.putString("status", viewHolder.status);
                bundle.putString("topik", viewHolder.topik);
                bundle.putString("logoUrl", viewHolder.logoUrl);

                JurnalFormFragment fragment = new JurnalFormFragment();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) getView().getContext();

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, fragment, JurnalFormFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            }
        };

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(jurnalAdapter);
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