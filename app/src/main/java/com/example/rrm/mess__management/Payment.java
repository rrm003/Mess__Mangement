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

public class Payment extends AppCompatActivity {
    int id;
    double amt;
    String ids;
    int SEND_PERMISSION_REQUEST_CODE=1;
    TextView getid,getamt;
    Button dept,dispinfo;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseHelper(this);
        setContentView(R.layout.activity_payment);
        getid = findViewById(R.id.idText);
        getamt = findViewById(R.id.amtText);

        dispinfo = findViewById(R.id.btdisplay);
        dispinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int custid = Integer.parseInt(getid.getText().toString());
                fetchcust2(custid);
            }
        });

        dept = findViewById(R.id.deptbutton);
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deptAmt();
            }
        });
    }

    private void fetchcust2(int cid){
        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();


        StringBuilder buffer=new StringBuilder();
        while(res.moveToNext()) {

            if (Integer.parseInt(res.getString(0)) == cid) {
                buffer.append("ID : ").append(res.getString(0)).append('\n');
                buffer.append("Name : ").append(res.getString(1)).append('\n');
                buffer.append("Address : ").append(res.getString(2)).append('\n');
                buffer.append("Phone : ").append(res.getString(3)).append('\n');
                buffer.append("Pending Amount : ").append(res.getString(8)).append('\n').append('\n');

            }
        }
        printMessage2(buffer.toString());


    }

    public void printMessage2(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.show();

    }

    public void deptAmt(){
        ids = getid.getText().toString();
        id = Integer.parseInt(ids);
        amt = Double.parseDouble(getamt.getText().toString());
        double amtpaid=0,amtpending =0;
        String pho1=null;
        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();

        while(res.moveToNext()) {
            if(id == Integer.parseInt(res.getString(0))) {
                pho1 = res.getString(3);
                amtpaid=Double.parseDouble(res.getString(6));
                amtpending= Double.parseDouble(res.getString(8));
                break;
            }
        }
        amtpaid = amtpaid+amt;

        boolean checkamt=false;
        if(amtpending >=amt){
          amtpending = amtpending - amt;
          checkamt = true;
        }else if(amtpending<amt){
            checkamt = false;
            Toast.makeText(this,"Amount entered exceeds pending amount !\nAmount Pending :"+amtpending,Toast.LENGTH_SHORT).show();
        }
        boolean flag = db.custDeposit(ids,amtpaid,amtpending);
        if(flag && checkamt) {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            onsend(pho1,"Your Payement recived!\nAmount Paid:"+amtpaid+"\nAmount Pending :"+amtpending);

        } else
            Toast.makeText(this,"Error Updating",Toast.LENGTH_SHORT).show();

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