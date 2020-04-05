package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class cie1 extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    EditText cie1t, cie1q, cie2t, cie2q, cie3t, cie3q, self, total, names, number;
    Button submit, sms;
    DatabaseReference rootRef,cie1ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cie1);

        cie1t = (EditText) findViewById(R.id.cie1t);
        cie2t = (EditText) findViewById(R.id.cie2t);
        cie3t = (EditText) findViewById(R.id.cie3t);
        cie1q = (EditText) findViewById(R.id.cie1q);
        cie2q = (EditText) findViewById(R.id.cie2q);
        cie3q = (EditText) findViewById(R.id.cie3q);
        self = (EditText) findViewById(R.id.self);
        cie1t = (EditText) findViewById(R.id.cie1t);
        total = (EditText) findViewById(R.id.total);
        names = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);
        submit = (Button) findViewById(R.id.submit);
        sms = (Button) findViewById(R.id.sms);
        rootRef = FirebaseDatabase.getInstance().getReference();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cie1_t = cie1t.getText().toString();
                String cie2_t = cie2t.getText().toString();
                String cie3_t = cie3t.getText().toString();
                String cie1_q = cie1q.getText().toString();
                String cie2_q = cie2q.getText().toString();
                String cie3_q = cie3q.getText().toString();
                String selfstudy = self.getText().toString();
                String tot = total.getText().toString();
                String phone = number.getText().toString();
                String name = names.getText().toString();



                rootRef.child(name);
                rootRef.child(name).setValue(phone);
                


                rootRef.child(name).child("CIE-1").child("Test").setValue(cie1_t);
                rootRef.child(name).child("CIE-1").child("Quiz").setValue(cie1_q);

                rootRef.child(name).child("CIE-2").child("Test").setValue(cie2_t);
                rootRef.child(name).child("CIE-2").child("Quiz").setValue(cie2_q);

                rootRef.child(name).child("CIE-3").child("Test").setValue(cie3_t);
                rootRef.child(name).child("CIE-3").child("Quiz").setValue(cie3_q);

                rootRef.child(name).child("Self Study").setValue(selfstudy);


                Toast.makeText(getApplicationContext(), "Inserted Data into Database", Toast.LENGTH_SHORT).show();
                sms.setVisibility(View.VISIBLE);

            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMSMessage();
            }
        });
    }


    protected void sendSMSMessage() {
        String cie1_t = cie1t.getText().toString();
        String cie2_t = cie2t.getText().toString();
        String cie3_t = cie3t.getText().toString();
        String cie1_q = cie1q.getText().toString();
        String cie2_q = cie2q.getText().toString();
        String cie3_q = cie3q.getText().toString();
        String selfstudy = self.getText().toString();
        String tot = total.getText().toString();
        String phone = number.getText().toString();
        String name = names.getText().toString();

        int icie1_t = Integer.parseInt(cie1_t);
        int icie2_t = Integer.parseInt(cie2_t);
        int icie3_t = Integer.parseInt(cie3_t);
        int icie1_q = Integer.parseInt(cie1_q);
        int icie2_q = Integer.parseInt(cie2_q);
        int icie3_q = Integer.parseInt(cie3_q);
        int iself = Integer.parseInt(selfstudy);
        double total = (icie1_t + icie2_t + icie3_t)*0.4 + (iself + icie1_q + icie2_q + icie3_q);
        String stotal = Double.toString(total);
        String sstotal = stotal.substring(0,2);
        SmsManager smsManager = SmsManager.getDefault();
        String msg = "Hello " + name + ", your CIE marks in Global Elective subject is (test,quiz):" + "\n" + "CIE-1: " + cie1_t + "," + cie1_q + "\n" +  "CIE-2: " + cie2_t + "," + cie2_q  + "\n" + "CIE-3: " + cie3_t + "," + cie3_q  + "\n" + "Self Study: "+ selfstudy + "\n" + "Total: " + sstotal + "/" + tot;
        smsManager.sendTextMessage(phone,null,msg,null,null);
        Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Thanks for permitting", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not permitted", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

}
