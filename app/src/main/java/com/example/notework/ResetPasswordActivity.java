package com.example.notework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText emailEditText;
    Button resetPassButton;
    ProgressBar progressBar;
    TextView loginBtnTextView;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailEditText=findViewById(R.id.email_edit_text1);
        resetPassButton=findViewById(R.id.reset_pass_btn);
        progressBar=findViewById(R.id.progress_bar1);
        loginBtnTextView=findViewById(R.id.login_activity_btn);

        resetPassButton.setOnClickListener((v)->resetPassword());
        loginBtnTextView.setOnClickListener((v)->startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class)) );
    }

    void resetPassword(){
        changeInProgress(true);
        if(!emailEditText.getText().toString().isEmpty())
            auth.sendPasswordResetEmail(emailEditText.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            changeInProgress(false);
                            Toast.makeText(ResetPasswordActivity.this, "Password reset email has been sent.", Toast.LENGTH_SHORT).show();
                            new Intent(ResetPasswordActivity.this,LoginActivity.class);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            changeInProgress(false);
                            Toast.makeText(ResetPasswordActivity.this, "Password reset failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        else{
            changeInProgress(false);
            Toast.makeText(ResetPasswordActivity.this, "Missing fields identified.", Toast.LENGTH_SHORT).show();
        }
    }

    void changeInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            resetPassButton.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            resetPassButton.setVisibility(View.VISIBLE);
        }
    }
}