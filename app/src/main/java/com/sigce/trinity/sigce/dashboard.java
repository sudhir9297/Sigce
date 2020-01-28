package com.sigce.trinity.sigce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class dashboard extends AppCompatActivity{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mToggle;
    NavigationView navi;

    BottomNavigationView btmnavi;
    FrameLayout btmframe;

    private ImageView imageView;
    private TextView  Disp_name;

    private dash dash;
    private activity activity;
    private todo todo;
    private chat chats;
    private setting setting;

    private FirebaseUser current_user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_activity);
        setUpToolbar();
        getSupportActionBar().setTitle("Dashboard");
        current_user=FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("user").child(current_user.getUid());




        //btm navigation stuff
        btmnavi = (BottomNavigationView) findViewById(R.id.btm_navi);
        BottomNavigationViewHelper.disableShiftMode(btmnavi);
        btmframe = (FrameLayout) findViewById(R.id.framelayout);

        dash = new dash();
        activity = new activity();
        todo = new todo();
        chats = new chat();
        setting = new setting();

        setUPfragment(dash);

        btmnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.btmnavi_dashboard:

                        setUPfragment(dash);
                        return true;

                    case R.id.btmnavi_activity:
                        setUPfragment(activity);
                        return true;

                    case R.id.btmnavi_todolist:
                        setUPfragment(todo);
                        return true;

                    case R.id.btmnavi_chats:
                        setUPfragment(chats);
                        return true;

                    case R.id.btmnavi_settings:
                        setUPfragment(setting);
                        return true;

                        default:
                            return false;
                }


            }
        });

        //side navigation stuff
        navi =(NavigationView) findViewById(R.id.navigationmenu);
        View hView =  navi.getHeaderView(0);
        imageView=(ImageView)hView.findViewById(R.id.navi_profile_picture);
        Disp_name=(TextView)hView.findViewById(R.id.navi_profile_name);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String navi_name = dataSnapshot.child("name").getValue().toString();
                String navi_image = dataSnapshot.child("image").getValue().toString();

                Disp_name.setText(navi_name);
                Picasso.get().load(navi_image).placeholder(R.drawable.default_img).into(imageView);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        navi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.navi_profile:
                       {
                           Intent intent=new Intent(dashboard.this,profile.class);
                           startActivity(intent);
                        break;
                       }
                   case R.id.navi_event:
                   {
                       Intent intent=new Intent(dashboard.this,naviUpcompingEvent.class);
                       startActivity(intent);
                       break;
                   }
                   case R.id.navi_classmate:
                   {
                       Intent intent=new Intent(dashboard.this,classmates.class);
                       startActivity(intent);
                       break;
                   }
                   case R.id.navi_dept:
                   {
                       Intent intent=new Intent(dashboard.this,department.class);
                       startActivity(intent);
                       break;
                   }
                   case R.id.navi_clg:
                   {
                       Intent intent=new Intent(dashboard.this,collage_detail.class);
                       startActivity(intent);
                       break;
                   }
                   case R.id.navi_logout:
                   {
                       FirebaseAuth.getInstance().signOut();
                       Intent intent=new Intent(dashboard.this,login.class);
                       startActivity(intent);
                       finish();
                       break;
                   }
                   case R.id.register_new_user:
                   {
                       Intent intent=new Intent(dashboard.this,Register_user.class);
                       startActivity(intent);
                       break;
                   }
               }
                return false;
            }
        });
    }


    //setting up the fragment for bottom navigation
    private void setUPfragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }


    //setting up the tool bar
    private void setUpToolbar(){
        drawerLayout =(DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

    }

}

