package com.example.rrm.mess__management;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class login extends Activity {

    EditText username,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =  findViewById(R.id.editText_username);
        password =  findViewById(R.id.editText_password);
        login = findViewById(R.id.button_login);


        login.setOnClickListener(new OnClickListener(){

            public void onClick(View view){
                login1();

            }

        });

    }




    public void login1(){





        if(password.getText().toString().equals("") && username.getText().toString().equals("")){

            Toast.makeText(this,"Enter all Fields ",Toast.LENGTH_SHORT).show();
        }


        else if(password.getText().toString().equals("pass123") && username.getText().toString().equals("admin")){

            Toast.makeText(this,"login successful ",Toast.LENGTH_SHORT).show();
            Intent intent;
            intent = new Intent(this,options.class);
            startActivity(intent);
        }

        else {
            Toast.makeText(this,"Enter all Fields correctly ",Toast.LENGTH_SHORT).show();

        }

    }


}