package com.example.techwheelsservicecentre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Sign_up extends AppCompatActivity implements View.OnClickListener {
    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editFullName;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private String fullname,email,password,emailPattern,phonepattern,phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView backtomain=(TextView)findViewById(R.id.lnkLogin);
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.txtEmail);
        editTextPassword = (EditText) findViewById(R.id.txtPwd);
        editFullName = (EditText)   findViewById(R.id.txtName);

        buttonRegister = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //attaching listener to button
        buttonRegister.setOnClickListener(this);


        backtomain.setMovementMethod(LinkMovementMethod.getInstance());
        backtomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_up.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        fullname = editFullName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        TextView phone=(TextView)findViewById(R.id.phone_view);
        phoneno=phone.getText().toString().trim();
        String passpattern="^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        phonepattern="(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6-9]\\d{9}$";
        if(TextUtils.isEmpty(fullname)){
            //name is empty
            Toast.makeText(this, "Please Enter FullName",Toast.LENGTH_SHORT).show();
            //stopping further execution
            return;
        }
        if(TextUtils.isEmpty(email)|| !email.matches(emailPattern)){
            //email is empty or pattern does not match
            Toast.makeText(this, "Please Enter valid email",Toast.LENGTH_SHORT).show();
            //stopping further execution
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please Enter password",Toast.LENGTH_SHORT).show();
            //stopping further execution
            return;
        }

        if(password.length()<6){
            Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.matches(passpattern)){
            Toast.makeText(getApplicationContext(),"Password should contain 8 char,1 digit,1 lower and Upper alpha char"+"\n"+" and one special char",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(phoneno)) {
            Toast.makeText(getApplicationContext(), "Please enter Phone number", Toast.LENGTH_LONG).show();
            return;
        }
        if(!phoneno.matches(phonepattern)) {
            Toast.makeText(getApplicationContext(), "Please enter valid Phone number", Toast.LENGTH_LONG).show();
            return;
        }



            //if validations are ok
        //creating a new user
        progressDialog.setMessage("Registering User...");

        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            //sendUserData();

                            Map<String, Object> User = new HashMap<>();
                            User.put("name", fullname);
                            User.put("email", email);
                            User.put("phoneno",phoneno);

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            db.collection("Users").document(email)
                                    .set(User)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent=new Intent(Sign_up.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            //Log.d(TAG, "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //Log.w(TAG, "Error writing document", e);
                                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();


                                        }
                                    });

                            Toast.makeText(getApplicationContext(),"Registered Successfully.Please LogIn",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                        else{
                            //display some message here
                            Toast.makeText(getApplicationContext(),"Could not register. Please try Again",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
    }

}
