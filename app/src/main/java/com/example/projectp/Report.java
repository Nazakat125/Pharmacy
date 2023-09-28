package com.example.projectp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;

public class Report extends Fragment {

ImageButton reportBtn;

    private ProgressDialog progressDialog;
    private ArrayList<reportData> reportlist;
    private ReportAdapter reportAdapter;
    private RecyclerView reportRecy;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_report, container, false);

        String phone = getArguments().getString("number");
        String pharmacyName = getArguments().getString("pharmacyName");

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait....... ");


        reportRecy = view.findViewById(R.id.repotRect);
        reportRecy.setLayoutManager(new LinearLayoutManager(requireContext()));
        reportlist = new ArrayList<>();
        reportAdapter = new ReportAdapter(requireContext(), reportlist);
        reportRecy.setAdapter(reportAdapter);

        progressDialog.show();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference refOwner = db.getReference(phone).child("Government_report");
        refOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                progressDialog.dismiss();
                reportlist.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String subject = messageSnapshot.child("Subject").getValue(String.class);
                    String message = messageSnapshot.child("Message").getValue(String.class);
                    reportlist.add(new reportData(subject, message));
                }
                reportAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });











      reportBtn = view.findViewById(R.id.reportBtn);
        View customDialog = LayoutInflater.from(requireContext()).inflate(R.layout.reportxml,null);
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(requireContext());
        alterDialog.setView(customDialog);
        final AlertDialog dialog = alterDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton reportCanncleBtn = customDialog.findViewById(R.id.reportCloseBtn);
        EditText reportSubject = customDialog.findViewById(R.id.reportSubject);
        EditText reportMessage = customDialog.findViewById(R.id.reportMessage);
        Button reportDoneBtn = customDialog.findViewById(R.id.reportDone);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        reportCanncleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                reportMessage.setText("");
                reportSubject.setText("");
            }
        });


        reportDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = reportSubject.getText().toString();
                String message = reportMessage.getText().toString();
                if (!subject.isEmpty() && !message.isEmpty()){
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference ref = db.getReference("Government_report").push();
                    DatabaseReference refOwner = db.getReference(phone).child("Government_report").push();
                    HashMap<String, String> data = new HashMap<>();
                    data.put("Name", pharmacyName);
                    data.put("Subject", subject);
                    data.put("Message", message);
                    ref.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            refOwner.setValue(data);
                            dialog.dismiss();
                            reportMessage.setText("");
                            reportSubject.setText("");
                            FancyToast.makeText(requireContext(),   " Your report has been sent", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            FancyToast.makeText(requireContext(), e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }
                    });

                }else{

                    if(subject.isEmpty()){
                        reportSubject.setError("Write The Resson of Report");
                    }
                    if(message.isEmpty()){
                        reportMessage.setError("Write donw you message");
                    }
                }
            }
        });
        return view;
    }
}