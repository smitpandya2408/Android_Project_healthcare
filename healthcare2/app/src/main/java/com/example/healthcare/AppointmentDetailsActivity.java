package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        Intent intent = getIntent();
        if (intent != null) {
            String doctorName = intent.getStringExtra("doctorName");
            String address = intent.getStringExtra("address");
            String contact = intent.getStringExtra("contact");
            String fees = intent.getStringExtra("fees");
            String appointmentDate = intent.getStringExtra("appointmentDate");
            String appointmentTime = intent.getStringExtra("appointmentTime");

            displayAppointmentDetails(doctorName, address, contact, fees, appointmentDate, appointmentTime);
        }
    }

    private void displayAppointmentDetails(String doctorName, String address, String contact, String fees, String appointmentDate, String appointmentTime) {
        TextView textViewDoctorNameValue = findViewById(R.id.textViewDoctorNameValue);
        TextView textViewAddressValue = findViewById(R.id.textViewAddressValue);
        TextView textViewContactValue = findViewById(R.id.textViewContactValue);
        TextView textViewFeesValue = findViewById(R.id.textViewFeesValue);
        TextView textViewAppointmentDateValue = findViewById(R.id.textViewAppointmentDateValue);
        TextView textViewAppointmentTimeValue = findViewById(R.id.textViewAppointmentTimeValue);

        textViewDoctorNameValue.setText(doctorName);
        textViewAddressValue.setText(address);
        textViewContactValue.setText(contact);
        textViewFeesValue.setText(fees);
        textViewAppointmentDateValue.setText(appointmentDate);
        textViewAppointmentTimeValue.setText(appointmentTime);
    }
}
