package com.hackuniv.daanveer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackuniv.daanveer.Model.Users;
import com.hackuniv.daanveer.databinding.ActivitySignUpPhoneNumberBinding;

import java.util.concurrent.TimeUnit;

public class SignUpPhoneNumber extends AppCompatActivity {
    ActivitySignUpPhoneNumberBinding binding;
    FirebaseDatabase database;
    private FirebaseAuth auth;
    int counter = 0;
    // string for storing our verification ID
    private String verificationId;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(SignUpPhoneNumber.this);
        progressDialog.setTitle("Fetching your details");
        progressDialog.setMessage("Just a moment");
        Log.d("SachinKadian","Aab SignUp execute hua h");


        binding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.etPhone.getText().toString())) {
                    // when mobile number text field is empty
                    // displaying a toast message.
                    Toast.makeText(SignUpPhoneNumber.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    // if the text field is not empty we are calling our
                    // send OTP method for getting OTP from Firebase.
                    String phone = "+91" + binding.etPhone.getText().toString();

                    sendVerificationCode(phone);
                }
            }
        });


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.etSetOtp.getText().toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(SignUpPhoneNumber.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(binding.etSetOtp.getText().toString());
                }
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
                if(auth.getCurrentUser() != null){
                    Log.d("SachinKadian","Aab onStart() execute hua h");
//                    database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Users users = snapshot.getValue(Users.class);
//                            Log.d("SachinKadian", "this is "+users.getUserType());
//                            if(users.getUserType().equals("Donator")){
//                                startActivity(new Intent(SignUpPhoneNumber.this,DonatorActivity.class));
//                                finish();
//                            }
//                            else if(users.getUserType().equals("Organizer")){
//                                startActivity(new Intent(SignUpPhoneNumber.this,CharityActivity.class));
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });


//            Toast.makeText(this, "You're Already Signed-In!! 😁", Toast.LENGTH_SHORT).show();
        }
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.show();
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.

//                            database.getReference().child("Users").child(auth.getUid()).child("Phone No").setValue(binding.etPhone.getText().toString());
//                            database.getReference().child("Users").child(auth.getUid()).child("User Profile Created").addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if(!(snapshot.getValue(String.class).equals("1")))
//                                        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").setValue("0");
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("User Profile Created").setValue("0");
//                            Log.d("Checkin","this is updated here");
//                            Toast.makeText(SignUpPhoneNumber.this, "Phone Number Saved", Toast.LENGTH_LONG).show();
                            //yhaan se *********************************************************************************************************************************************************
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
                                                        startActivity(new Intent(SignUpPhoneNumber.this,DonatorActivity.class));
                                                        finish();
                                                    }else if(type.equals("Organizer")){
                                                        Log.d("SachinKadian", "this is organiser");
                                                        startActivity(new Intent(SignUpPhoneNumber.this,CharityActivity.class));
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
                                    progressDialog.dismiss();
                                    Log.d("SachinKadian","koi bhi history nhi mili bro" + counter);
                                    if(counter == 0) {
                                        Log.d("SachinKadian","toh usertype kholte h chlo");
                                        Intent i = new Intent(SignUpPhoneNumber.this, UserType.class);
                                        startActivity(i);
                                        finish();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            //*********************************************************************************************************************************************
//                            if(counter == 0) {
//                                Log.d("SachinKadian", "now counter is zero...");
//                                Intent i = new Intent(SignUpPhoneNumber.this, UserType.class);
//                                startActivity(i);
//                                finish();
//                            }
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(SignUpPhoneNumber.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.

//        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)            // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }
        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                binding.etSetOtp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }
        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(SignUpPhoneNumber.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }
}