package com.example.f1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TextView usern, team, driver;

    private Button changeFav;
    private Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        usern = findViewById(R.id.username);
        team = findViewById(R.id.favoriteTeam);
        driver = findViewById(R.id.favoriteDriver);

        changeFav = findViewById(R.id.changeFavorites);
        logout = findViewById(R.id.logout);
        //listView = findViewById(R.id.list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
   	  	Log.d("User", "No user found");
	    }

        DocumentReference ref = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        ref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
		@Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String temp = "Hello, " + value.getString("Username");
		        usern.setText(temp);

                temp = "Your Favorite Team is " + value.getString("Favorite Team");
                team.setText(temp);

                temp = "Your Favorite Driver is " + value.getString("Favorite Driver");
                driver.setText(temp);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });
        changeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ChangeFavoriteActivity.class));
            }
        });

//        FirebaseDatabase.getInstance().getReference().child("Programming Knowledge").child("Android").setValue("abcd");
    }
}