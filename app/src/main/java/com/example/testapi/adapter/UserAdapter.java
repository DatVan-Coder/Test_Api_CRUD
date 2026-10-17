package com.example.testapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapi.R;
import com.example.testapi.handle.Item_HoaDon_handle;
import com.example.testapi.models.HoaDon;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context context;
    private ArrayList<HoaDon> ds;
    private Item_HoaDon_handle handle;

    public UserAdapter(Context context, ArrayList<HoaDon> ds, Item_HoaDon_handle handle) {
        this.context = context;
        this.ds = ds;
        this.handle = handle;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        HoaDon hd = ds.get(position);
        holder.tvMaHD.setText(hd.getMaHD());
        holder.tvTenHD.setText(hd.getTenHD());
        holder.tvTenSP.setText(hd.getTenSP());
        holder.tvSLuong.setText(hd.getSLuong());
        holder.tvGia.setText(hd.getGia());

        holder.btnEdit.setOnClickListener(v -> handle.onEdit(position));
        holder.btnDelete.setOnClickListener(v -> handle.onDelete(position));

    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHD, tvTenHD, tvTenSP, tvSLuong, tvGia;
        Button btnEdit, btnDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvTenHD = itemView.findViewById(R.id.tvTenHD);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvSLuong = itemView.findViewById(R.id.tvSLuong);
            tvGia = itemView.findViewById(R.id.tvGia);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById((R.id.btnDelete));
        }
    }
}
