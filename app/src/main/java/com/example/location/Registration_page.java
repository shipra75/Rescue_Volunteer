package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration_page extends AppCompatActivity {
EditText mfullname,memail,mpassword,mphone;
Button mregisterbtn;
TextView mloginbtn;
FirebaseAuth fauth;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
mfullname=findViewById(R.id.PersonName);
memail=findViewById(R.id.Emailid);
mpassword=findViewById(R.id.password);
mphone=findViewById(R.id.phonenumber);
mregisterbtn=findViewById(R.id.registerbutton);
    mloginbtn=findViewById(R.id.logintext);
    progressBar=findViewById(R.id.progressbar);
    fauth=FirebaseAuth.getInstance();
   if(fauth.getCurrentUser()!=null){
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
        finish();
    }
    mregisterbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = memail.getText().toString().trim();
            String password = mpassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                memail.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mpassword.setError("Password is required");
                return;
            }

            if(password.length()<6){
                mpassword.setError("password should be >= 6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registration_page.this,"User Created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                    finish();
                    }
                    else{
                        Toast.makeText(Registration_page.this,"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    });
mloginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),Login_Page.class));
    }
});
    }
}