package com.eadsliit.travel_manager_mobile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditProfileActivity extends AppCompatActivity {
    private EditText emailEditText, firstNameEditText, lastNameEditText, nicEditText, mobileEditText;
    private Button updateProfileButton, deactivateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        emailEditText = findViewById(R.id.editTextEmail);
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        nicEditText = findViewById(R.id.editTextNIC);
        mobileEditText = findViewById(R.id.editTextMobile);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        deactivateButton = findViewById(R.id.deactivate);

        // Fetch user details based on NIC
        fetchUserDetailsByNIC();

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the updated user details from EditText fields
                String newEmail = emailEditText.getText().toString();
                String newFirstName = firstNameEditText.getText().toString();
                String newLastName = lastNameEditText.getText().toString();
                String newNIC = nicEditText.getText().toString();
                String newMobile = mobileEditText.getText().toString();

                // Update the user's profile
                updateProfile(newEmail, newFirstName, newLastName, newNIC, newMobile);

                // Display a success message or handle errors
                Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Set an OnClickListener for the "Deactivate Profile" button
        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement profile deactivation logic
                deactivateProfile();

                // Display a success message or handle errors
                Toast.makeText(EditProfileActivity.this, "Profile deactivated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserDetailsByNIC() {
        new FetchUserDetailsTask().execute("https://eafd-116-206-246-73.ngrok-free.app/api/Traveler/200024700159");
    }

    private void updateProfile(String email, String firstName, String lastName, String nic, String mobile) {
        new UpdateProfileTask().execute(email, firstName, lastName, nic, mobile);
    }

    private class UpdateProfileTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String apiUrl = "https://eafd-116-206-246-73.ngrok-free.app/api/Traveler/200024700158";
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set up the HTTP request
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create a JSON object with the updated user data
                JSONObject userData = new JSONObject();
                userData.put("email", params[0]);
                userData.put("firstName", params[1]);
                userData.put("lastName", params[2]);
                userData.put("nic", params[3]);
                userData.put("mobileNo", params[4]);

                // Write the JSON data to the connection
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = userData.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get the HTTP response code
                int responseCode = connection.getResponseCode();

                // Check if the update was successful
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Profile updated successfully
                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Handle the error
                Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void deactivateProfile() {

    }

    private class FetchUserDetailsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String urlString = params[0];
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Data is fetched successfully
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    // Handle the error
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String userData) {
            // Parse the JSON response and populate the EditText fields with user details
            try {
                JSONObject jsonObject = new JSONObject(userData);
                nicEditText.setText(jsonObject.getString("nic"));
                firstNameEditText.setText(jsonObject.getString("firstName"));
                lastNameEditText.setText(jsonObject.getString("lastName"));
                emailEditText.setText(jsonObject.getString("email"));
                mobileEditText.setText(jsonObject.getString("mobileNo"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
