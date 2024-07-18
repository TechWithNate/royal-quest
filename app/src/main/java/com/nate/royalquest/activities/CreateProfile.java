package com.nate.royalquest.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.nate.royalquest.R;

public class CreateProfile extends AppCompatActivity {

    private ImageView userImg;
    private EditText username;
    private AutoCompleteTextView genderAutoComplete;
    private AutoCompleteTextView gradeAutoComplete;
    private String gender;
    private String grade;
    private static final int PICK_IMAGE = 100;
    private String imageUriAccessToken;
    private Uri imageUri;
    private MaterialButton createProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();


        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderOptions);
        genderAutoComplete.setAdapter(adapter);
        genderAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            gender = (String) parent.getItemAtPosition(position);
        });

        String[] gradeOptions = {"Primary 1", "Primary 2", "Primary 3", "Primary 4", "Primary 5", "JHS 1", "JHS 2", "JHS 3"};
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, gradeOptions);
        gradeAutoComplete.setAdapter(gradeAdapter);
        gradeAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            grade = (String) parent.getItemAtPosition(position);
        });

        userImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });

        createProfileBtn.setOnClickListener(v -> checkFields());

    }

    private void initViews(){
        userImg = findViewById(R.id.user_img);
        username = findViewById(R.id.username);
        genderAutoComplete = findViewById(R.id.gender);
        gradeAutoComplete = findViewById(R.id.grade);
        createProfileBtn = findViewById(R.id.create_profile_btn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            userImg.setImageURI(imageUri);
        }
    }

    private void checkFields(){
        if (username.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }else if (gender == null){
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        }else if (grade == null){
            Toast.makeText(this, "Please select your grade", Toast.LENGTH_SHORT).show();
        }else{
            createProfile(username.getText().toString(), gender, grade);
        }
    }

    private void createProfile(String username, String gender, String grade) {
    }
}