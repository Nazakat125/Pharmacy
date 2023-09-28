package com.example.projectp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private Context context;
    private List<reportData> reportList;

    public ReportAdapter(Context context, List<reportData> reportList) {
        this.context = context;
        this.reportList = reportList;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_recy, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        reportData report = reportList.get(position);
        holder.recySubject.setText(report.getReportSubject());
        holder.recyMessage.setText(report.getReportMessage());
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView recySubject;
        TextView recyMessage;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            recySubject = itemView.findViewById(R.id.recySubject);
            recyMessage = itemView.findViewById(R.id.recyMessage);
        }
    }
}
