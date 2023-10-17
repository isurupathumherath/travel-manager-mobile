package com.eadsliit.travel_manager_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        // Get user input from EditText fields
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {

                if (emailEditText.getText() != null) {
                    // Get the user's ID
                    String emailAddress = emailEditText.getText().toString();

                    System.out.println("Email Address: " + emailAddress);
                }

                try {
                    // Define the URL and request body
                    String apiUrlRegister = "https://eafd-116-206-246-73.ngrok-free.app/api/User/login";

                    String requestBodyRegister = "{\n" +
                            "  \"id\": {},\n" +
                            "  \"username\": \"" + emailEditText.getText() + "\",\n" +
                            "  \"password\": \" " + passwordEditText.getText() + "\",\n" +
                            "}";

                    URL urlLogin = new URL(apiUrlRegister);
                    HttpURLConnection connection = (HttpURLConnection) urlLogin.openConnection();

                    // Set up the HTTP request
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setDoOutput(true);

                    // Write the request body to the connection
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = requestBodyRegister.getBytes("UTF-8");
                        os.write(input, 0, input.length);
                    }

                    // Get the HTTP response code
                    int responseCode = connection.getResponseCode();

                    // Check if the user was created successfully (usually HTTP status 200)
                    return responseCode == HttpURLConnection.HTTP_OK;

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean userCreatedSuccessfully) {
                if (userCreatedSuccessfully) {
                    // User created successfully
                    Log.d("CreateUserActivity", "User Login successfully");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // Handle the error
                    Log.e("CreateUserActivity", "Failed to Login");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        }

        // Login
        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateUserTask().execute();
            }
        });

        // Go to Register
        Button registerBtn = findViewById(R.id.buttonRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the main activity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


    }
}