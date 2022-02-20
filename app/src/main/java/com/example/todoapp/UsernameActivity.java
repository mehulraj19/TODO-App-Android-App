package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.model.Username;
import com.example.todoapp.util.Utils;

import java.util.prefs.Preferences;

public class UsernameActivity extends AppCompatActivity {

    EditText enterUsername;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);


        enterUsername = findViewById(R.id.enterUsernameText);
        saveButton = findViewById(R.id.saveUsernameButton);


        saveButton.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(Utils.USERNAME_ID, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", enterUsername.getText().toString().trim());
            editor.apply();
            Intent i = new Intent();
            finish();
            Toast.makeText(this, "Username saved!! Restart App", Toast.LENGTH_SHORT).show();
            this.finishAffinity();
        });



    }
}