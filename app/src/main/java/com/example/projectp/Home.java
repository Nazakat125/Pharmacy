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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;


public class Home extends Fragment {
    private ProgressDialog progressDialog;
    private ArrayList<homeData> homeList;
    private HomeAdapter homeAdapter;
    private RecyclerView homerecy;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        String phone = getArguments().getString("number");


        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait....... ");


        homerecy = view.findViewById(R.id.homeRecy);
        homerecy.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeList = new ArrayList<>();
        homeAdapter = new HomeAdapter(requireContext(), homeList);
        homerecy.setAdapter(homeAdapter);

        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(phone).child("Medicine Detail");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                homeList.clear();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    String medName = messageSnapshot.child("Medicine_Name").getValue(String.class);
                    String medQuntity = messageSnapshot.child("Medicine_Quntity").getValue(String.class);
                    homeList.add(new homeData(medName, medQuntity));
                }
                homeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });









        ImageButton addButton = view.findViewById(R.id.addMedicine);


        View customDialog = LayoutInflater.from(requireContext()).inflate(R.layout.add_medicine,null);
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(requireContext());
        alterDialog.setView(customDialog);

        ImageButton dialogCanccelBtn = customDialog.findViewById(R.id.dialogCloseBtn);
        EditText dialogMedicineName = customDialog.findViewById(R.id.medicineName);
        EditText dialogMedicineQuntiy = customDialog.findViewById(R.id.medicinequantity);
        CheckBox dialogMedicineAvalible = customDialog.findViewById(R.id.medicineAvalible);
        CheckBox dialogMedicineUnavalible= customDialog.findViewById(R.id.medicineUnavalible);
        Button dialogDoneBtn= customDialog.findViewById(R.id.dialogDoneBtn);
        TextInputLayout dialogMedicineQuntiyLayout = customDialog.findViewById(R.id.medicinequantityLayout);



        final AlertDialog dialog = alterDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

                dialogCanccelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialogMedicineName.setText("");
                        dialogMedicineQuntiy.setText("");

                    }
                });



                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference(phone).child("Medicine Detail").push();

                dialogMedicineAvalible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            dialogMedicineUnavalible.setChecked(false); // Uncheck the "Unavailable" checkbox
                            dialogMedicineQuntiyLayout.setVisibility(View.VISIBLE);

                            dialogDoneBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String name = dialogMedicineName.getText().toString();
                                    String qunantity = dialogMedicineQuntiy.getText().toString();

                                    dialogMedicineQuntiyLayout.setVisibility(View.VISIBLE);


                                    HashMap<String, String> data = new HashMap<>();
                                    data.put("Medicine_Name", name);
                                    data.put("Medicine_Quntity", qunantity);
                                    myRef.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogMedicineName.setText("");
                                            dialogMedicineQuntiy.setText("");
                                            FancyToast.makeText(requireContext(), name + " Added", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialogMedicineName.setText("");
                                            dialogMedicineQuntiy.setText("");
                                            FancyToast.makeText(requireContext(), e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }
                                    });
                                }
                            });
                        } else {
                            dialogMedicineQuntiyLayout.setVisibility(View.GONE);
                        }
                    }
                });

                dialogMedicineUnavalible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            dialogMedicineAvalible.setChecked(false); // Uncheck the "Available" checkbox
                            dialogMedicineQuntiyLayout.setVisibility(View.GONE);



                            dialogDoneBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String name = dialogMedicineName.getText().toString();
                                    HashMap<String, String> data = new HashMap<>();
                                    data.put("Medicine_Name", name);
                                    data.put("Medicine_Quntity", "Out Of Stock");
                                    myRef.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogMedicineName.setText("");
                                            FancyToast.makeText(requireContext(), name + " Added", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialogMedicineName.setText("");
                                            FancyToast.makeText(requireContext(), e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }
                                    });
                                }
                            });
                        }
                    }
                });

            }
        });






        return view;
    }
}