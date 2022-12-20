package com.app.jurnalkelas.ui.guru;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruFragment extends Fragment {
    Context mContext;
    BaseApiService mBaseApiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;

    private GuruAdapter guruAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_guru, container, false);
        swipe = root.findViewById(R.id.guru_swipeContainer);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Data Guru");
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
        mBaseApiService.getGuru()
                .enqueue(new Callback<GuruModelList>() {
                    @Override
                    public void onResponse(@NotNull Call<GuruModelList> call, @NotNull Response<GuruModelList> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            generateGuruList(Objects.requireNonNull(response.body()).getGuruArrayList());

                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GuruModelList> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });
    }

    private void generateGuruList(ArrayList<GuruModelRecycler> guruArrayList) {

        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recycler_view_guru_list);
        guruAdapter = new GuruAdapter(guruArrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(guruAdapter);
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
