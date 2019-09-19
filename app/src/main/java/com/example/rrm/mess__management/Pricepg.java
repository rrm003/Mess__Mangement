package com.example.rrm.mess__management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pricepg extends AppCompatActivity {

    static double onePrice;
    static double twoPrice;

    TextView oneTime,twoTime;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricepg);

        oneTime = findViewById(R.id.oneText);
        twoTime = findViewById(R.id.twoText);
        bt = findViewById(R.id.setbutton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setamt();
            }
        });
    }

    public void setamt(){

        Pricepg.onePrice = Double.parseDouble(oneTime.getText().toString());
        Pricepg.twoPrice = Double.parseDouble(twoTime.getText().toString());
        Toast.makeText(this, "Price Marked !", Toast.LENGTH_SHORT).show();
    }

    public static double getOnePrice() {
        return onePrice;
    }

    public static double getTwoPrice() {
        return twoPrice;
    }

}