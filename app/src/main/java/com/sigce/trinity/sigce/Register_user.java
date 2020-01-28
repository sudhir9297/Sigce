package com.sigce.trinity.sigce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_user extends AppCompatActivity{

    private TextInputLayout rName,rDob,rCollegeUid,rDepartment,rYear,rOccupation,rPhone,rAddress,rPassword,rEmail;
    private Button rButton;

    private FirebaseAuth rAuth;
    private FirebaseAuth rAuth2;

    private DatabaseReference rDatabase;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

       rName=(TextInputLayout) findViewById(R.id.username);
       rDob=(TextInputLayout) findViewById(R.id.date_of_birth);
       rCollegeUid=(TextInputLayout) findViewById(R.id.clg_uid);
       rDepartment=(TextInputLayout) findViewById(R.id.department);
       rYear=(TextInputLayout) findViewById(R.id.year);
       rOccupation=(TextInputLayout) findViewById(R.id.occupation);
       rEmail=(TextInputLayout) findViewById(R.id.email);
       rPhone=(TextInputLayout) findViewById(R.id.phone);
       rAddress=(TextInputLayout) findViewById(R.id.address);
       rPassword=(TextInputLayout) findViewById(R.id.rPassword);

        rButton=(Button) findViewById(R.id.Register_btn);

        rAuth=FirebaseAuth.getInstance();

        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String username=rName.getEditText().getText().toString();
               String Dob=rDob.getEditText().getText().toString();
               String clg_uid=rCollegeUid.getEditText().getText().toString();
               String year=rYear.getEditText().getText().toString();
               String dept=rDepartment.getEditText().getText().toString();
               String occupation=rOccupation.getEditText().getText().toString();
               String email=rEmail.getEditText().getText().toString();
               String default_pass=rPassword.getEditText().getText().toString();
               String phone=rPhone.getEditText().getText().toString();
               String address=rAddress.getEditText().getText().toString();

              register_user(username,Dob,clg_uid,year,dept,occupation,email,default_pass,phone,address);

               progressDialog = new ProgressDialog(Register_user.this);
               progressDialog.setTitle("Registering");
               progressDialog.setMessage("Please Wait for few seconds");
               progressDialog.setCanceledOnTouchOutside(false);
               progressDialog.show();

            }
        });

    }

    private void register_user(final String username,final String Dob,final String clg_uid,final String year,final String dept,final String occupation,final String email, String default_pass,final String phone,final String address) {

        rAuth.createUserWithEmailAndPassword(email,default_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    String rUser=FirebaseAuth.getInstance().getUid();

                    rDatabase= FirebaseDatabase.getInstance().getReference().child("department").child(dept).child("users").child(occupation).child(rUser);
                    HashMap<String,String> usermap=new HashMap<>();
                    usermap.put("name",username);
                    usermap.put("dob",Dob);
                    usermap.put("clg_uid",clg_uid);
                    usermap.put("department",dept);
                    usermap.put("image","default");
                    usermap.put("thumb_image","default");
                    usermap.put("status","Now I'm a part of Sigce family");
                    usermap.put("year",year);
                    usermap.put("occupation",occupation);
                    usermap.put("mobile",phone);
                    usermap.put("address",address);
                    usermap.put("email",email);

                    rDatabase.setValue(usermap);

                    progressDialog.dismiss();
                    Intent intent=new Intent(Register_user.this,dashboard.class);
                    startActivity(intent);
                    finish();


                }else
                {
                    Toast.makeText(Register_user.this,"failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    } 
}
