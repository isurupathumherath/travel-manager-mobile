package com.eadsliit.travel_manager_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private Button btnLogout;
    private Button btnUpdateProfile;

    private ImageView userIcon;
    private FirebaseAuth firebaseAuth;
    private TextView welcomeTextView;
    private FirebaseFirestore firestore;
    private ListenerRegistration userListener;
    private RecyclerView recyclerView;
    private TrainScheduleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchData();

        btnLogout = findViewById(R.id.logoutButton);
        userIcon = findViewById(R.id.userIcon);

        welcomeTextView = findViewById(R.id.textViewWelcome);

        // Get the user's ID from the Intent
        String emailAddress = getIntent().getStringExtra("emailAddress");

        // Retrieve the current user's information
        System.out.println("USER ID: " + emailAddress);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<TrainScheduleItem> trainScheduleItemList = new ArrayList<>();
        adapter = new TrainScheduleAdapter(trainScheduleItemList);
        recyclerView.setAdapter(adapter);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a Logout message
                Toast.makeText(MainActivity.this, "You are logging out!", Toast.LENGTH_SHORT).show();

                // Redirect to the main activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                finish();
            }
        });

    }

    private void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://eafd-116-206-246-73.ngrok-free.app/api/TrainSchedule";

        // Create a request to fetch the data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null, response -> {
            try {
                List<TrainScheduleItem> trainScheduleItemList = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    TrainScheduleItem item = new TrainScheduleItem();

                    item.setTrainId(jsonObject.getString("trainId"));
                    item.setName(jsonObject.getString("name"));
                    item.setStartStation(jsonObject.getString("startStation"));
                    item.setEndStations(jsonObject.getString("endStations"));
                    item.setDepartureTime(jsonObject.getString("departureTime"));
                    item.setArrivalTime(jsonObject.getString("arrivalTime"));
                    item.setIsActive(jsonObject.getString("isActive"));

                    trainScheduleItemList.add(item);
                }
                adapter = new TrainScheduleAdapter(trainScheduleItemList);
                recyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            // Handle errors
            Log.e("FetchData", "Error fetching data: " + error.getMessage());
        });

        requestQueue.add(jsonArrayRequest);
    }

}