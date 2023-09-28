package com.example.projectp;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Message extends Fragment {
    private EditText messageEdit;
    private ImageButton sendBtn;
    private RecyclerView chatRecy;
    private String phone;
    private String pharmacyName;

    private messageAdapter chatAdapter;
    private ArrayList<messageData> list;
    private ProgressDialog progressDialog;

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait....... ");

        phone = getArguments().getString("number");
        pharmacyName = getArguments().getString("pharmacyName");

        init(view);

        chatRecy.setLayoutManager(new LinearLayoutManager(requireContext()));
        list = new ArrayList<>();
        chatAdapter = new messageAdapter(requireContext(), list);
        chatRecy.setAdapter(chatAdapter);

        progressDialog.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        db.getReference("Messages").child("All Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        list.clear();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            String message = messageSnapshot.child("Message").getValue(String.class);
                            Boolean isSend = messageSnapshot.child("isSend").getValue(Boolean.class);
                            String time = messageSnapshot.child("Time").getValue(String.class);
                            String name = messageSnapshot.child("pharmacyName").getValue(String.class);
                            list.add(new messageData(message, isSend, time,name));
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
        db.getReference(phone).child("Messages").child("SendMessage")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        list.clear();
                        for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                            String message = messageSnapshot.child("Message").getValue(String.class);
                            Boolean isSend = messageSnapshot.child("isSend").getValue(Boolean.class);
                            String time = messageSnapshot.child("Time").getValue(String.class);
                            String name = messageSnapshot.child("pharmacyName").getValue(String.class);
                            list.add(new messageData(message, isSend, time,name));
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });




        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEdit.getText().toString();
                if (!message.isEmpty()) {
                    // Get current time in milliseconds
                    long sendTime = System.currentTimeMillis();

                    // Define the desired time format in 12-hour format without AM/PM indicator
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
                    // Format the current time using the specified format
                    String currentTime = timeFormat.format(sendTime);


                    HashMap<String, Object> reciveDate = new HashMap<>();
                    reciveDate.put("Message", message);
                    reciveDate.put("Time", currentTime);
                    reciveDate.put("isSend", true);
                    reciveDate.put("pharmacyName", pharmacyName);


                    HashMap<String, Object> sendDate = new HashMap<>();
                    sendDate.put("Message", message);
                    sendDate.put("Time", currentTime);
                    sendDate.put("isSend", false);
                    sendDate.put("pharmacyName", pharmacyName);
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    db.getReference("Messages").child("All Chat").push().setValue(sendDate)
                            .addOnSuccessListener(aVoid -> {
                                messageEdit.getText().clear();
                                db.getReference(phone).child("Messages").child("SendMessage").push().setValue(reciveDate);
                            })
                            .addOnFailureListener(e -> Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show());
                }
            }
        });

        return view;
    }

    private void init(View view) {
        messageEdit = view.findViewById(R.id.chatEdit);
        sendBtn = view.findViewById(R.id.chatBtn);
        chatRecy = view.findViewById(R.id.chat_recy);
    }
}
