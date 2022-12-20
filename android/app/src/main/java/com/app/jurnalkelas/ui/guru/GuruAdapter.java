package com.app.jurnalkelas.ui.guru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jurnalkelas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GuruAdapter extends RecyclerView.Adapter<GuruAdapter.GuruViewHolder> {

    private ArrayList<GuruModelRecycler> dataList;

    public GuruAdapter(ArrayList<GuruModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public GuruAdapter.GuruViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_guru_list, parent, false);
        return new GuruAdapter.GuruViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruAdapter.GuruViewHolder holder, int position) {

        holder.tvKodeGuru.setText(dataList.get(position).getKode_guru());
        holder.tvNamaLengkap.setText(dataList.get(position).getNama_lengkap());
        holder.tvAlamat.setText(dataList.get(position).getAlamat());
        holder.tvEmail.setText(dataList.get(position).getEmail());
        holder.tvMengajar.setText(dataList.get(position).getMengajar());
        Picasso.get().load(dataList.get(position).getFotoUrl()).into(holder.ivFoto);

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

    class GuruViewHolder extends RecyclerView.ViewHolder {

        TextView tvKodeGuru, tvNamaLengkap, tvAlamat, tvEmail, tvMengajar;
        ImageView ivFoto;

        public GuruViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKodeGuru = itemView.findViewById(R.id.rowGuru_tvKodeGuru);
            tvNamaLengkap = itemView.findViewById(R.id.rowGuru_tvNamaLengkap);
            tvAlamat = itemView.findViewById(R.id.rowGuru_tvAlamat);
            tvEmail = itemView.findViewById(R.id.rowGuru_tvEmail);
            tvMengajar = itemView.findViewById(R.id.rowGuru_tvMengajar);
            ivFoto = itemView.findViewById(R.id.rowGuru_ivFoto);
        }
    }
}
