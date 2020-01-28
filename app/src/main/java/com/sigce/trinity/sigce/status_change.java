package com.sigce.trinity.sigce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class status_change extends AppCompatActivity {

    private TextInputLayout status_Layout;
    private Button status_btn;

    private DatabaseReference databaseReference;
    private FirebaseUser cuser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_change);
        cuser= FirebaseAuth.getInstance().getCurrentUser();
        String uid=cuser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("user").child(uid);

        String status_value=getIntent().getStringExtra("status_value");

        status_Layout=(TextInputLayout) findViewById(R.id.status_change);
        status_btn=(Button) findViewById(R.id.status_change_btn);

        status_Layout.getEditText().setText(status_value);
        status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog =new ProgressDialog(status_change.this);
                progressDialog.setTitle("Saving Changes");
                progressDialog.setMessage("Please wait until we save Changes");
                progressDialog.show();
                String status= status_Layout.getEditText().getText().toString();

              databaseReference.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {

                      if(task.isSuccessful()){
                          progressDialog.dismiss();
                          startActivity(new Intent(getApplicationContext(),profile.class));
                          finish();

                      }else {

                          Toast.makeText(getApplicationContext(),"error occured",Toast.LENGTH_LONG).show();
                      }
                  }
              });
            }
        });
    }

}
