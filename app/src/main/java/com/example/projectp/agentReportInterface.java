package com.example.projectp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class agentReportInterface extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ArrayList<AgentData> agentList;
    private AgentAdapter agentAdapter;
    private RecyclerView agentRecy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_report_interface);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait....... ");

        agentRecy = findViewById(R.id.agentRecy);
        agentRecy.setLayoutManager(new LinearLayoutManager(this));
        agentList = new ArrayList<>();
        agentAdapter = new AgentAdapter(this, agentList);
        agentRecy.setAdapter(agentAdapter);


        progressDialog.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refOwner = db.getReference("Government_report");
        refOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                progressDialog.dismiss();
                agentList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String name = messageSnapshot.child("Name").getValue(String.class);
                    String subject = messageSnapshot.child("Subject").getValue(String.class);
                    String message = messageSnapshot.child("Message").getValue(String.class);
                    agentList.add(new AgentData(name,subject, message));
                }
                agentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(agentReportInterface.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}