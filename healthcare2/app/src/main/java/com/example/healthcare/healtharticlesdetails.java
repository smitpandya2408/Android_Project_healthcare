package com.example.healthcare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class healtharticlesdetails extends AppCompatActivity {
    TextView title,heading;
    ImageView image;

    Button btnback;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healtharticlesdetails);
        title=findViewById(R.id.title);
        heading=findViewById(R.id.heading);
        image=findViewById(R.id.image);
        btnback=findViewById(R.id.btnback);

        Intent i=getIntent();
        title.setText(i.getStringExtra("text1"));

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            int resId=bundle.getInt("text2");
            image.setImageResource(resId);
        }
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), healthartical.class);
                startActivity(i);
            }
        });
    }
}

