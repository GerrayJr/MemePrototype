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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@SuppressWarnings("ALL")
public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edUsername, edEmail, edPhone, edPassword;
    TextView login;
    Button btnCreate;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        progressDialog = new ProgressDialog(this);

        edUsername = findViewById(R.id.create_user);
        edEmail = findViewById(R.id.create_email);
        edPhone = findViewById(R.id.create_phone);
        edPassword = findViewById(R.id.create_pass);
        btnCreate = findViewById(R.id.btn_create);
        login = findViewById(R.id.tv_login);

        btnCreate.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreate) {
//            registerUser();
            startActivity(new Intent(CreateActivity.this, LoginActivity.class));
        }
        if (v == login) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void registerUser() {
        final String username = edUsername.getText().toString().trim();
        final String email = edEmail.getText().toString().trim();
        final int phone = Integer.parseInt(edPhone.getText().toString().trim());
        String password = edPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
            return;

        }


        progressDialog.setMessage("Registering User....");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //Successful registration
                            Toast.makeText(CreateActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CreateActivity.this, LoginActivity.class));

                            InfoUser infoUser = new InfoUser(username, email, phone);
                            FirebaseUser user = auth.getCurrentUser();
                            databaseReference.child(user.getUid()).setValue(infoUser);

                        } else {
                            //Registration Failed
                            Toast.makeText(CreateActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
