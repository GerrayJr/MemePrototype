package com.gerray.memeprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_login;
    EditText edEmail, edPassword;
    TextView tvCreate;
    ProgressDialog progressDialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_pass);
        tvCreate = findViewById(R.id.tv_create);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tvCreate.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_login) {
//            loginUser();
            startActivity(new Intent(this, MainActivity.class));
        }
        if (v == tvCreate) {
            startActivity(new Intent(this, CreateActivity.class));
        }
    }

    private void loginUser() {
        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter Email!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password!!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            FirebaseUser user = auth.getCurrentUser();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
