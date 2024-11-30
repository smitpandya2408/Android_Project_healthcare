package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {

    Button buttonCartBack;

    private String[][] packages = {
            {"package 1 : Full Body checkup", "", "", "", "999"},
            {"package 2 : Blood Glucose Fasting", "", "", "", "300"},
            {"package 3 : Covid-19 Antibody-Ig9", "", "", "", "699"},
            {"package 4 : Thyroid Check", "", "", "", "500"},
            {"package 5 : Immunity Check", "", "", "", "500"}
    };

    private String[] packages_details = {
            "Blood packages Fasting\n" +
                    "complete Hemogram\n" +
                    "HbAlc\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LDH Lactate Dehydrogenase,Serum\n" +
                    "Lipid Profile\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "Covid-19 Antibody-Ig9",
            "Thyroid Profile-Total(T3,T4 & TSH Ultra-sensitive) ",
            "Complete Hemogram\n" +
                    "CRP(C Reactive Protein) Quantitative,Serum\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin D Total-25 Hydroxy\n" +
                    "Liver Function Test\n" +
                    "Lipid Profile"
    };

    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;
    private ListView listView;
    private Button buttonTGotoCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);

        buttonCartBack=findViewById(R.id.buttonCartBack);
        listView = findViewById(R.id.listviewCart);

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost: " + packages[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[position][0]); // Package name
                it.putExtra("text2", packages_details[position]); // Package details
                it.putExtra("text3", packages[position][4]); // Total cost
                startActivity(it);
            }
        });
        buttonCartBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });


    }
}
