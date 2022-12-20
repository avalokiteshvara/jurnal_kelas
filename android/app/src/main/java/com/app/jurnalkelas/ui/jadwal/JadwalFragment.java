package com.app.jurnalkelas.ui.jadwal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.jurnalkelas.R;
import com.app.jurnalkelas.util.SharedPrefManager;
import com.app.jurnalkelas.util.api.BaseApiService;
import com.app.jurnalkelas.util.api.UtilsApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalFragment extends Fragment {


    JadwalExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    SwipeRefreshLayout swipe;
    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


//        listDataHeader = new ArrayList<>();
//        listDataChild = new HashMap<>();

        View root = inflater.inflate(R.layout.fragment_jadwal, container, false);
        swipe = root.findViewById(R.id.jadwal_swipeContainer);
        // get the listview
        expListView = root.findViewById(R.id.lvExp);

        swipe.setOnRefreshListener(() -> {
            swipe.setRefreshing(false);
            loadData();
        });

        loadData();
        return root;

    }

    private void loadData() {

        BaseApiService mBaseApiService;
        SharedPrefManager sharedPrefManager;

        mBaseApiService = UtilsApi.getAPIService();
        sharedPrefManager = new SharedPrefManager(mContext);

        ProgressDialog loading = ProgressDialog.show(mContext, null, "Mengambil data ...", true, false);

        mBaseApiService.getJadwalPelajaran(sharedPrefManager.getSpLevel(), sharedPrefManager.getSpId())
                .enqueue(new Callback<JadwalGetMenuResponse>() {

                    @Override
                    public void onResponse(Call<JadwalGetMenuResponse> call, Response<JadwalGetMenuResponse> response) {
                        loading.dismiss();

                        JadwalData menuData = response.body().getData();
                        int menuSize = menuData.getJadwal().size();

                        listDataHeader = new ArrayList<>();
                        listDataChild = new HashMap<>();

                        for (int i = 0; i < menuSize; i++) {
                            String namaHari = menuData.getJadwal().get(i).getNama_hari();
                            listDataHeader.add(namaHari);

                            List<String> listArrayList = new ArrayList<>();

                            int subMenuSize = menuData.getJadwal().get(i).getDetails().size();
                            for (int j = 0; j < subMenuSize; j++) {
                                String matapelajaran = menuData.getJadwal().get(i).getDetails().get(j).getMatapelajaran();
                                String pengajar = menuData.getJadwal().get(i).getDetails().get(j).getGuru();
                                String jam = menuData.getJadwal().get(i).getDetails().get(j).getJam();
                                listArrayList.add(jam + " - " + matapelajaran + " - " + pengajar);
                                listDataChild.put(listDataHeader.get(i), listArrayList);
                            }
                        }

                        listAdapter = new JadwalExpandableListAdapter(mContext, listDataHeader, listDataChild);
                        expListView.setAdapter(listAdapter);

                        if (sharedPrefManager.getSpLevel().equals("SISWA") || sharedPrefManager.getSpLevel().equals("SISWA-JURNAL")) {
                            //DateFormat dateFormat = new SimpleDateFormat("u", Locale.getDefault());
                            //String dayOfWeek = dateFormat.format(new Date());
                            Calendar c = Calendar.getInstance();
                            String dayOfWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
                            //minggu,senin,selasa,rabu,kamis,jumat,sabtu

                            if ((Integer.parseInt(dayOfWeek)) > 1) {
                                expListView.expandGroup(Integer.parseInt(dayOfWeek) - 2);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JadwalGetMenuResponse> call, Throwable t) {
                        Toasty.error(mContext, "Ada kesalahan!\n" + t.toString(), Toast.LENGTH_LONG, true).show();
                        loading.dismiss();
                    }
                });

    }

}
