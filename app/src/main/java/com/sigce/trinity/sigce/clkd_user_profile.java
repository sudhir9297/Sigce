package com.sigce.trinity.sigce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class clkd_user_profile extends AppCompatActivity {

    private String usersRec_id;
    private ImageView profile_img;
    private TextView name, status, total_frnd, reqt;

    private DatabaseReference rDatabaseref;

    private DatabaseReference friend_Req_datab;
    private FirebaseUser current_user;

    private String current_state;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clkd_user_profile);

        usersRec_id = getIntent().getExtras().get("user_id").toString();

        rDatabaseref = FirebaseDatabase.getInstance().getReference().child("user").child(usersRec_id);

        friend_Req_datab = FirebaseDatabase.getInstance().getReference().child("friend_req");

        current_user = FirebaseAuth.getInstance().getCurrentUser();

        profile_img = (ImageView) findViewById(R.id.clk_pro_pic);
        name = (TextView) findViewById(R.id.profile_name2);
        status = (TextView) findViewById(R.id.status_pro);
        total_frnd = (TextView) findViewById(R.id.total_friend);
        reqt = (TextView) findViewById(R.id.request1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("please wait until we Load the Data :)");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        current_state ="not_friend";

        rDatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String D_name = dataSnapshot.child("name").getValue().toString();
                String D_status = dataSnapshot.child("status").getValue().toString();
                String D_image = dataSnapshot.child("image").getValue().toString();

                name.setText(D_name);
                status.setText(D_status);
                Picasso.get().load(D_image).placeholder(R.drawable.default_img).into(profile_img);


               // progressDialog.dismiss();
                //..........check for the request value.....

               friend_Req_datab.child(current_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       String req_typ=dataSnapshot.child(usersRec_id).child("request_type").getValue().toString();

                       if(req_typ.equals("received")){
                           current_state="req_received";
                           reqt.setText("Accept Friend Request");
                       }else if(req_typ.equals("sent")){
                           current_state="req_sent";
                           reqt.setText("cancel Friend Request");

                       }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //0= not friend
        //1= sent
        //2=received
        //3=req_sent
        //4=req_received

        //.....................not friend State...................//
        reqt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqt.setEnabled(false);
                if (current_state.equals("not_friend")) {
                    friend_Req_datab.child(current_user.getUid()).child(usersRec_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                friend_Req_datab.child(usersRec_id).child(current_user.getUid()).child("request_type").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        reqt.setEnabled(true);
                                        current_state = "req_sent";
                                        reqt.setText("Cancel Friend Request");
                                       // Toast.makeText(clkd_user_profile.this, "successfull", Toast.LENGTH_LONG);
                                    }
                                });

                            } else {

                                Toast.makeText(clkd_user_profile.this, "Failed", Toast.LENGTH_LONG);
                            }
                        }
                    });
                }

                //....................Cancel Request state...............//

                if (current_state.equals("req_sent")) {
                    friend_Req_datab.child(current_user.getUid()).child(usersRec_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            friend_Req_datab.child(usersRec_id).child(current_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    reqt.setEnabled(true);
                                    current_state ="not_friend";
                                    reqt.setText("send Friend Request");
                                }
                            });
                        }
                    });
                }


            }
        });
    }
}
