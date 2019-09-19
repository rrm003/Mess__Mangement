package com.example.rrm.mess__management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class deletepg extends AppCompatActivity {

    TextView getid;
    Button deleteButton;
    String id;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletepg);
        db = new DataBaseHelper(this);
        getid = findViewById(R.id.idText);
        deleteButton = findViewById(R.id.dltbutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCust();
            }
        });
    }

    public void deleteCust(){
        id = getid.getText().toString();
        if(db.deleteDb(id))
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "ERROR DELETING", Toast.LENGTH_SHORT).show();

    }
}