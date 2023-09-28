package com.example.projectp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.AgentViewHolder> {

    private Context context;
    private List<AgentData> agentList;

    public AgentAdapter(Context context, List<AgentData> agentList) {
        this.context = context;
        this.agentList = agentList;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_recy, parent, false);
        return new AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position) {
        AgentData agentItem = agentList.get(position);
        holder.pName.setText(agentItem.getName());
        holder.pSubject.setText(agentItem.getSubject());
        holder.pMessage.setText(agentItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return agentList.size();
    }

    static class AgentViewHolder extends RecyclerView.ViewHolder {
        TextView pName;
        TextView pSubject;
        TextView pMessage;

        public AgentViewHolder(@NonNull View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.pName);
            pSubject = itemView.findViewById(R.id.pSubject);
            pMessage = itemView.findViewById(R.id.pMessage);
        }
    }
}

