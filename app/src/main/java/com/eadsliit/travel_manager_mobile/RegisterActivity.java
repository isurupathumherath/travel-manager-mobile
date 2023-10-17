package com.eadsliit.travel_manager_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText nicEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText mobileEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize EditText fields and Register button
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        nicEditText = findViewById(R.id.editTextNIC);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        mobileEditText = findViewById(R.id.editTextMobile);
        registerButton = findViewById(R.id.buttonRegister);

        // Get user input from EditText fields
        final String firstName = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String nic = nicEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString();
        final String mobile = mobileEditText.getText().toString().trim();

        class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Define the URL and request body
                    String apiUrlRegister = "https://eafd-116-206-246-73.ngrok-free.app/api/User/register";
                    String apiUrlRegisterDetails = "https://eafd-116-206-246-73.ngrok-free.app/api/Traveler";

                    String requestBodyRegister = "{\n" +
                            "  \"id\": {},\n" +
                            "  \"username\": \"" + emailEditText.getText() + "\",\n" +
                            "  \"password\": \" " + passwordEditText.getText() + "\",\n" +
                            "  \"role\": \"travelAgent\",\n" +
                            "  \"nic\": \" " + nicEditText.getText() + " \"\n" +
                            "}";


                    String requestBodyRegisterDetails = "{\n" +
                            "  \"nic\": \"" + nicEditText.getText() + "\",\n" +
                            "  \"firstName\": \"" + firstNameEditText.getText() + "\",\n" +
                            "  \"lastName\": \"" + lastNameEditText.getText() + "\",\n" +
                            "  \"email\": \"" + emailEditText.getText() + "\",\n" +
                            "  \"mobileNo\": \"" + mobileEditText.getText() + "\",\n" +
                            "  \"isActive\": true\n" +
                            "}";

                    URL urlRegister = new URL(apiUrlRegister);
                    URL urlRegisterDetails = new URL(apiUrlRegisterDetails);
                    HttpURLConnection connection = (HttpURLConnection) urlRegister.openConnection();
                    HttpURLConnection connectionDetails = (HttpURLConnection) urlRegisterDetails.openConnection();

                    // Set up the HTTP request
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json; utf-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setDoOutput(true);

                    connectionDetails.setRequestMethod("POST");
                    connectionDetails.setRequestProperty("Content-Type", "application/json; utf-8");
                    connectionDetails.setRequestProperty("Accept", "application/json");
                    connectionDetails.setDoOutput(true);

                    // Write the request body to the connection
                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = requestBodyRegister.getBytes("UTF-8");
                        os.write(input, 0, input.length);
                    }

                    try (OutputStream os = connectionDetails.getOutputStream()) {
                        byte[] input = requestBodyRegisterDetails.getBytes("UTF-8");
                        os.write(input, 0, input.length);
                    }catch (Exception e) {
                        System.out.println(e);
                    }

                    // Get the HTTP response code
                    int responseCode = connection.getResponseCode();
                    int responseCodeDetails = connectionDetails.getResponseCode();

                    // Check if the user was created successfully
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
                    Log.d("CreateUserActivity", "User created successfully");
                } else {
                    // Handle the error
                    Log.e("CreateUserActivity", "Failed to create user");
                }
            }
        }

        // Create an instance of the CreateUserTask and execute it
        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CreateUserTask().execute();
            }
        });

        // Go back
        Button gobackBtn = findViewById(R.id.buttonBack);
        gobackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the main activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


}