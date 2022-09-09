package com.example.f1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ChangeFavoriteActivity extends AppCompatActivity {

    private Spinner constructors;
    private Spinner drivers;
    private Button update;

    private ArrayAdapter<CharSequence> teamAdapter;
    private ArrayAdapter<CharSequence> driverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_favorite);

        update = findViewById(R.id.update);

        // Setting the spinners up
        constructors = findViewById(R.id.teamSelect);
        drivers = findViewById(R.id.driverSelect);

        teamAdapter = ArrayAdapter.createFromResource(this,R.array.team_array, android.R.layout.simple_spinner_item);
        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        constructors.setAdapter(teamAdapter);

        driverAdapter = ArrayAdapter.createFromResource(this,R.array.driver_array, android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drivers.setAdapter(driverAdapter);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtTeam = constructors.getSelectedItem().toString();
                String txtDriver = drivers.getSelectedItem().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference ref  = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                ref.update("Favorite Team", txtTeam);
                ref.update("Favorite Driver", txtDriver);

                Toast.makeText(ChangeFavoriteActivity.this, "Change successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangeFavoriteActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}