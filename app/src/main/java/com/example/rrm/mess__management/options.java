package com.example.rrm.mess__management;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class options extends Activity {

    DataBaseHelper db;
    Button insert,display,updateb,deleteb,attendb,pendingb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        db = new DataBaseHelper(this);

        //insert_button
        insert = findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                goToInsertpg();
            }

        });


        //disply_button
        display = findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                goToDisplaypg();
            }

        });

        //update_button
        updateb = findViewById(R.id.update);
        updateb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                goToUpdatepg();
            }

        });

        //delete button
        deleteb = findViewById(R.id.deletek);
        deleteb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoDelete();
            }
        });

        //attendb button
        attendb = findViewById(R.id.attendbutton);
        attendb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAttend();
            }
        });

        attendb = findViewById(R.id.bpending);
        attendb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPending();
            }
        });

    }


    private void  goToInsertpg(){

        Intent intent = new Intent(this,insertpg.class);
        startActivity(intent);
    }


    private void  goToDisplaypg(){

        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();


        StringBuilder buffer=new StringBuilder();
        while(res.moveToNext()){

            buffer.append("ID : ").append(res.getString(0)).append('\n');
            buffer.append("Name : ").append(res.getString(1)).append('\n');
            buffer.append("Address : ").append(res.getString(2)).append('\n');
            buffer.append("Phone : ").append(res.getString(3)).append('\n');
            buffer.append("Amount Paid : ").append(res.getString(6)).append('\n');
            buffer.append("LunchPlates : ").append(res.getString(4)).append('\n');
            buffer.append("DinnerPlates : ").append(res.getString(5)).append('\n');
            buffer.append("Date of Joining : ").append(res.getString(7)).append('\n').append('\n');
        }
        printMessage(buffer.toString());

    }

    private void  goToUpdatepg(){

        Intent intent = new Intent(this,update.class);
        startActivity(intent);
    }

    private void gotoDelete(){

        Intent intent = new Intent(this,deletepg.class);
        startActivity(intent);
    }

    private  void gotoAttend(){

        Intent intent = new Intent(this,attendance.class);
        startActivity(intent);
    }

    private  void gotoPending(){


        Cursor res =db.getDB();


        if(res.getCount() == 0)
            Toast.makeText(this,"No Data Found",Toast.LENGTH_SHORT).show();


        StringBuilder buffer=new StringBuilder();
        while(res.moveToNext()) {

            if (Double.parseDouble(res.getString(8)) != 0) {
                buffer.append("ID : ").append(res.getString(0)).append('\n');
                buffer.append("Name : ").append(res.getString(1)).append('\n');
                buffer.append("Address : ").append(res.getString(2)).append('\n');
                buffer.append("Phone : ").append(res.getString(3)).append('\n');
                buffer.append("Pending Amount : ").append(res.getString(8)).append('\n').append('\n');

            }
        }
        printMessage(buffer.toString());
    }

    public void printMessage(String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.show();

    }

}