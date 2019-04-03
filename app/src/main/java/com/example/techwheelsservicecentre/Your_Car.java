package com.example.techwheelsservicecentre;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Your_Car extends AppCompatActivity{
    private Button b1,b2;
    private TextView regno;
    private String regnopattern,vehiclemodel;
    DatabaseHelper techdb=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your__car);

        b1=(Button)findViewById(R.id.submit);
        regno=(TextView)findViewById(R.id.regno);

        final AutoCompleteTextView autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.modelvehicle);
        String [] cars=getResources().getStringArray(R.array.cars);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cars);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vehiclemodel = parent.getItemAtPosition(position).toString();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String regnoo=regno.getText().toString().trim();
                regnopattern="^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$";
                if(TextUtils.isEmpty(vehiclemodel))
                {
                    Toast.makeText(getApplicationContext()," Enter Vehicle Model ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(regnoo)|| !regnoo.matches(regnopattern)) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Registration no.", Toast.LENGTH_LONG).show();
                    return;
                }

                    boolean insertdata=techdb.getCardata(vehiclemodel,regnoo);
                    if(insertdata==true)
                    {
                        Toast.makeText(getApplicationContext()," Check Registered Vehicles ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()," Data Couldn't be added :(- ",Toast.LENGTH_SHORT).show();
                    }




            }
        });



        b2=(Button)findViewById(R.id.check);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Your_Car.this,Registered_Cars.class);
                startActivity(intent);
            }
        });
    }




}
