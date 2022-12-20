package com.app.jurnalkelas.ui.jurnal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jurnalkelas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JurnalAdapter extends RecyclerView.Adapter<JurnalAdapter.JurnalViewHolder> {

    OnBindCallBack onBindCallBack;
    private ArrayList<JurnalModelRecycler> dataList;

    public JurnalAdapter(ArrayList<JurnalModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public JurnalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_jurnalkelas_list, parent, false);
        return new JurnalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JurnalViewHolder holder, int position) {

        holder.rowId = dataList.get(position).getId();
        holder.tvMatapelajaran.setText(dataList.get(position).getMatapelajaran());
        holder.tvPengajar.setText(dataList.get(position).getPengajar());
        holder.tvJam.setText(dataList.get(position).getJam());
        holder.tvMulai.setText(dataList.get(position).getMulai());
        holder.tvSelesai.setText(dataList.get(position).getSelesai());
        holder.status = dataList.get(position).getStatus();
        holder.topik = dataList.get(position).getTopik();
        holder.logoUrl = dataList.get(position).getLogoUrl();
        holder.diff = dataList.get(position).getDiff();

        Picasso.get().load(dataList.get(position).getLogoUrl()).into(holder.ivLogo);

        if("+".equals(dataList.get(position).getDiff())){
            holder.btnIsiJurnal.setText("ISI JURNAL");
            holder.btnIsiJurnal.setEnabled(false);
            holder.btnIsiJurnal.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }else{
            if ("PENDING".equals(dataList.get(position).getStatus())) {
                holder.btnIsiJurnal.setText("ISI JURNAL");
                holder.btnIsiJurnal.setBackgroundColor(Color.RED);
                holder.btnIsiJurnal.setTextColor(Color.WHITE);
            } else {
                if ("HADIR".equals(dataList.get(position).getStatus())) {
                    holder.btnIsiJurnal.setBackgroundColor(Color.GREEN);
                    holder.btnIsiJurnal.setTextColor(Color.BLACK);
                } else if ("DIGANTIKAN".equals(dataList.get(position).getStatus())) {
                    holder.btnIsiJurnal.setBackgroundColor(Color.BLUE);
                    holder.btnIsiJurnal.setTextColor(Color.WHITE);
                } else {
                    holder.btnIsiJurnal.setBackgroundColor(Color.YELLOW);
                    holder.btnIsiJurnal.setTextColor(Color.BLACK);
                }

                holder.btnIsiJurnal.setText("UBAH JURNAL");
            }
        }





        holder.btnIsiJurnal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBindCallBack != null) {
                    onBindCallBack.OnViewBind("isi_jurnal", holder, position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class JurnalViewHolder extends RecyclerView.ViewHolder {

        TextView tvMatapelajaran, tvPengajar, tvJam, tvMulai, tvSelesai;
        ImageView ivLogo;
        Button btnIsiJurnal;
        int rowId;
        String status, topik, logoUrl,diff;

        public JurnalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatapelajaran = itemView.findViewById(R.id.rowJurnalKelas_tvMatapelajaran);
            tvPengajar = itemView.findViewById(R.id.rowJurnalKelas_tvPengajar);
            tvJam = itemView.findViewById(R.id.rowJurnalKelas_tvJam);
            tvMulai = itemView.findViewById(R.id.rowJurnalKelas_tvMulai);
            tvSelesai = itemView.findViewById(R.id.rowJurnalKelas_tvSelesai);
            ivLogo = itemView.findViewById(R.id.rowJurnalKelas_ivLogo);
            btnIsiJurnal = itemView.findViewById(R.id.rowJurnalKelas_btnIsiJurnal);
        }
    }
}
