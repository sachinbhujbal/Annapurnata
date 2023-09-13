package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText Fullname, Email,DOB, Mobile,Password,Confirm_password;
    ProgressBar progressBar;
    Button register;
    RadioGroup radioGroupGender;
    RadioButton radioButton;
    private DatePickerDialog picker;
    private static final String TAG="RegisterActivity";
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

        // setteing up datepicker on editTextDOB
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                //  Date Picker Dialog
                picker=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DOB.setText(dayOfMonth +"/"+(month+1)+"/"+year);

                    }
                },year,month,day);
                picker.show();
            }
        });


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

                // Validate mobile number using matcher and pattern (Regular Expression)
                String mobileRegx="[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePatern=Pattern.compile(mobileRegx);
                mobileMatcher=mobilePatern.matcher(mobile_no);
                



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
                    DOB.setError("Kindly Enter DOB (dd/mm/yyyy)");
                    DOB.requestFocus();
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
                    Mobile.setError("10 digit Mobile No be Must!");
                    Mobile.requestFocus();
                } else if (!mobileMatcher.find()) {
                    Toast.makeText(RegisterActivity.this, "Please RE-Enter Mobile No",
                            Toast.LENGTH_SHORT).show();
                    Mobile.setError("Must Start from 6 to 9");
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

        // create user profile
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User Registered Sucessfully",
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser=auth.getCurrentUser();

                    // Update Display name of user
                    UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder().
                                                                        setDisplayName(fullname).build();
                    firebaseUser.updateProfile(userProfileChangeRequest);

                    // Enter User data into the Firebase Real-time Database
                    ReadWriteUserDetails readWriteUserDetails=new ReadWriteUserDetails(email,dob,gender,mobileNo);

                    /* Extracting User reference from database for Registered User
                    for creating node through DatabaseRefereence as Registered User */
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Registered User");

                    databaseReference.child(firebaseUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                // Sent E-mail Verification.
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this,"User Registered Sucessfully! Please Cheak Mail",
                                                Toast.LENGTH_LONG).show();

                                // Open User Profile
                              //      Intent intent=new Intent(RegisterActivity.this,UserProfileActivity.class);
                                 /* want to go on new activity through previous one by clearing flag
                                to prevent user from returing back from register activity on pressing back after sucessfully
                                registeration */
                             /*       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                    Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                    finish();   */
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,"Registeration Failed Please try again",
                                        Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                else{
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e){
                        Password.setError("Your Password is to weak! Kindly use a mix of alphabate, character, and symbols");
                        Password.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        Email.setError("Your email is invalid or already use kindly Re-Enter it!");
                        Email.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        Email.setError("E-mail Already used");
                        Email.requestFocus();
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                    progressBar.setVisibility(View.GONE);

                }
            }
        });



    }
}