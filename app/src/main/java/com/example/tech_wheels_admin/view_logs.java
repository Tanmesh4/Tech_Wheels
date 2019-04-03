package com.example.tech_wheels_admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class view_logs extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateclick,datedisplay;
    private String date1;
    private Button view;
    private FirebaseFirestore db;
    private  String TAG=" View Logs ";
    private static ArrayList<myhistory> myhistories;
    private static RecyclerView recyclerView;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);
        dateclick=(TextView)findViewById(R.id.dateselect);
        datedisplay=(TextView)findViewById(R.id.date);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myhistories = new ArrayList<>();

        dateclick.setMovementMethod(LinkMovementMethod.getInstance());
        dateclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new com.example.tech_wheels_admin.DatePicker();
                dialogFragment.show(getSupportFragmentManager(), "date picker");
            }
        });




            }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        date1= DateFormat.getDateInstance().format(c.getTime());
        datedisplay.setText(date1);

        db=FirebaseFirestore.getInstance();
        db.collection("History")
                .whereEqualTo("date",date1 )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myhistories.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myhistory m=new myhistory(
                                        "TimeSlot:   "+document.getString("timeslot"),
                                        "Email:  "+document.getString("email"),
                                        "Date:  "+document.getString("date"),
                                        "Registration No:    "+document.getString("regno"),
                                        "Vehicle Model:  "+document.getString("vehicleModel"),
                                        "TimeStamp:  "+document.getTimestamp("timestamp").toDate(),
                                        "Status: "+document.getString("status")


                                );

                                myhistories.add(m);
                                Log.d(TAG,m.getDate()+m.getEmail()+m.getRegno()+m.getTimeslot()+m.getTimestamp()+m.getVehicleModel()+m.getStatus());
                            }
                            if(myhistories.isEmpty())
                            {
                                Toast.makeText(getApplicationContext()," No logs for "+date1,Toast.LENGTH_LONG).show();
                                return;
                            }
                            adapter=new RecyclerAdapter(myhistories,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            for(myhistory m:myhistories){
                                Log.d("myop",m.getEmail()+m.getRegno());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
