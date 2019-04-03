package com.example.techwheelsservicecentre;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registered_Cars extends AppCompatActivity {
    DatabaseHelper techdb=new DatabaseHelper(this);
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered__cars);

       // button=(Button)findViewById(R.id.view_history);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor=techdb.showCarData();
                if(cursor.getCount() == 0)
                {
                    display(" ERROR "," No data Found ");
                    return;
                }

                StringBuffer stringBuffer=new StringBuffer();
                while(cursor.moveToNext())
                {
                    stringBuffer.append("\n");
                    stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                    stringBuffer.append("Vehicle Model: "+cursor.getString(1)+"\n");
                    stringBuffer.append("Registered Number: "+cursor.getString(2)+"\n");


                    display("Your Cars",stringBuffer.toString());
                }

            }
        });



    }
    public void display(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }
}
