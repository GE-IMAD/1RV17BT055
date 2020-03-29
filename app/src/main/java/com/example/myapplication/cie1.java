package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

    EditText m1, m2, s1, s2, name;
    Button submit, show, update;
    DatabaseReference rootRef,subRef,marksRef,valuesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cie1);

        m1 = (EditText) findViewById(R.id.marks1);
        m2 = (EditText) findViewById(R.id.marks2);
        s1 = (EditText) findViewById(R.id.subject1);
        s2 = (EditText) findViewById(R.id.subject2);
        name = (EditText) findViewById(R.id.name);
        submit = (Button) findViewById(R.id.submit);
        show = (Button) findViewById(R.id.show);
        update = (Button) findViewById(R.id.update);
        rootRef = FirebaseDatabase.getInstance().getReference();



        //marksRef = subRef.child("Marks");
        //valuesRef = marksRef.child("Values");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject1 = s1.getText().toString();
                String subject2 = s2.getText().toString();
                String marks1 = m1.getText().toString();
                String marks2 = m2.getText().toString();
                String names = name.getText().toString();


                rootRef.child(names);
                rootRef.child(names).child(subject1);
                rootRef.child(names).child(subject2);
                rootRef.child(names).child(subject1).setValue(marks1);
                rootRef.child(names).child(subject2).setValue(marks2);
                Toast.makeText(getApplicationContext(), "Inserted Data into Database", Toast.LENGTH_SHORT).show();
                m1.setVisibility(View.GONE);
                m2.setVisibility(View.GONE);
                s1.setVisibility(View.GONE);
                s2.setVisibility(View.GONE);
                name.setVisibility(View.GONE);

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String subject1 = s1.getText().toString();
                String subject2 = s2.getText().toString();
                String marks1 = m1.getText().toString();
                String marks2 = m2.getText().toString();

                s1.setText(subject1 + ":" + marks1);
                s2.setText(subject2 + ":" + marks2);

                //m1.setVisibility(View.VISIBLE);
                //m2.setVisibility(View.VISIBLE);
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m1.setVisibility(View.VISIBLE);
                m2.setVisibility(View.VISIBLE);
                s1.setVisibility(View.VISIBLE);
                s2.setVisibility(View.VISIBLE);
                m1.setText("Enter updated marks");
                m2.setText("Enter updated marks");

                Toast.makeText(getApplicationContext(), "Click on submit to update", Toast.LENGTH_SHORT).show();

            }
        });







    }
}
