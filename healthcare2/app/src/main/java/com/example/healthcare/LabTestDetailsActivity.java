package com.example.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LabTestDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails, edFullName, edContactNumber, edAddress;
    Button btnBack, buttonLDAddToCart, buttonAppDate, buttonAppTime;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize views
        tvPackageName = findViewById(R.id.textViewLDPackageName);
        tvTotalCost = findViewById(R.id.textViewLDTotalCost);
        edDetails = findViewById(R.id.editTextLDTextMultiLine);
        btnBack = findViewById(R.id.buttonLDBack);
        buttonLDAddToCart = findViewById(R.id.buttonLDAddToCart);
        edFullName = findViewById(R.id.editTextFullName);
        edContactNumber = findViewById(R.id.editTextPatientContact);
        edAddress = findViewById(R.id.editTextAppAddress);
        buttonAppDate = findViewById(R.id.buttonAppDate); // Initialize buttonAppDate
        buttonAppTime = findViewById(R.id.buttonAppTime); // Initialize buttonAppTime

        // Disable editing in the EditText
        edDetails.setEnabled(false);

        // Get intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String packageName = intent.getStringExtra("text1");
            String details = intent.getStringExtra("text2");
            String totalCost = intent.getStringExtra("text3");

            // Set values to views
            tvPackageName.setText(packageName);
            edDetails.setText(details);
            tvTotalCost.setText("Total Cost: " + totalCost + "/-");
        }

        // Handle back button click
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to LabTestActivity
                startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
                finish(); // Finish this activity
            }
        });

        // Handle Add to Cart button click
        buttonLDAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from EditText fields
                String fullName = edFullName.getText().toString().trim();
                String contactNumber = edContactNumber.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                String selectedDate = buttonAppDate.getText().toString().trim();
                String selectedTime = buttonAppTime.getText().toString().trim();

                // Check if any field is empty
                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(address)
                        || TextUtils.isEmpty(selectedDate) || TextUtils.isEmpty(selectedTime)) {
                    Toast.makeText(LabTestDetailsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Continue with adding data to Firestore
                Intent intent = getIntent();
                if (intent != null) {
                    String product = tvPackageName.getText().toString();
                    float price = Float.parseFloat(intent.getStringExtra("text3"));

                    // Create a new document with a generated ID
                    Map<String, Object> cartItem = new HashMap<>();
                    cartItem.put("product", product);
                    cartItem.put("price", price);
                    cartItem.put("type", "lab");
                    cartItem.put("fullName", fullName);
                    cartItem.put("contactNumber", contactNumber);
                    cartItem.put("address", address);
                    cartItem.put("selectedDate", selectedDate);
                    cartItem.put("selectedTime", selectedTime);

                    db.collection("cart")
                            .add(cartItem)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(getApplicationContext(), "Record Inserted To Cart ", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(), "Error adding record to cart", Toast.LENGTH_SHORT).show();
                                Log.e("LabTestDetailsActivity", "Error adding document", e);
                            });
                }
            }
        });

        // Set OnClickListener for buttonAppDate
        buttonAppDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Set OnClickListener for buttonAppTime
        buttonAppTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
    }

    private void showDatePicker() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this, // DateSetListener
                year,
                month,
                dayOfMonth
        );

        // Show date picker dialog
        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this, // TimeSetListener
                hourOfDay,
                minute,
                false // 24 hour format
        );

        // Show time picker dialog
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Handle date selection
        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        buttonAppDate.setText(selectedDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Handle time selection
        String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
        buttonAppTime.setText(selectedTime);
    }
}
