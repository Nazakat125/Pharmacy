package com.example.projectp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

public class pharmacy_login extends AppCompatActivity {
    EditText phoneNumber;
    EditText password;
    Button login;
    Button signin;
    TextView eMessage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_login);
        signin = findViewById(R.id.singUpBtn);
        phoneNumber = findViewById(R.id.loginNumber);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginBtn);

        eMessage = findViewById(R.id.LogINerror);
        //progressDialog is the loading screen
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        //signIn button as reffred by name is use to navigate to registration screen
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pharmacy_login.this,ph_registration.class);
                startActivity(intent);
            }
        });
        //Login button as reffred by name is use to navigate
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setting data into variables
                String ph = phoneNumber.getText().toString();
                String pass = password.getText().toString();
                //applying conditons
                if (ph.isEmpty() && pass.isEmpty()){

                    eMessage.setVisibility(View.VISIBLE);
                    if(ph.isEmpty()){
                        phoneNumber.setError("Enter your Phone Number");
                    }
                    if(pass.isEmpty()){
                        password.setError("Enter your Password");
                    }
                }else{
                    progressDialog.show();
                    //CALLING THE FIREABSE DATABSE
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    // MAKING REFERENCE TO OUR DATABASE
                    DatabaseReference myRef = database.getReference(ph).child("Personal Detail");
                    //we are using the realtime databse if in database data is change in realtime then we use this
                    //method to get data
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //getting data from the database
                            String password = snapshot.child("Password").getValue(String.class);
                            String name = snapshot.child("Name").getValue(String.class);
                            String pharmacyName = snapshot.child("Pharmacy_Name").getValue(String.class);
                            //applying condion to confirm that password is correct or not
                            if(pass.equals(password)){
                                progressDialog.dismiss();
                                Intent inte = new Intent(pharmacy_login.this,pharmacyInterface.class);
                                inte.putExtra("number",ph);
                                inte.putExtra("pharmacyName",pharmacyName);
                                FancyToast.makeText(pharmacy_login.this, "Welcome " + name, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                startActivity(inte);
                            }else{
                                progressDialog.dismiss();
                                eMessage.setText("Invalid Password");
                                eMessage.setVisibility(View.VISIBLE);
                                Log.d("PasswordComparison", "User-entered password: " + pass);
                                Log.d("PasswordComparison", "Database password: " + password);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            FancyToast.makeText(pharmacy_login.this, error.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                        }
                    });

                }

            }
        });


    }
}