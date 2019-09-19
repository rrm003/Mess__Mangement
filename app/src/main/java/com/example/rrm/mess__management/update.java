package com.example.rrm.mess__management;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class update extends AppCompatActivity {

    Button updatecust,up,updateTime,addpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updatecust = findViewById(R.id.customerbutton);
        updatecust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer();
            }
        });

        up = findViewById(R.id.pricebutton);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toprice();
            }
        });


        updateTime = findViewById(R.id.timebutton);
        updateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toltime();
            }
        });

        addpay = findViewById(R.id.paybutton);
        addpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPayment();
            }
        });
    }

    public void updateCustomer(){
        Intent intent = new Intent(this,updatepg.class);
        startActivity(intent);
    }


    public void toprice(){
        Intent intent = new Intent(this,Pricepg.class);
        startActivity(intent);
    }

    public void toltime(){
        Intent intent = new Intent(this,Timepg.class);
        startActivity(intent);
    }

    public void addPayment(){
        Intent intent = new Intent(this,Payment.class);
        startActivity(intent);

    }


}