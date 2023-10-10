package com.eadsliit.travel_manager_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText nicEditText;
    private EditText mobileEditText;
    private Button updateProfileButton;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.editTextEmail);
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        nicEditText = findViewById(R.id.editTextNIC);
        mobileEditText = findViewById(R.id.editTextMobile);
        updateProfileButton = findViewById(R.id.updateProfileButton);

        // Get the current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // Set current values in EditText fields (optional)
            emailEditText.setText(user.getEmail());
            firstNameEditText.setText(user.getDisplayName());

            updateProfileButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get updated values from EditText fields
                    String newEmail = emailEditText.getText().toString().trim();
                    String newFirstName = firstNameEditText.getText().toString().trim();
                    String newLastName = lastNameEditText.getText().toString().trim();
                    String newNic = nicEditText.getText().toString().trim();
                    String newMobile = mobileEditText.getText().toString().trim();

                    // Update the user's email in Firebase Auth
                    user.updateEmail(newEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Email update successful
                                        // You can also update other profile fields as needed
                                        updateUserInFirestore(user.getUid(), newFirstName, newLastName, newNic, newMobile);

                                        Toast.makeText(EditProfileActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Email update failed
                                        Toast.makeText(EditProfileActivity.this, "Failed to update email.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
    }

    private void updateUserInFirestore(String userId, String newFirstName, String newLastName, String newNic, String newMobile) {
        // Update the user's data in Firestore (assuming you have a 'users' collection)
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstName", newFirstName);
        updates.put("lastName", newLastName);
        updates.put("nic", newNic);
        updates.put("mobile", newMobile);

        firestore.collection("users").document(userId)
                .update(updates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Firestore update successful
                            // You can add more actions or handle success as needed
                        } else {
                            // Firestore update failed
                            // Handle the failure
                        }
                    }
                });
    }
}