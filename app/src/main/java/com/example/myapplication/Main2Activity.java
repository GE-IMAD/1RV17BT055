package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    Button search;
    DatabaseReference showref, noderef;
    EditText searchedname;
    TextView marksv,totalv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        search = findViewById(R.id.searchbutton);
        searchedname = findViewById(R.id.name);
        marksv = findViewById(R.id.marksview);
        totalv = findViewById(R.id.totalview);
        noderef = FirebaseDatabase.getInstance().getReference();
        marksv.setVisibility(View.GONE);
        totalv.setVisibility(View.GONE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = searchedname.getText().toString();
                showref = noderef.child(name);
                showref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String cie1t  = dataSnapshot.child("CIE-1").child("Test").getValue(String.class);
                        String cie1q  = dataSnapshot.child("CIE-1").child("Quiz").getValue(String.class);
                        String cie2t  = dataSnapshot.child("CIE-2").child("Test").getValue(String.class);
                        String cie2q  = dataSnapshot.child("CIE-2").child("Quiz").getValue(String.class);
                        String cie3t  = dataSnapshot.child("CIE-3").child("Test").getValue(String.class);
                        String cie3q  = dataSnapshot.child("CIE-3").child("Quiz").getValue(String.class);
                        String selfstudy = dataSnapshot.child("Self Study").getValue(String.class);

                        int icie1_t = Integer.parseInt(cie1t);
                        int icie2_t = Integer.parseInt(cie2t);
                        int icie3_t = Integer.parseInt(cie3t);
                        int icie1_q = Integer.parseInt(cie1q);
                        int icie2_q = Integer.parseInt(cie2q);
                        int icie3_q = Integer.parseInt(cie3q);
                        int iself = Integer.parseInt(selfstudy);

                        String marks_list = cie1q + "," + cie1t + "," + cie2q + "," + cie2t + "," + cie3q + "," + cie3t + "," + selfstudy;
                        marksv.setText(marks_list);

                        double total = (icie1_t + icie2_t + icie3_t)*0.4 + (iself + icie1_q + icie2_q + icie3_q);
                        String stotal = Double.toString(total);
                        String sstotal = stotal.substring(0,4);
                        String total_marks = sstotal + "/" + "100";
                        totalv.setText(total_marks);
                        totalv.setVisibility(View.VISIBLE);
                        marksv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"No such entry",Toast.LENGTH_SHORT).show();
                    }
                });









            }
        });


    }
}
