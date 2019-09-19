package com.example.rrm.mess__management;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Calendar;


public class insertpg extends AppCompatActivity {
    Pricepg p;
    Double total;
    int SEND_PERMISSION_REQUEST_CODE=1;
    int i;
    Button insert;
    TextView name,phone,address,amtpaid;
    CheckBox lunch,dinner;
    DataBaseHelper db;
    int lunchPlates=0;
    int dinnerPlates=0;

    static int yr_day;

    String name1,address1,value1,value2;
    long phone1;
    double amtpaid1;
    boolean l,d;

    Calendar calendar = Calendar.getInstance();
    String date = calendar.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertpg);

        db = new DataBaseHelper(this);
        name = findViewById(R.id.custid);
        phone = findViewById(R.id.custphone);
        address = findViewById(R.id.custaddress);
        amtpaid = findViewById(R.id.custId);
        lunch = findViewById(R.id.checklunch);
        dinner = findViewById(R.id .checkdinner);


        insert = findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                String date = DateFormat.getDateInstance().format(calendar.getTime());

                name1=name.getText().toString();
                address1=address.getText().toString();
                value1 = phone.getText().toString();
                phone1 = Long.parseLong(value1);
                value2 = amtpaid.getText().toString();
                amtpaid1 = Double.parseDouble(value2);
                l=lunch.isChecked();
                d=dinner.isChecked();
                yr_day = calendar.get(Calendar.DAY_OF_YEAR);
                insertToDB(name1,phone1,address1,amtpaid1,l,d,date, yr_day);
            }
        });


    }


    private void insertToDB(String name, long phone, String address, double amtpaid, boolean lunch, boolean dinner, String date, int yr_day) {

        if (lunch) {
            lunchPlates = 30;
            total = p.onePrice;
        }
        if (dinner) {
            dinnerPlates = 30;
            total = p.onePrice;
        }

        if(lunch && dinner)
        {
            total=p.twoPrice;
        }

        boolean checkflag = true;
        Cursor rs = db.getDB();



        while (rs.moveToNext()) {
            String id=rs.getString(0);
            i=Integer.parseInt(id);
            if (name.equals(rs.getString(1))) {
                Toast.makeText(this, "User Name Already exist !\nFor ID : " + rs.getString(0), Toast.LENGTH_SHORT).show();
                checkflag = false;
                break;
            }
            else{
                checkflag = true;
                i++;

            }
        }

        if (checkflag) {

            boolean flag = db.intoDB(name, phone, address, amtpaid, lunchPlates, dinnerPlates, date, yr_day);

            if (flag) {

                Toast.makeText(this, "Data Inserted :" + lunch + lunchPlates + dinner + dinnerPlates + "\n" + yr_day, Toast.LENGTH_SHORT).show();
                if(i==0){
                    i =1;
                }
                onsend(value1,"Mess__Management\n\nID :"+i+"\nName : "+name1+"\nLunch Plates :"+lunchPlates+"\nDinner Plates : "+dinnerPlates+"\nAmount Pending :"+(total-amtpaid1)+"\n\nYou are Added.\n\nWELCOME !!!");

                Intent intent = new Intent(this, SendMessage.class);
                startActivity(intent);
            } else
                Toast.makeText(this, "Error Inserting :" + lunch + lunchPlates + dinner + dinnerPlates + "\n" + yr_day, Toast.LENGTH_SHORT).show();
        }

    }
    public void onsend(String ph,String msg){

        if(!checkPermission(Manifest.permission.SEND_SMS))
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_PERMISSION_REQUEST_CODE);

        if(checkPermission(Manifest.permission.SEND_SMS)){

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(ph,null,msg,null,null);
            Toast.makeText(this, "Sent to " + ph, Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this, "Error Sending to "+ph, Toast.LENGTH_SHORT).show();
        }

    }

    public boolean checkPermission(String permission){

        int check = ContextCompat.checkSelfPermission(this,permission);
        return(check == PackageManager.PERMISSION_GRANTED);

    }
}