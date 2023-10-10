package com.eadsliit.travel_manager_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText nicEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText mobileEditText;
    private Button registerButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Authentication and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Initialize EditText fields and Register button
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        nicEditText = findViewById(R.id.editTextNIC);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        mobileEditText = findViewById(R.id.editTextMobile);
        registerButton = findViewById(R.id.buttonRegister);

        // Handle registration button click
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input from EditText fields
                final String firstName = firstNameEditText.getText().toString().trim();
                final String lastName = lastNameEditText.getText().toString().trim();
                final String nic = nicEditText.getText().toString().trim();
                final String email = emailEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString();
                final String mobile = mobileEditText.getText().toString().trim();

                // Perform basic validation here (e.g., check if fields are not empty)

                // Create a new user in Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // User registration successful
                                    // Get the user's unique ID
                                    String userId = firebaseAuth.getCurrentUser().getUid();

                                    // Create a user document in Firestore
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("firstName", firstName);
                                    user.put("lastName", lastName);
                                    user.put("nic", nic);
                                    user.put("email", email);
                                    user.put("mobile", mobile);

                                    firestore.collection("users").document(userId)
                                            .set(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> firestoreTask) {
                                                    if (firestoreTask.isSuccessful()) {
                                                        // Firestore document creation successful
                                                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                                        // You can add more actions here, e.g., navigate to the main activity
                                                    } else {
                                                        // Firestore document creation failed
                                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    // User registration failed
                                    Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}