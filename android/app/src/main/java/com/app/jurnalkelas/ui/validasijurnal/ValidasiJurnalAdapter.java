package com.app.jurnalkelas.ui.validasijurnal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jurnalkelas.R;
import com.app.jurnalkelas.ui.jurnal.JurnalModelRecycler;


import java.util.ArrayList;

public class ValidasiJurnalAdapter extends RecyclerView.Adapter<ValidasiJurnalAdapter.ValidasiJurnalViewHolder> {

    OnBindCallBack onBindCallBack;
    private ArrayList<ValidasiJurnalModelRecycler> dataList;

    public ValidasiJurnalAdapter(ArrayList<ValidasiJurnalModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ValidasiJurnalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_validasijurnal_list, parent, false);
        return new ValidasiJurnalAdapter.ValidasiJurnalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValidasiJurnalViewHolder holder, int position) {
        holder.rowId = dataList.get(position).getId();
        holder.tvMataPelajaran.setText(dataList.get(position).getMatapelajaran());
        holder.tvTanggal.setText(dataList.get(position).getTanggal());
        holder.etTopik.setText(dataList.get(position).getTopik());

        holder.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBindCallBack != null) {
                    onBindCallBack.OnViewBind("validasi_jurnal", holder, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ValidasiJurnalViewHolder extends RecyclerView.ViewHolder {
        int rowId;
        TextView tvMataPelajaran,tvTanggal;
        Button btnSimpan;
        EditText etTopik;

        public ValidasiJurnalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMataPelajaran = itemView.findViewById(R.id.rowValidasi_tvMatapelajaran);
            tvTanggal = itemView.findViewById(R.id.rowValidasi_tvTanggal);
            etTopik = itemView.findViewById(R.id.rowValidasi_etTopik);
            btnSimpan = itemView.findViewById(R.id.rowValidasi_btnSimpan);
        }
    }
}
