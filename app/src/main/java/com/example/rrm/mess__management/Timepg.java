package com.example.rrm.mess__management;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;



public class Timepg extends AppCompatActivity {


    static int stime,etime,dstime,detime;
    TextView st,et,dst,det;
    Button tset;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepg);

        calendar = Calendar.getInstance();

        st = findViewById(R.id.sText);
        et = findViewById(R.id.eText);
        dst = findViewById(R.id.dsText);
        det = findViewById(R.id.deText);

        tset=findViewById(R.id.tsbutton);
        tset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTime();
            }
        });
    }

    public void setTime(){
        stime = Integer.parseInt(st.getText().toString());
        etime = Integer.parseInt(et.getText().toString());
        dstime = Integer.parseInt(dst.getText().toString());
        detime = Integer.parseInt(det.getText().toString());

        Toast.makeText(this, stime+" "+etime+" "+dstime+" "+detime, Toast.LENGTH_SHORT).show();
    }

    public static int getEtime() {
        return etime;
    }

    public static int getStime() {
        return stime;
    }

    public static int getDstime() {
        return dstime;
    }

    public static int getDetime() {
        return detime;
    }
}