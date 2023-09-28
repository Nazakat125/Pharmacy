package com.example.projectp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;

public class ph_registration extends AppCompatActivity {
//Make the Variables
    EditText name;
    EditText pharmacy;
    EditText license;
    EditText address;
    EditText password;
    EditText repassword;
    EditText phone;
    Button done;
    TextView eMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Calling all the id
        setContentView(R.layout.activity_ph_registration);
        name = findViewById(R.id.userName);
        pharmacy = findViewById(R.id.userCompanyName);
        license = findViewById(R.id.lisence);
        password = findViewById(R.id.userPassword);
        repassword = findViewById(R.id.userRePassword);
        address = findViewById(R.id.userAddress);
        done = findViewById(R.id.doneBtn);
        phone = findViewById(R.id.userPhone);
        eMassage = findViewById(R.id.error);
        //progressDialog is the loading screen
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        //What action will be performed on cliking on the done button
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting text from the EditText and set into a Variable
                String  na  = name.getText().toString();
                String  pah  = pharmacy.getText().toString();
                String  li  = license.getText().toString();
                String  add  = address.getText().toString();
                String  pass  = password.getText().toString();
                String  re  = repassword.getText().toString();
                String  ph  = phone.getText().toString();
                // Calling condition to check any EditText is Emputy or not
                if(na.isEmpty() && pah.isEmpty() &&li.isEmpty() &&add.isEmpty()
                        &&pass.isEmpty() && re.isEmpty() && ph.isEmpty()){
                    // If Some Fiald is Emputy show the Error
                    eMassage.setVisibility(View.VISIBLE);
                if(na.isEmpty()){
                    name.setError("Enter your Name");
                }
                if(pah.isEmpty()){
                    pharmacy.setError("Enter your Pharmacy Name");
                }
                if(li.isEmpty()){
                    license.setError("Enter your License Number");
                }
                if(add.isEmpty()){
                    address.setError("Enter your Address");
                }
                if(pass.isEmpty()){
                    password.setError("Make Your Password");
                }
                if(re.isEmpty()){
                    repassword.setError("Enter Same Password Again");
                }
                if(ph.isEmpty()){
                    phone.setError("Enter Your Phone Number");
                }

                }else{
                    //Checking password and repassword have same values or not
                    if (pass.equals(re)){
                        //CALLING THE FIREABSE DATABSE
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        // MAKING REFERENCE TO OUR DATABASE
                        DatabaseReference myRef = database.getReference(ph).child("Personal Detail");
                        // SETTING ALL THE DATA INTO A HASHMAP
                        HashMap<String, String> data = new HashMap<>();
                        data.put("Name",na);
                        data.put("Pharmacy_Name",pah);
                        data.put("License",li);
                        data.put("Phone",ph);
                        data.put("Re_Password",re);
                        data.put("Password",pass);
                        data.put("Addres",re);
                        progressDialog.show();

                        DatabaseReference agentDetail = database.getReference("Agent");
                        HashMap<String, String> agentData = new HashMap<>();
                        agentData.put("Name","Nazakat Hussain");
                        agentData.put("Token","1214");
                        agentData.put("Password","124578");

                        //Inserting the data into database
                        myRef.setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                agentDetail.setValue(agentData);
                                //When data is sccressfully submitted into databse
                                Intent inten = new Intent(ph_registration.this,pharmacy_login.class);
                                progressDialog.dismiss();
                                //Custom Toast
                                FancyToast.makeText(ph_registration.this, "Welcome " + na, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                startActivity(inten);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                //when data is not inserted into database
                                FancyToast.makeText(ph_registration.this, e.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();


                            }
                        });
                    }else{
                        //if password and repassword have not same values show the error
                        eMassage.setVisibility(View.VISIBLE);
                        eMassage.setText("Password and RePassword Is not Same");
                    }
                }


                }
        });

    }
}