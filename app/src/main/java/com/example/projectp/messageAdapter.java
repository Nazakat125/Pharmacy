package com.example.projectp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class messageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SEND_MESSAGE = 1;
    private static final int RECEIVE_MESSAGE = 2;

    private Context context;
    private ArrayList<messageData> chatList;

    public messageAdapter(Context context, ArrayList<messageData> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SEND_MESSAGE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_message_recy, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recive_message_recy, parent, false);
            return new reciveViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        messageData message = chatList.get(position);
        if (holder.getItemViewType() == SEND_MESSAGE) {
            senderViewHolder senderHolder = (senderViewHolder) holder;
            senderHolder.sendMessage.setText(message.getMessage());
            senderHolder.sendTime.setText(message.getTime());
            senderHolder.sendName.setText(message.getName());
        } else {
            reciveViewHolder reciveHolder = (reciveViewHolder) holder;
            reciveHolder.reciveMessage.setText(message.getMessage());
            reciveHolder.reciveTime.setText(message.getTime());
            reciveHolder.reciveTime.setText(message.getTime());
        }
    }

    @Override
    public int getItemViewType(int position) {
        messageData currentMessage = chatList.get(position);
        if (currentMessage.getIsSend()) {
            return SEND_MESSAGE;
        } else {
            return RECEIVE_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class senderViewHolder extends RecyclerView.ViewHolder {
        TextView sendMessage;
        TextView sendTime;
        TextView sendName;

        public senderViewHolder(View view) {
            super(view);
            sendMessage = view.findViewById(R.id.send_message);
            sendTime = view.findViewById(R.id.send_time);
            sendName = view.findViewById(R.id.send_name);
        }
    }

    public class reciveViewHolder extends RecyclerView.ViewHolder {
        TextView reciveMessage;
        TextView reciveTime;
        TextView reciveName;

        public reciveViewHolder(View view) {
            super(view);
            reciveMessage = view.findViewById(R.id.recive_message);
            reciveTime = view.findViewById(R.id.recive_time);
            reciveName = view.findViewById(R.id.recive_name);
        }
    }
}

