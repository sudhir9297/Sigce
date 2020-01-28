package com.sigce.trinity.sigce;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class classmates extends AppCompatActivity {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mToggle;
    private RecyclerView ClassmateList;

    private DatabaseReference rDatabaseRef;
    List<Users> list = new ArrayList<>();
    RecyclerView.Adapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classmates);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Classmates");


        rDatabaseRef = FirebaseDatabase.getInstance().getReference().child("user");

        ClassmateList = (RecyclerView) findViewById(R.id.recyclerView);
        ClassmateList.setHasFixedSize(true);
        ClassmateList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options=
                new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(rDatabaseRef,Users.class)
                .build();

        FirebaseRecyclerAdapter<Users,UserViewHolder> adapter= new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, final int position, @NonNull Users model) {

                holder.setName(model.getName());
                holder.setStatus(model.getStatus());
                holder.setThumb_img(model.getThumb_img());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clkd_user_id=getRef(position).getKey();
                        Intent intent=new Intent(classmates.this,clkd_user_profile.class);
                        intent.putExtra("user_id",clkd_user_id);
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_singlelayout,parent,false);
                UserViewHolder holder = new UserViewHolder(view);
                return holder;
            }
        };

        ClassmateList.setAdapter(adapter);
        adapter.startListening();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        View rview;

        public UserViewHolder(View itemView) {
            super(itemView);
            rview = itemView;
        }
        public void setName(String name) {

            TextView singleusername = (TextView) rview.findViewById(R.id.single_displayname);
            singleusername.setText(name);
        }
        public void setStatus(String name) {

            TextView singleStatus = (TextView) rview.findViewById(R.id.single_status);
            singleStatus.setText(name);
        }

        public void setThumb_img(String thumb_image) {
            CircleImageView user_img = (CircleImageView) rview.findViewById(R.id.thumb_image);
            Picasso.get().load(thumb_image).placeholder(R.drawable.default_img).into(user_img);
        }
    }
}