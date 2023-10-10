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

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private Button btnLogout;
    private Button btnUpdateProfile;
    private FirebaseAuth firebaseAuth;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        btnLogout = findViewById(R.id.btnLogout);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);

        welcomeTextView = findViewById(R.id.textViewWelcome);

        // Retrieve the current user's information
        FirebaseUser user = firebaseAuth.getCurrentUser();
        System.out.println("USER ID: " + user);

        if (user != null) {
            String firstName = user.getDisplayName();
            System.out.println("WELCOME" + firstName);

            // Display a welcome message with the user's First Name
            welcomeTextView.setText("Welcome, " + firstName + "!");
        } else {
            // Display a welcome message with the user's First Name
            welcomeTextView.setText("NO USER !");
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
}