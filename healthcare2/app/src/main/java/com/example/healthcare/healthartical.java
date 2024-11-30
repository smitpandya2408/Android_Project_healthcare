package com.example.healthcare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class healthartical extends AppCompatActivity {

    TextView title,heading;
    ListView listView;

    Button back;
    private String [][] heatlh_details={
            {"Walking Daily","","","","Click More Details"},
            {"Home Care Of COVID-19","","","","Click More Details"},
            {"Stop Smocking","","","","Click More Details"},
            {"Menstrual Cramps","","","","Click More Details"},
            {"Healthy Gut","","","","Click More Details"}
    };

    private  int[] images={
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };

    HashMap<String,String > item;
    ArrayList list;
    SimpleAdapter sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthartical);
        listView=findViewById(R.id.listView);
        heading=findViewById(R.id.heading);
        title=findViewById(R.id.title);
        back=findViewById(R.id.button);

        list = new ArrayList();
        for (int j = 0; j < heatlh_details.length; j++) {  // Corrected loop condition
            item = new HashMap<String, String>();
            item.put("Line1", heatlh_details[j][0]);
            item.put("Line2", heatlh_details[j][1]);
            item.put("Line3", heatlh_details[j][2]);
            item.put("Line4", heatlh_details[j][3]);
            item.put("Line5", heatlh_details[j][4]);
            list.add(item);
        }
        sa=new SimpleAdapter(this,list,R.layout.multi_lines,
                new String[]{"Line1","Line2","Line3","Line4","Line5",},
                new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
        );
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getApplicationContext(), healtharticlesdetails.class);
                i.putExtra("text1",heatlh_details[position][0]);
                i.putExtra("text2",images[position]);
                startActivity(i);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
    }
}
