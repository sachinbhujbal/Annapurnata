package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText Fullname, Email,DOB, Mobile,Password,Confirm_password;
    ProgressBar progressBar;
    Button register;
    RadioGroup radioGroupGender;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");

        Toast.makeText(this, "You Can Register Now.", Toast.LENGTH_SHORT).show();

        Fullname=findViewById(R.id.edit_fullName);
        Email=findViewById(R.id.edit_Email);
        DOB=findViewById(R.id.edit_Birth);
        Mobile=findViewById(R.id.edit_Mobile);
        Password=findViewById(R.id.edit_Password);
        Confirm_password=findViewById(R.id.Reedit_Password);

        progressBar=findViewById(R.id.progress_circular);

        register=findViewById(R.id.register_btn);

        radioGroupGender=findViewById(R.id.radio_id);
        radioGroupGender.clearCheck();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectGenderId=radioGroupGender.getCheckedRadioButtonId();
                radioButton=findViewById(selectGenderId);

                String fullname=Fullname.getText().toString();
                String email=Email.getText().toString();
                String dob=DOB.getText().toString();
                String mobile_no=Mobile.getText().toString();
                String password=Password.getText().toString();
                String Re_Password=Confirm_password.getText().toString();
                String gender;

                if(TextUtils.isEmpty(fullname)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Name",
                            Toast.LENGTH_SHORT).show();
                    Fullname.setError("Kindly Enter Full Name");
                    Fullname.requestFocus();
                }

               else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Email-Id",
                            Toast.LENGTH_SHORT).show();
                    Email.setError("Kindly Enter Email");
                    Email.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please Re-Enter Email-Id",
                            Toast.LENGTH_SHORT).show();
                    Email.setError("Neat Mail tak madarchod");
                    Email.requestFocus();

                }
               else if(TextUtils.isEmpty(dob)){
                    Toast.makeText(RegisterActivity.this, "Please Enter DOB",
                            Toast.LENGTH_SHORT).show();
                    Email.setError("Kindly Enter DOB (dd/mm/yyyy)");
                    Email.requestFocus();
                }
               else if (radioGroupGender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(RegisterActivity.this, "Please Select Gender",
                            Toast.LENGTH_SHORT).show();
                    radioButton.setError("Unselected Gender");
                    radioButton.requestFocus();
                }
               else if(TextUtils.isEmpty(mobile_no)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Mobile No",
                            Toast.LENGTH_SHORT).show();
                    Mobile.setError("Kindly Enter Mobile No");
                    Mobile.requestFocus();
                } else if (mobile_no.length()!=10) {
                    Toast.makeText(RegisterActivity.this, "Please Enter Mobile No",
                            Toast.LENGTH_SHORT).show();
                    Mobile.setError("10 digit Mobile No re ratalya");
                    Mobile.requestFocus();

                }
               else if(TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Please Enter Password",
                            Toast.LENGTH_SHORT).show();
                    Password.setError("Kindly Enter Password");
                    Password.requestFocus();
                } else if (password.length()<6) {
                    Toast.makeText(RegisterActivity.this, "Passwrod must 6 digit",
                            Toast.LENGTH_SHORT).show();
                    Password.setError("Kindly Enter 6-digit Password");
                    Password.requestFocus();

                }
               else if(TextUtils.isEmpty(Re_Password)){
                    Toast.makeText(RegisterActivity.this, "Please Enter your Confirm Password",
                            Toast.LENGTH_SHORT).show();
                    Confirm_password.setError("Please Confirm it");
                    Confirm_password.requestFocus();
                } else if (!password.equals(Re_Password)) {
                    Toast.makeText(RegisterActivity.this, "Please Re-Enter correct ",
                            Toast.LENGTH_SHORT).show();
                    Confirm_password.setError("Passowrd not matches");
                    Confirm_password.requestFocus();

                    Password.clearComposingText();
                    Confirm_password.clearComposingText();

                }
               else {
                    gender=radioButton.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);

                    registerUser(fullname,email,dob,gender,mobile_no,password,Re_Password);

                }
            }
        });

    }

    // Register user using crediantial
    private void registerUser(String fullname, String email, String dob, String gender,
                              String mobileNo, String password, String rePassword) {
        FirebaseAuth auth =FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Registered Sucessfully",
                            Toast.LENGTH_SHORT).show();

                    FirebaseUser firebaseUser=auth.getCurrentUser();

                    // Sent E-mail Verification.
                    firebaseUser.sendEmailVerification();

                    // Open User Profile
             //      Intent intent=new Intent(RegisterActivity.this,UserProfileActivity.class);
                     /* want to go on new activity through previous one by clearing flag
                    to prevent user from returing back from register activity on pressing back after sucessfully
                    registeration */
             /*       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                    Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();       */


                }
            }
        });



    }
}