package com.example.tech_wheels_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Create_slot extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FirebaseFirestore db;
    private TextView dateclick,datedisplay;
    private Button createslot;
    private String date1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_slot);

        dateclick=(TextView)findViewById(R.id.dateselect);
        datedisplay=(TextView)findViewById(R.id.date);
        createslot=(Button)findViewById(R.id.checkslot);

        dateclick.setMovementMethod(LinkMovementMethod.getInstance());
        dateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new com.example.tech_wheels_admin.DatePicker();
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        createslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=FirebaseFirestore.getInstance();
                Map<String,String> User=new HashMap<>();
                User.put("9AM-11AM-1", "");
                User.put("9AM-11AM-2", "");
                User.put("9AM-11AM-3", "");
                User.put("9AM-11AM-4", "");
                User.put("11AM-1PM-1", "");
                User.put("11AM-1PM-2", "");
                User.put("11AM-1PM-3", "");
                User.put("11AM-1PM-4", "");
                User.put("2PM-4PM-1", "");
                User.put("2PM-4PM-2", "");
                User.put("2PM-4PM-3", "");
                User.put("2PM-4PM-4", "");
                User.put("4PM-6PM-1", "");
                User.put("4PM-6PM-2", "");
                User.put("4PM-6PM-3", "");
                User.put("4PM-6PM-4", "");

                db.collection("Slots").document(date1)
                        .set(User)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Log.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(getApplicationContext(),"Slots created for "+date1,Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(Create_slot.this,selection.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error writing document", e);
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            }
                        });


            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        date1= DateFormat.getDateInstance().format(c.getTime());
        datedisplay.setText(date1);
    }
}
