package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etAddress, etContact;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.editTextPatientName);
        etAddress = findViewById(R.id.editTextPatientAddress);
        etContact = findViewById(R.id.editTextPatientContact);
        btnSubmit = findViewById(R.id.buttonRegister);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String contact = etContact.getText().toString().trim();

                Log.d("RegistrationActivity", "Name: " + name);
                Log.d("RegistrationActivity", "Address: " + address);
                Log.d("RegistrationActivity", "Contact: " + contact);

                if (name.isEmpty() || address.isEmpty() || contact.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(RegistrationActivity.this, BookAppointmentActivity.class);
                    intent.putExtra("patientName", name);
                    intent.putExtra("patientAddress", address);
                    intent.putExtra("patientContact", contact);
                    startActivity(intent);
                    finish(); // Close the registration activity
                }
            }
        });
    }
}
