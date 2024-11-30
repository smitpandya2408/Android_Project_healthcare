package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartLabActivity extends AppCompatActivity {

    private static final String TAG = "CartLabActivity";

    private FirebaseFirestore db;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        listView = findViewById(R.id.listviewCart);
        db = FirebaseFirestore.getInstance();

        // Retrieve username from intent extra
        String username = getIntent().getStringExtra("username");

        // Retrieve data from Firestore based on the user's information
        db.collection("users").document(username).collection("Appointments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> packageList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String packageName = document.getString("packageName");
                                if (packageName != null) {
                                    packageList.add(packageName);
                                }
                            }
                            // Update the UI with the retrieved data
                            adapter = new ArrayAdapter<>(CartLabActivity.this, android.R.layout.simple_list_item_1, packageList);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click if needed
            }
        });

        // Button to navigate back to the previous activity
        Button btnBack = findViewById(R.id.buttonCartBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartLabActivity.this, LabTestActivity.class);
                startActivity(i);
            }
        });
    }
}
