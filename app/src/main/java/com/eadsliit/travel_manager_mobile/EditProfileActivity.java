package com.eadsliit.travel_manager_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private Button btnUpdateProfile;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        // Retrieve the current user's information
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String firstName = user.getPhoneNumber();
            String email = user.getEmail();

            // Populate the EditText fields with the retrieved data
            editTextFirstName.setText(firstName);
            editTextEmail.setText(email);
        }

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the logic to update the user profile here
                // You can use Firebase Authentication's updateProfile method to update the user's profile information
                // Example:
                // String newFirstName = editTextFirstName.getText().toString();
                // UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                //         .setDisplayName(newFirstName)
                //         .build();
                //
                // user.updateProfile(profileUpdates)
                //         .addOnCompleteListener(new OnCompleteListener<Void>() {
                //             @Override
                //             public void onComplete(@NonNull Task<Void> task) {
                //                 if (task.isSuccessful()) {
                //                     // Profile updated successfully
                //                     finish(); // Close the activity after successful update
                //                 } else {
                //                     // Handle update failure
                //                 }
                //             }
                //         });
            }
        });
    }
}