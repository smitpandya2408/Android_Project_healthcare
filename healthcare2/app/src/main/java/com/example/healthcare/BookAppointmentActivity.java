package com.example.healthcare;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText ed1, ed2, ed3, ed4, patientNameEditText, patientAddressEditText, patientContactEditText;
    private TextView tv;
    private Button dateButton, timeButton, btnBook;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        db = FirebaseFirestore.getInstance();

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextFullName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookAppointment);

        patientNameEditText = findViewById(R.id.editTextPatientName);
        patientAddressEditText = findViewById(R.id.editTextPatientAddress);
        patientContactEditText = findViewById(R.id.editTextPatientContact);

        Intent intent = getIntent();
        if (intent != null) {
            String doctorName = intent.getStringExtra("doctorName");
            String address = intent.getStringExtra("address");
            String contact = intent.getStringExtra("contact");
            String fees = intent.getStringExtra("fees");

            if (doctorName != null) {
                ed1.setText(doctorName);
            }
            if (address != null) {
                ed2.setText(address);
            }
            if (contact != null) {
                ed3.setText(contact);
            }
            if (fees != null) {
                ed4.setText(fees);
            }
        }

        // Disable EditText fields for doctor name, address, contact, and fees
        ed1.setEnabled(false); // doctor name
        ed2.setEnabled(false); // address
        ed3.setEnabled(false); // contact
        ed4.setEnabled(false); // fees

        initDatePicker();
        initTimePicker();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName = ed1.getText().toString();
                String address = ed2.getText().toString();
                String contact = ed3.getText().toString();
                String fees = ed4.getText().toString();
                String appointmentDate = dateButton.getText().toString();
                String appointmentTime = timeButton.getText().toString();

                String patientName = patientNameEditText.getText().toString();
                String patientAddress = patientAddressEditText.getText().toString();
                String patientContact = patientContactEditText.getText().toString();

                storeAppointment(doctorName, address, contact, fees,
                        appointmentDate, appointmentTime,
                        patientName, patientAddress, patientContact);
            }
        });
    }

    private void initDatePicker() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateButton.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    private void initTimePicker() {
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(BookAppointmentActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeButton.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });
    }

    private void storeAppointment(String doctorName, String address, String contact, String fees,
                                  String appointmentDate, String appointmentTime,
                                  String patientName, String patientAddress, String patientContact) {
        Map<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("doctorName", doctorName);
        appointmentData.put("address", address);
        appointmentData.put("contact", contact);
        appointmentData.put("fees", fees);
        appointmentData.put("appointmentDate", appointmentDate);
        appointmentData.put("appointmentTime", appointmentTime);
        appointmentData.put("patientName", patientName);
        appointmentData.put("patientAddress", patientAddress);
        appointmentData.put("patientContact", patientContact);

        db.collection("appointments")
                .add(appointmentData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(BookAppointmentActivity.this,
                            "Appointment booked successfully!",
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(BookAppointmentActivity.this,
                            "Failed to book appointment. Please try again.",
                            Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error adding appointment data: " + e.getMessage());
                });
    }
}
