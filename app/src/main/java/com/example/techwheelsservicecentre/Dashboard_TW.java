package com.example.techwheelsservicecentre;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Dashboard_TW extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private long back;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    private DocumentReference docRef;
    private TextView username;
    private TextView emailID;
    private static final String TAG = "Dashboard_TW";

    //String recive_email;
    private static String name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard__tw);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        //recive_email=intent.getStringExtra("key");
        //Toast.makeText(getApplicationContext(),recive_email,Toast.LENGTH_SHORT).show();

        Log.d(TAG,"Toast");
        final TextView dash=(TextView)findViewById(R.id.dash);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            //Log.d(TAG,"Mydocument"+ name);
            //Toast.makeText(getApplicationContext(),"Welcome "+name+"!",Toast.LENGTH_LONG).show();
            email=user.getEmail();
            Log.d(TAG,"auth"+email);

        }
        else {
            Toast.makeText(getApplicationContext(),"Cannot retrive User",Toast.LENGTH_LONG).show();

        }

        docRef = db.collection("Users").
                document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        name=document.getString("name");
                        //email=document.getString("email");
                        Log.d(TAG, "Docname" + name);
                        emailID.setText(email);
                        username.setText(name);
                        TextView tv=(TextView)findViewById(R.id.dash);
                      tv.setText("HI,"+name+"!");


                    } else {
                        Log.d( TAG,"No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "\t\t\t\t! Tech Wheels is happy to Serve !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        emailID=(TextView)headerView.findViewById(R.id.emailId);
        username=(TextView)headerView.findViewById(R.id.userN);

        emailID.setText(email);
        username.setText(name);

        TextView book=(findViewById(R.id.buttonorder));
        book.setMovementMethod(LinkMovementMethod.getInstance());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard_TW.this,Book_slot.class);
                intent.putExtra("key",email);
                startActivity(intent);


            }
        });

        Button button1=(findViewById(R.id.service));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard_TW.this,Our_Services.class);
                startActivity(intent);

            }
        });
        Button button4=(findViewById(R.id.history));
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard_TW.this,History.class);
                intent.putExtra("email",email);
                startActivity(intent);

            }
        });

        Button button2=(Button) (findViewById(R.id.car_view));
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard_TW.this,Your_Car.class);
                startActivity(intent);

            }


        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(back+2000>System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else {
                Toast.makeText(getBaseContext(),"Press Back Again to Exit",Toast.LENGTH_SHORT).show();
            }
            back=System.currentTimeMillis();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard__tw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_techwheels) {
           aboutUs();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void aboutUs() {
        Intent intent=new Intent(Dashboard_TW.this,About_Us.class);
        startActivity(intent);
    }

    public void logoutfrom(MenuItem menuItem)
    {
        customDialog("Logut","Do you want to Logout?","cancelfrom","okfrom");
    }
    public void cancelfrom()
    {

    }
    public void okfrom()
    {

        Intent intent=new Intent(Dashboard_TW.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_LONG).show();
        finish();
        SharedPreferences preferences =getSharedPreferences("loginbutton",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

    }

    public void customDialog(String title,String message,final String cancel,final String ok)
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_stat_name);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cancel.equals("cancelfrom"))
                            cancelfrom();
                    }
                }
        );

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(ok.equals("okfrom"))
                    okfrom();
            }
        });
        builder.show();
    }


}
