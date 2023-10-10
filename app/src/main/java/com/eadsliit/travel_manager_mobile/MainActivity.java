package com.eadsliit.travel_manager_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private Button btnLogout;
    private Button btnUpdateProfile;
    private FirebaseAuth firebaseAuth;
    private TextView welcomeTextView;
    private FirebaseFirestore firestore;
    private ListenerRegistration userListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.logoutButton);
        btnUpdateProfile = findViewById(R.id.updateProfileButton);

        welcomeTextView = findViewById(R.id.textViewWelcome);

        // Initialize Firebase Auth and Firestore
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Get the user's ID from the Intent
        String userId = getIntent().getStringExtra("userId");

        // Retrieve the current user's information
        System.out.println("USER ID: " + userId);

        if (userId != null) {
            // Listen for changes to the user document in FireStore
            userListener = firestore.collection("users").document(userId)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                // Handle the error
                                return;
                            }

                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                // User document exists, fetch and display data
                                String firstName = documentSnapshot.getString("firstName");
                                String lastName = documentSnapshot.getString("lastName");

                                welcomeTextView.setText("Welcome, " + firstName + "!");
                                System.out.println("Welcome, " + firstName + "!");
                            }
                        }
                    });
        } else {
            // Display a welcome message with the user's First Name
            welcomeTextView.setText("NO USER ID FOUND!");
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a Logout message
                Toast.makeText(MainActivity.this, "You are logging out!", Toast.LENGTH_SHORT).show();


                // Sign out the current user
                firebaseAuth.signOut();

                // Redirect to the main activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to the update activity
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the Firestore listener when the activity is destroyed
        if (userListener != null) {
            userListener.remove();
        }
    }
}