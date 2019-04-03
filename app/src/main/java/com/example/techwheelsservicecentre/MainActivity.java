package com.example.techwheelsservicecentre;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private long back;

    private ImageView bookIconImageView;
    private TextView bookITextView;
    private ProgressBar loadingProgressBar;
    private RelativeLayout rootView, afterAnimationView;
    private Button loginButton;

    //defining views
    private EditText editTextEmail;
    //Shared pref
    SharedPreferences sp;

    @Override
    public void onBackPressed() {
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

    private EditText editTextPassword;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        initViews();

        //Shared pref
        sp = getSharedPreferences("loginbutton",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            Intent intent=new Intent(MainActivity.this,Dashboard_TW.class);
            startActivity(intent);
            finish();

        }


        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                bookITextView.setVisibility(GONE);
                loadingProgressBar.setVisibility(GONE);
                // rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSplashText));

                bookIconImageView.setImageResource(R.drawable.try_icon);
                startAnimation();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        /*
        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null) {
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        */
        //initializing views
        editTextEmail = (EditText) findViewById(R.id.txtEmail);
        editTextPassword = (EditText) findViewById(R.id.txtPwd);
        progressDialog = new ProgressDialog(this);


        loginButton.setOnClickListener(this);

        TextView forpass=(TextView)findViewById(R.id.forgotpass);
        forpass.setMovementMethod(LinkMovementMethod.getInstance());
        forpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Forgot_pass.class);
                startActivity(intent);
            }
        });

        TextView signup=(TextView)findViewById(R.id.signup);
        signup.setMovementMethod(LinkMovementMethod.getInstance());
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Sign_up.class);
                startActivity(intent);
            }





        });


    }

    private void initViews() {
        bookIconImageView = findViewById(R.id.bookIconImageView);
        bookITextView = findViewById(R.id.bookITextView);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        rootView = findViewById(R.id.rootView);
        afterAnimationView = findViewById(R.id.afterAnimationView);
        loginButton=(Button)findViewById(R.id.loginButton);



    }

    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(40f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void checkEmailVerification(){
        // FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag;
        // emailflag= firebaseUser.isEmailVerified();
        emailflag=true;
        if(emailflag){

            //firebaseAuth.signOut();
        }
        else {
            Toast.makeText(this,"Verify Your Registerd Email",Toast.LENGTH_SHORT).show();
            return;
        }

    }
    //method for user login
    private void userLogin(){
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Logging In Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            System.out.println("Login succeessful");
                            Intent intent=new Intent(MainActivity.this,Dashboard_TW.class);
                            intent.putExtra("key",email);
                            startActivity(intent);
                            finish();
                            //Shared pref
                            sp.edit().putBoolean("logged",true).apply();


                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Incorrect Email-id or Password ",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v==loginButton){
            userLogin();
        }

    }
}
/*


2. your car
3. history view
4. navigation bar //

 */