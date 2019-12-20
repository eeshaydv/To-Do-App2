package com.android.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    public EditText signup_email, signup_password;
    Button signup_button;
    TextView tv_signin;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_button = findViewById(R.id.signup_button);
        tv_signin = findViewById(R.id.tv_signin);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailID = signup_email.getText().toString();
                String password = signup_password.getText().toString();
                if (emailID.isEmpty()) {
                    signup_email.setError("Provide your Email first!");
                    signup_email.requestFocus();
                } else if (password.isEmpty()) {
                    signup_password.setError("Set your password!");
                    signup_password.requestFocus();
                } else if (!(emailID.isEmpty() && password.isEmpty())) {

                    firebaseAuth.createUserWithEmailAndPassword(emailID, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this.getApplicationContext(),
                                        "SignUp Successful:" + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            }
                        }
                    });

                } else {
                    Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(I);
            }
        });


    }


}

