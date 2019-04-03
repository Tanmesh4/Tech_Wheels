package com.example.techwheelsservicecentre;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class History extends AppCompatActivity {
    private static String email;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentReference docRef;
    private static String TAG="Firebase";
    private static ArrayList<myhistory> myhistories;
    private static RecyclerView recyclerView;
    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerAdapter adapter;

    // ListView listView;

    //private List<String> name
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent i=getIntent();
        email=i.getStringExtra("email");
        // Query docRef = db.collection("History").whereEqualTo("email", email);

        //docRef=db.collection("History").document();


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myhistories = new ArrayList<>();


        db.collection("History")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
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

/*
        adapter=new RecyclerAdapter(myhistories,this);
        recyclerView.setAdapter(adapter);

        for(myhistory m:myhistories){
            Log.d("myop",m.getEmail()+m.getRegno());
        }

*/
    }
}
