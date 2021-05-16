package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView logPage=findViewById(R.id.logPage);
        logPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        final EditText userEmail = findViewById(R.id.userEmail);
        final EditText userPassword = findViewById(R.id.userPassword);
        final EditText userMobile=findViewById(R.id.userMobile);
        final EditText userName=findViewById(R.id.userName);

        Button regButton = findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Log.i("ASS","click");
        rootNode= FirebaseDatabase.getInstance();
        reference =rootNode.getReference("users");




                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                firebaseAuth = FirebaseAuth.getInstance(); //need firebase authentication instance

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.i("SUCCESS", "New user registration: " + task.isSuccessful());

                                if (!task.isSuccessful()) {

                                    RegisterActivity.this.showToast("Authentication failed. " + task.getException());
                                } else {
                                    String name= userName.getText().toString();
                                    String email1= userEmail.getText().toString();
                                    String phone= userMobile.getText().toString();
                                    UserHelperClass helperClass = new UserHelperClass(name,email1,phone);
                                    reference.child(firebaseAuth.getCurrentUser().getUid().toString()).setValue(helperClass);
                                    RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    RegisterActivity.this.finish();
                                }
                            }
                        });
            }
        });

    }

    private void showToast(String s) {
        Log.i("SUCCEESSS", s);
    }
}

