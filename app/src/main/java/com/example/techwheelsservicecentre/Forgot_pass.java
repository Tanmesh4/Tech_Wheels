package com.example.techwheelsservicecentre;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_pass extends AppCompatActivity {

    private Button resetPassword;
    private EditText passwordEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        passwordEmail = (EditText)findViewById(R.id.forgotemail);
        resetPassword = (Button)findViewById(R.id.resetpass);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = passwordEmail.getText().toString().trim();
                if(useremail.equals("")){
                    Toast.makeText(Forgot_pass.this,"Please enter valid email id",Toast.LENGTH_SHORT).show();

                }
                else {
                    firebaseAuth.sendPasswordResetEmail(useremail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Forgot_pass.this,"Password reset email sent",Toast.LENGTH_SHORT).show();

                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"Error in sending password reset email",Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                    }

                                }
                            });
                }
            }
        });

        TextView backTomain=(TextView)findViewById(R.id.back);
        backTomain.setMovementMethod(LinkMovementMethod.getInstance());
        backTomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Forgot_pass.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
