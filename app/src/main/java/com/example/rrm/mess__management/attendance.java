package com.example.rrm.mess__management;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class attendance extends AppCompatActivity {
    int SEND_PERMISSION_REQUEST_CODE=1;
    Button attend,dis;
    TextView id;
    int ID;
    DataBaseHelper db;

    public attendance() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = new DataBaseHelper(this);

        dis = findViewById(R.id.buttondisplay);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = findViewById(R.id.custId);
                String val = id.getText().toString();
                ID = Integer.parseInt(val);
                fetchcust(ID);
            }
        });

        attend = findViewById(R.id.buttonattendance);
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updatetoDBattendance(ID);
            }
        });
    }

    private void fetchcust(int custid){
        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();


        StringBuilder buffer=new StringBuilder();
        while(res.moveToNext()) {

            if (Integer.parseInt(res.getString(0)) == custid) {
                buffer.append("ID : ").append(res.getString(0)).append('\n');
                buffer.append("Name : ").append(res.getString(1)).append('\n');
                buffer.append("Address : ").append(res.getString(2)).append('\n');
                buffer.append("Phone : ").append(res.getString(3)).append('\n');
                buffer.append("Pending Amount : ").append(res.getString(8)).append('\n').append('\n');

            }
        }
        printMessage1(buffer.toString());


    }


    public void printMessage1(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.show();

    }

    private void updatetoDBattendance(int a){
        Cursor res =db.getDB();

        String custid = null,custname = null,custaddress= null,custphone = null,custlp= null,custdp= null,custpending= null,doj =null;

        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();

        while(res.moveToNext()) {
            if(a == Integer.parseInt(res.getString(0))) {

                custid = res.getString(0);
                custname = res.getString(1);
                custaddress = res.getString(2);
                custphone = res.getString(3);

                custlp = res.getString(4);
                custdp = res.getString(5);
                doj = res.getString(9);
                custpending = res.getString(8);
                break;
            }
        }

        int lp = Integer.parseInt(custlp);
        int dp = Integer.parseInt(custdp);
        int sd = Integer.parseInt(doj);
        Toast.makeText(this, "Into DB"+lp+dp, Toast.LENGTH_SHORT).show();

        int ls = Timepg.getStime(),le = Timepg.getEtime(),ds,de;
        ds = Timepg.getDstime();
        de = Timepg.getDetime();
        Calendar calendar = Calendar.getInstance();
        int crhr = calendar.get(Calendar.HOUR_OF_DAY);
        Toast.makeText(this, "Into DB"+ls+" "+le+" "+ds+" "+de+" "+crhr, Toast.LENGTH_SHORT).show();

        boolean ra = false;
        if(lp!=0 || dp!=0){

            if(crhr>=ls && crhr<=le ){
                lp = lp-1;
                ra = db.updateAttendance(a,lp,1);
            }else if(crhr>=ds && crhr<=de){
                dp =dp-1;
                ra = db.updateAttendance(a,dp,2);
            }else {

                Toast.makeText(this, "Wrong Time !", Toast.LENGTH_SHORT).show();
            }


        }else{

            Toast.makeText(this, "You Have Consumed All Plates", Toast.LENGTH_SHORT).show();
        }

        if(ra) {
            Toast.makeText(this, "Attendance Marked !", Toast.LENGTH_SHORT).show();
            onsend(custphone,"Mess__Management\nID :"+custid+"\nName : "+custname+"\nLunch Plates :"+lp+"\nDinner Plates : "+dp+"\nAmount Pending :"+custpending+"\nyour attendance marked.\n");

        }else if(!ra)
            Toast.makeText(this, "Error Occured !", Toast.LENGTH_SHORT).show();


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