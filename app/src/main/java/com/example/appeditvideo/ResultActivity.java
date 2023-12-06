package com.example.appeditvideo;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {
    private ImageView imageViewPreview;
    private ImageButton mButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imageViewPreview = findViewById(R.id.img_result);
        mButton=(ImageButton) findViewById(R.id.btnBack);
        imageViewPreview.setImageURI(getIntent().getData());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}