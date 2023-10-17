package com.eadsliit.travel_manager_mobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TrainDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);

        // Retrieve the train code from the intent's extras
        String trainCode = getIntent().getStringExtra("trainCode");

        // Use the train code to fetch train details from your data source
        // Update UI to display the details
        // Example: Fetch data and display it in TextViews
    }
}
