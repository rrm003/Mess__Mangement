package com.example.rrm.mess__management;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.Toast;


public class SendMessage extends AppCompatActivity {

    final int SEND_PERMISSION_REQUEST_CODE=1;
    DataBaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseHelper(this);
        onSend();
    }

    public void onSend(){

        String custid,custname,custaddress,custphone,custlp,custdp,custpaid,custdoj;
        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();

        res.moveToLast();
        custid=res.getString(0);
        custname=res.getString(1);
        custaddress=res.getString(2);
        custphone=res.getString(3);
        custlp=res.getString(4);
        custdp=res.getString(5);
        custpaid=res.getString(6);
        custdoj=res.getString(7);

        if(!checkPermission(Manifest.permission.SEND_SMS))
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_PERMISSION_REQUEST_CODE);

        if(checkPermission(Manifest.permission.SEND_SMS)){

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(custphone,null,"Welcome to Mess Manager :-"+custname+"\nYour id is :-"+custid+"\nCustomer Address :"+custaddress+"\nLunchPlates Allocated :-"+custlp+"\nLunchPlates Allocated :-"+custdp+"\nAmount Paid :-"+custpaid+"\nDate of Joining :-"+custdoj,null,null);
            Toast.makeText(this, "Sent to " + custphone, Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(this, "Error Sending to "+custphone, Toast.LENGTH_SHORT).show();
        }


    }

    public boolean checkPermission(String permission){

        int check = ContextCompat.checkSelfPermission(this,permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }

}

