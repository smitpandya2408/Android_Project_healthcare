package com.example.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    private LinearLayout layoutAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        layoutAppointments = findViewById(R.id.layoutAppointments);

        loadAppointments();
    }

    private void loadAppointments() {
        db.collection("appointments")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Map<String, Object> appointmentData = documentSnapshot.getData();
                            addAppointmentView(appointmentData, documentSnapshot.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error getting appointments", e);
                    }
                });
    }

    private void addAppointmentView(Map<String, Object> appointmentData, String appointmentId) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 20);

        LinearLayout appointmentLayout = new LinearLayout(this);
        appointmentLayout.setLayoutParams(layoutParams);
        appointmentLayout.setOrientation(LinearLayout.VERTICAL);

        // Display appointment details
        for (Map.Entry<String, Object> entry : appointmentData.entrySet()) {
            TextView textView = new TextView(this);
            textView.setText(entry.getKey() + ": " + entry.getValue());
            if (entry.getKey().equals("patientName") ||
                    entry.getKey().equals("patientContact") ||
                    entry.getKey().equals("patientAddress") ||
                    entry.getKey().equals("fees") ||
                    entry.getKey().equals("doctorName") ||
                    entry.getKey().equals("contact") ||
                    entry.getKey().equals("appointmentTime") ||
                    entry.getKey().equals("appointmentDate") ||
                    entry.getKey().equals("address")) {
                textView.setTextColor(ContextCompat.getColor(this, R.color.black));
            }
            appointmentLayout.addView(textView);
        }

        Button btnDelete = new Button(this);
        btnDelete.setText("Delete");
        btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppointment(appointmentId);
            }
        });
        btnDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.button));
        btnDelete.setTextColor(Color.WHITE);
        appointmentLayout.addView(btnDelete);

        layoutAppointments.addView(appointmentLayout);
    }


    private void deleteAppointment(String appointmentId) {
        db.collection("appointments").document(appointmentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Appointment deleted successfully", Toast.LENGTH_SHORT).show();
                        layoutAppointments.removeAllViews();
                        loadAppointments();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error deleting appointment", e);
                        Toast.makeText(MainActivity.this, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
