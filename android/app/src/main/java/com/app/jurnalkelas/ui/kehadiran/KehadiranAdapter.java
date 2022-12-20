package com.app.jurnalkelas.ui.kehadiran;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.jurnalkelas.R;

import java.util.ArrayList;

public class KehadiranAdapter extends RecyclerView.Adapter<KehadiranAdapter.KehadiranViewHolder> {

    private ArrayList<KehadiranModelRecycler> dataList;

    public KehadiranAdapter(ArrayList<KehadiranModelRecycler> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public KehadiranAdapter.KehadiranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_kehadiran_list, parent, false);
        return new KehadiranAdapter.KehadiranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KehadiranViewHolder holder, int position) {

        holder.teKiri.setText(String.format("%s%n%s%n%s%n%s",
                dataList.get(position).getTanggal(),
                dataList.get(position).getHari(),
                dataList.get(position).getNama_kelas(),
                dataList.get(position).getPelajaran())
        );

        holder.teTopik.setText(dataList.get(position).getTopik());

        if ("PENDING".equals(dataList.get(position).getStatus())) {
            holder.teKanan.setText("JURNAL BELUM DIISI");
            holder.teKanan.setBackgroundColor(Color.RED);
            holder.teKanan.setTextColor(Color.WHITE);
        } else {
            if ("HADIR".equals(dataList.get(position).getStatus())) {
                holder.teKanan.setText("HADIR");
                holder.teKanan.setBackgroundColor(Color.GREEN);
                holder.teKanan.setTextColor(Color.BLACK);
            } else if ("DIGANTIKAN".equals(dataList.get(position).getStatus())) {
                holder.teKanan.setText("DIGANTIKAN");
                holder.teKanan.setBackgroundColor(Color.BLUE);
                holder.teKanan.setTextColor(Color.WHITE);
            } else {
                holder.teKanan.setText("KOSONG");
                holder.teKanan.setBackgroundColor(Color.YELLOW);
                holder.teKanan.setTextColor(Color.BLACK);
            }
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class KehadiranViewHolder extends RecyclerView.ViewHolder {

        TextView teKiri, teKanan, teTopik;

        public KehadiranViewHolder(@NonNull View itemView) {
            super(itemView);
            teKiri = itemView.findViewById(R.id.teKehadiran_kiri);
            teKanan = itemView.findViewById(R.id.teKehadiran_kanan);
            teTopik = itemView.findViewById(R.id.teKehadiran_topik);
        }
    }
}
