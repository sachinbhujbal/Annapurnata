package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPassword;
    private Button login;

    private FirebaseAuth userAuthProfile;
    private ProgressBar progressBar;
    private static final String TAG="LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");

        editTextLoginEmail=findViewById(R.id.edit_Email_login);
        editTextLoginPassword=findViewById(R.id.edit_pwd);

        progressBar=findViewById(R.id.progress_login);

        login=findViewById(R.id.login_btn);
        userAuthProfile=FirebaseAuth.getInstance();

        // Show Hide Password using eye icon
        ImageView img_ViewShow_Hide_pwd=findViewById(R.id.pwd_hide_unhide_eye);
        img_ViewShow_Hide_pwd.setImageResource(R.drawable.ic_hide_pwd);

        img_ViewShow_Hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLoginPassword.getTransformationMethod().
                        equals(HideReturnsTransformationMethod.getInstance())){
                    // if password is visible then hide it;
                    editTextLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_ViewShow_Hide_pwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_ViewShow_Hide_pwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=editTextLoginEmail.getText().toString();
                String textPassword=editTextLoginPassword.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
//                    Toast.makeText(LoginActivity.this, "Please Enter Registered E-mail ", Toast.LENGTH_SHORT).show();
                    editTextLoginEmail.setError("Enter Registered Mail");
                    editTextLoginEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    editTextLoginEmail.setError("Cheak Mail Format");
                    editTextLoginEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(textPassword)){

                    editTextLoginPassword.setError("Password is required!");
                    editTextLoginPassword.requestFocus();
                } else if (textPassword.length()<6) {
                    editTextLoginPassword.setError("Kindly Enter 6-digit Password and it was registered!");
                    editTextLoginPassword.requestFocus();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPassword);
                }
            }
        });


    }

    private void loginUser(String textEmail, String textPassword) {

        userAuthProfile.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener( LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "You'r Logged in now", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextLoginEmail.setError("Invalid Credential Please Try again");
                        editTextLoginEmail.requestFocus();
                    } catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("Invalid Login May User Does not exist");
                        editTextLoginEmail.requestFocus();
                    }
                    catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}