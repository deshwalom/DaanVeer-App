package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Users;

public class SplashActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth auth;
    int counter = 0;
    BroadcastReceiver broadcastReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Log.d("SachinKadian","splashAct");

        broadcastReceiver = new InternetReceiver();
        Internetstatus();
//        Thread thread = new Thread(){
//            public void run(){
//                try{
//                    sleep(1000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                finally {
//                    Intent intent = new Intent(SplashActivity.this,SignUpPhoneNumber.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };
//        thread.start();
    }
    public void Internetstatus(){
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }
    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
        if(auth.getCurrentUser() != null){


            database.getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot snapshot1:snapshot.getChildren()){
//                                        progressDialog.show();
                        String id = snapshot1.getKey().toString();
                        Log.d("SachinKadian",id + " is uId");
                        Log.d("SachinKadian",auth.getUid() + " is current uId");
                        if(id.equals(auth.getCurrentUser().getUid())){
                            counter++;
                            Log.d("SachinKadian", "id is matched");
                            database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Users users = snapshot.getValue(Users.class);
                                    String type = users.getUserType();
                                    Log.d("SachinKadian", "this is usertype " + type);
                                    Log.d("SachinKadian", "this is id " + auth.getUid());
                                    if(type.equals("Donator")){
                                        Log.d("SachinKadian", "this is donator");
                                        startActivity(new Intent(SplashActivity.this,DonatorActivity.class));
                                        finish();
                                    }else if(type.equals("Organizer")){
                                        Log.d("SachinKadian", "this is organiser");
                                        startActivity(new Intent(SplashActivity.this,CharityActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            break;
                        }

                    }
//                    progressDialog.dismiss();
                    Log.d("SachinKadian","koi bhi history nhi mili bro" + counter);
                    if(counter == 0) {
                        Log.d("SachinKadian","toh usertype kholte h chlo");
                        Intent i = new Intent(SplashActivity.this, UserType.class);
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


//***********************************************************************************************************5:45 12-03-23
//            database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Users users = snapshot.getValue(Users.class);
//                    Log.d("SachinKadian", "this is "+users.getUserType());
//                    Log.d("SachinKadian", "this is "+auth.getCurrentUser());
//                    if(users.getUserType().equals("Donator")){
//                        startActivity(new Intent(SplashActivity.this,DonatorActivity.class));
//                        finish();
//                    }
//                    else if(users.getUserType().equals("Organizer")){
//                        startActivity(new Intent(SplashActivity.this,CharityActivity.class));
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//*******************************************************************************************************5:45 12-03-23

//            Toast.makeText(this, "You're Already Signed-In!! 😁", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.d("SachinKadian","ye new user h bro");
            Intent intent = new Intent(SplashActivity.this,SignUpPhoneNumber.class);
            startActivity(intent);
            finish();
        }
    }


}