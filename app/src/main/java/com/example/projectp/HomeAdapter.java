package com.example.projectp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<homeData> dataList;
    private Context context;

    public HomeAdapter(Context context, List<homeData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.med_recy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        homeData item = dataList.get(position);

        holder.medName.setText(item.getMedicineName());
        holder.medQuintiy.setText(item.getQuantity());

        holder.editButton.setOnClickListener(v -> {
            // Handle edit button click here
            // Show the popup and populate with data from 'item'
            FancyToast.makeText(context, item.getMedicineName() + " clicked", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            // Implement your popup logic here
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView medName;
        TextView medQuintiy;
        Button editButton;

        ViewHolder(View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.medName);
            medQuintiy = itemView.findViewById(R.id.medQuintiy);
            editButton = itemView.findViewById(R.id.med_editBtn);
        }
    }
}
