// DoctorDetailsActivity.java

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

public class DoctorDetailsActivity extends AppCompatActivity {

    private String[][] doctor_details1 = {
            {"Doctor Name : Ajit Saste", "Hospital Address : Nana Mahuva", "Exp : 5yrs", "Mobile No : 9898982036", "500"},
            {"Doctor Name : Prasad Pawar ", "Hospital Address : Nana Mahuva", "Exp : 15yrs", "Mobile No : 7878789602", "600"},
            {"Doctor Name : Hasmukh  Ankhja", "Hospital Address : Nana Mahuva", "Exp : 8yrs", "Mobile No : 9373895620", "1000"},
            {"Doctor Name : Deepak Agravat ", "Hospital Address : Nana Mahuva", "Exp : 7yrs", "Mobile No : 7979798506", "750"},
            {"Doctor Name : Prakash Vavadiya", "Hospital Address : Nana Mahuva", "Exp : 6yrs", "Mobile No : 7985296347", "500"}
    };

    private String[][] doctor_details2 = {
            {"Doctor Name :  Neelam Patil", "Hospital Address : Nana Mahuva", "Exp : 5yrs", "Mobile No : 9898982036", "1500"},
            {"Doctor Name : swati Upadhyay ", "Hospital Address : Nana Mahuva", "Exp : 15yrs", "Mobile No : 7878789602", "2000"},
            {"Doctor Name : Neeraj Pandey", "Hospital Address : Nana Mahuva", "Exp : 8yrs", "Mobile No : 9373895620", "1000"},
            {"Doctor Name : Mayuri Pandya", "Hospital Address : Nana Mahuva", "Exp : 7yrs", "Mobile No : 7979798506", "2000"},
            {"Doctor Name : Nisha Vyash", "Hospital Address : Nana Mahuva", "Exp : 6yrs", "Mobile No : 7985296347", "2500"}
    };

    private String[][] doctor_details3 = {
            {"Doctor Name : Seema Saste", "Hospital Address : Nana Mahuva", "Exp : 5yrs", "Mobile No : 9898982036", "2500"},
            {"Doctor Name : Pankaj Pawar ", "Hospital Address : Nana Mahuva", "Exp : 6yrs", "Mobile No : 7878789602", "1500"},
            {"Doctor Name : Neelam  Vyash", "Hospital Address : Nana Mahuva", "Exp : 10yrs", "Mobile No : 9373895620", "1000"},
            {"Doctor Name : Hitix Agravat ", "Hospital Address : Nana Mahuva", "Exp : 78yrs", "Mobile No : 7979798506", "1500"},
            {"Doctor Name : Jatin Bharad", "Hospital Address : Nana Mahuva", "Exp : 13yrs", "Mobile No : 7985296347", "2000"}
    };

    private String[][] doctor_details4 = {
            {"Doctor Name : Nilesh Barot", "Hospital Address : Nana Mahuva", "Exp : 5yrs", "Mobile No : 9898982036", "3000"},
            {"Doctor Name : Hevin Patoliya", "Hospital Address : Nana Mahuva", "Exp : 15yrs", "Mobile No : 7878789602", "1000"},
            {"Doctor Name : Bhavin Pandya", "Hospital Address : Nana Mahuva", "Exp : 8yrs", "Mobile No : 9373895620", "3000"},
            {"Doctor Name : Devesh Chavda ", "Hospital Address : Nana Mahuva", "Exp : 7yrs", "Mobile No : 7979798506", "3000"},
            {"Doctor Name : Ruchal Patel", "Hospital Address : Nana Mahuva", "Exp : 6yrs", "Mobile No : 7985296347", "2000"}
    };

    private String[][] doctor_details5 = {
            {"Doctor Name : Alpa Makwana", "Hospital Address : Nana Mahuva", "Exp : 5yrs", "Mobile No : 9898982036", "2500"},
            {"Doctor Name : Ruchi Kadivar", "Hospital Address : Nana Mahuva", "Exp : 15yrs", "Mobile No : 7878789602", "3000"},
            {"Doctor Name : Manish Jain", "Hospital Address : Nana Mahuva", "Exp : 8yrs", "Mobile No : 9373895620", "1000"},
            {"Doctor Name : Vishal Patel ", "Hospital Address : Nana Mahuva", "Exp : 7yrs", "Mobile No : 7979798506", "2500"},
            {"Doctor Name : Shrikant Pandey", "Hospital Address : Nana Mahuva", "Exp : 6yrs", "Mobile No : 7985296347", "2500"}
    };

    String[][] doctor_details = {};
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;

    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonDDBack);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if (title.compareTo("Family Physician") == 0)
            doctor_details = doctor_details1;
        else if (title.compareTo("Dietitian") == 0)
            doctor_details = doctor_details2;
        else if (title.compareTo("Dentist") == 0)
            doctor_details = doctor_details3;
        else if (title.compareTo("Surgeon") == 0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < doctor_details.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fees:" + doctor_details[i][4] + "/-");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        ListView lst = findViewById(R.id.listViewDD);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                i.putExtra("doctorName", doctor_details[position][0]);
                i.putExtra("address", doctor_details[position][1]);
                i.putExtra("experience", doctor_details[position][2]);
                i.putExtra("contact", doctor_details[position][3]);
                i.putExtra("fees", doctor_details[position][4]+" /-");
                startActivity(i);
            }
        });
    }
}
