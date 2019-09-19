package com.example.rrm.mess__management;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class updatepg extends AppCompatActivity {
    String phno,i;
    String name1,address1,value;
    long phone1;
    int id1;
    final int SEND_PERMISSION_REQUEST_CODE=1;
    Button update;
    TextView name,phone,address,id;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepg);

        db = new DataBaseHelper(this);

        update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.custid);
                phone = findViewById(R.id.custphone);
                address = findViewById(R.id.custaddress);
                id = findViewById(R.id.custId);

                /*String name1,address1,value;
                long phone1;
                int id1;*/

                name1=name.getText().toString();
                address1=address.getText().toString();
                value = phone.getText().toString();
                phno=value;
                phone1 = Long.parseLong(value);
                value = id.getText().toString();
                i=value;
                id1 = Integer.parseInt(value);
                updateToDB(name1,phone1,address1,id1);
            }
        });


    }


    private void updateToDB(String name,long phone,String address,int id) {

        boolean flag = db.updateDB(name,phone,address,id);
        if(flag) {
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            //onSend(id);
            onsend(phno,"Mess__Management\n\nUpdated Info!!!\n\nID :"+id1+"\nName : "+name1+"\nPhone no:"+phone1+"\nAddress: "+address1+"\n\nUpdation Sucessfull !!!.\n");

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
    /*public void onSend(int id){

        String custid = null,custname = null,custaddress= null,custphone = null,custlp= null,custdp= null,custpaid= null,custdoj= null;
        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();

        while(res.moveToNext()) {
            if(id == Integer.parseInt(res.getString(0))) {

                custid = res.getString(0);
                custname = res.getString(1);
                custaddress = res.getString(2);
                custphone = res.getString(3);
                custlp = res.getString(6);
                custdp = res.getString(4);
                custpaid = res.getString(5);
                custdoj = res.getString(7);

                break;
            }
        }


        checkPermission(Manifest.permission.SEND_SMS);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_PERMISSION_REQUEST_CODE);

        if(custphone != null) {
            if (checkPermission(Manifest.permission.SEND_SMS)) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(custphone, null,   "Mr/Mrs. "+custname + ",your information has been Updated.\nYour id is :-" + custid + "\n" + custaddress + "\nLunchPlates Allocated :-" + custlp + "\nLunchPlates Allocated :-" + custdp + "\nAmount Paid :-" + custpaid + "\nDate of Joining :-" + custdoj, null, null);
                Toast.makeText(this, "Sent to " + custphone, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Error Sending to " + custphone, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Phone not initiallized !", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean checkPermission(String permission){

        int check = ContextCompat.checkSelfPermission(this,permission);
        return(check == PackageManager.PERMISSION_GRANTED);
    }*/

}