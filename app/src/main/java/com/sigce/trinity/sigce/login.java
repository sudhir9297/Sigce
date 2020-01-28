package com.sigce.trinity.sigce;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextemail;
    private EditText edittextpassword;
    private Button loginbtn;
    private TextView forgettext;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null) {
            //start dashboard activity
            finish();
            startActivity(new Intent(getApplicationContext(), dashboard.class));
        }

        mprogress =new ProgressDialog(this);
        editTextemail = (EditText) findViewById(R.id.username);
        edittextpassword = (EditText) findViewById(R.id.password);
        loginbtn= (Button) findViewById(R.id.login_btn);
        forgettext = (TextView) findViewById(R.id.forget_pass);


        loginbtn.setOnClickListener(this);
        forgettext.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v == loginbtn)
        {
            mprogress.setTitle("Logging In");
            mprogress.setMessage("Please wait while we check your credential");
            mprogress.setCanceledOnTouchOutside(false);
            mprogress.show();
            loginuser();
        }

        if(v == forgettext){
            finish();
            startActivity(new Intent(this,forgot.class));

        }
    }

    private void loginuser(){
        String email= editTextemail.getText().toString().trim();
        String password= edittextpassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please Enter email", Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"please Enter password",Toast.LENGTH_LONG).show();
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {

                         if(task.isSuccessful()){
                             mprogress.dismiss();
                             finish();
                             startActivity(new Intent(getApplicationContext(),dashboard.class));
                         }
                         else {
                             mprogress.hide();
                             Toast.makeText(login.this,"Wrong Email or Password",Toast.LENGTH_LONG).show();
                         }
                     }
                 });
    }


}
