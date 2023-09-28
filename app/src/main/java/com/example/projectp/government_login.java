package com.example.projectp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class government_login extends AppCompatActivity {
    EditText agentName;
    EditText agentToken;
    EditText agentPaasword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_login);

        agentName = findViewById(R.id.agentName);
        agentToken = findViewById(R.id.agentToken);
        agentPaasword = findViewById(R.id.agentPassword);
        login = findViewById(R.id.agentLoginBtn);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = agentName.getText().toString();
                String token = agentToken.getText().toString();
                String password = agentPaasword.getText().toString();

                if (name.isEmpty() && token.isEmpty() && password.isEmpty()) {

                    if (name.isEmpty()) {
                        agentName.setError("Enter your Name");
                    }
                    if (token.isEmpty()) {
                        agentToken.setError("Enter your Token Number");
                    }
                    if (password.isEmpty()) {
                        agentPaasword.setError("Enter your Password");
                    }
                }
                else{
                        progressDialog.show();
                        //CALLING THE FIREABSE DATABSE
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        // MAKING REFERENCE TO OUR DATABASE
                        DatabaseReference myRef = database.getReference("Agent");
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //getting data from the database
                                String na = snapshot.child("Name").getValue(String.class);
                                String tk = snapshot.child("Token").getValue(String.class);
                                String ps = snapshot.child("Password").getValue(String.class);
                                //applying condion to confirm that password is correct or not
                                if(name.equals(na) && tk.equals(token) && ps.equals(password)){
                                    progressDialog.dismiss();
                                    Intent inte = new Intent(government_login.this,agentReportInterface.class);
                                    FancyToast.makeText(government_login.this, "Welcome " + name, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                    startActivity(inte);

                                }else{
                                    FancyToast.makeText(government_login.this, "Invalid Information", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        if (!name.equals(na)){
                                            progressDialog.dismiss();
                                            FancyToast.makeText(government_login.this, "Invalid Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }
                                        if (!tk.equals(token)){
                                            progressDialog.dismiss();
                                            FancyToast.makeText(government_login.this, "Invalid Token", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }   if (!password.equals(ps)){
                                        progressDialog.dismiss();
                                            FancyToast.makeText(government_login.this, "Invalid Name", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                        }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                progressDialog.dismiss();
                                FancyToast.makeText(government_login.this, error.toString(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();

                            }
                        });
                    }

            }
        });
    }
}