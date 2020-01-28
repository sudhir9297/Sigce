package com.sigce.trinity.sigce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;



import de.hdodenhof.circleimageview.CircleImageView;


public class profile extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private FirebaseUser currentuser;
    private StorageReference mStorageRef;

    private FirebaseAuth auth1;

    //all values variable
    private CircleImageView profileimage;
    private ImageView statusEditView;
    private TextView  cname,cdob,caddress,coccupation,cemail,cphone,cstatus,cyear,cdepartment,change_profile_img;

    private static final int GALLERY_VAL = 1;

    private ProgressDialog rProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getting reference to their ids
        statusEditView =(ImageView)findViewById(R.id.profile_status_edit);
        profileimage =(CircleImageView) findViewById(R.id.profile_image);
        cname =(TextView) findViewById(R.id.profile_name);
        cdob =(TextView) findViewById(R.id.profile_dob);
        caddress =(TextView) findViewById(R.id.profile_address);
        cemail =(TextView) findViewById(R.id.profile_email);
        cphone =(TextView) findViewById(R.id.profile_phone);
        cstatus =(TextView) findViewById(R.id.profile_status);
        cyear =(TextView) findViewById(R.id.profile_year);
        cdepartment =(TextView) findViewById(R.id.profile_dept);
        change_profile_img= (TextView) findViewById(R.id.change_profile_pic);


        //firebase instances
        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid= currentuser.getUid();
        mStorageRef=FirebaseStorage.getInstance().getReference();

      /*  databaseReference=FirebaseDatabase.getInstance().getReference().child("departmenmt");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final String dept=datas.getKey();
                    Toast.makeText(profile.this,dept,Toast.LENGTH_LONG).show();

                    databaseReference=FirebaseDatabase.getInstance().getReference().child("departmenmt").child(dept).child("users");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot datats: dataSnapshot.getChildren()){
                                final String occupation=datats.getKey();
                                Toast.makeText(profile.this,occupation,Toast.LENGTH_LONG).show();

                                databaseReference = FirebaseDatabase.getInstance().getReference().child("department").child(dept).child("users").child(occupation).child(current_uid);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Toast.makeText(profile.this,occupation,Toast.LENGTH_LONG).show();

                                        String name=dataSnapshot.child("name").getValue().toString();
                                        String dob=dataSnapshot.child("dob").getValue().toString();
                                        String department=dataSnapshot.child("department").getValue().toString();
                                        String email=dataSnapshot.child("email").getValue().toString();
                                        String mobile=dataSnapshot.child("mobile").getValue().toString();
                                        String address=dataSnapshot.child("address").getValue().toString();
                                        String image=dataSnapshot.child("image").getValue().toString();
                                        //String thumb_image=dataSnapshot.child("thumb_image").getValue().toString();
                                        String year=dataSnapshot.child("year").getValue().toString();
                                        String status=dataSnapshot.child("status").getValue().toString();
                                        //String occupation=dataSnapshot.child("occupation").getValue().toString();

                                        cname.setText(name);
                                        cdob.setText(dob);
                                        cdepartment.setText(department);
                                        cemail.setText(email);
                                        cphone.setText(mobile);
                                        caddress.setText(address);
                                        cyear.setText(year);
                                        cstatus.setText(status);
                                        //coccupation.setText(occupation);

                                        Picasso.get().load(image).into(profileimage);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        //pointing to user value using current uid
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(current_uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String name=dataSnapshot.child("name").getValue().toString();
                String dob=dataSnapshot.child("dob").getValue().toString();
                String department=dataSnapshot.child("department").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String mobile=dataSnapshot.child("mobile").getValue().toString();
                String address=dataSnapshot.child("address").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
              //String thumb_image=dataSnapshot.child("thumb_image").getValue().toString();
                String year=dataSnapshot.child("year").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
               //String occupation=dataSnapshot.child("occupation").getValue().toString();

               cname.setText(name);
               cdob.setText(dob);
               cdepartment.setText(department);
               cemail.setText(email);
               cphone.setText(mobile);
               caddress.setText(address);
               cyear.setText(year);
               cstatus.setText(status);
              //coccupation.setText(occupation);

                Picasso.get().load(image).into(profileimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        statusEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status_value=cstatus.getText().toString();
                Intent intent=new Intent(profile.this,status_change.class);
                intent.putExtra("status_value",status_value);
                startActivity(intent);
            }
        });

        change_profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent= new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"Selected_image"),GALLERY_VAL);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_VAL && resultCode==RESULT_OK){

           Uri imageUri= data.getData();

            CropImage.activity(imageUri)
                    .setAspectRatio(1,1)
                    .start(this);
        }

       if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                String current_uid= currentuser.getUid();
                final StorageReference filepath=mStorageRef.child("profile_images/").child(current_uid+".jpg");
                filepath.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                rProgress=new ProgressDialog(profile.this);
                                rProgress.setTitle("Please Wait");
                                rProgress.setMessage("we are uploading just wait for few second..");
                                rProgress.setCanceledOnTouchOutside(false);
                                rProgress.show();

                                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri download_url= uri;
                                        databaseReference.child("image").setValue(download_url.toString());
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                rProgress.dismiss();
                                            }
                                        },3000);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(profile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        rProgress.dismiss();
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //Exception error = result.getError();

            }
        }
    }
}
