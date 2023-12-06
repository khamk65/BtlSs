package com.example.appeditvideo;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

public class MainActivity extends AppCompatActivity {
    ImageButton imageButtonCrop, imageButtonCamera;
    ImageView imageView, imageCamera;

    int IMAGE_REQUEST_CODE = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButtonCrop = findViewById(R.id.btn_crop);
        imageButtonCamera = findViewById(R.id.btn_camera);
        imageCamera = findViewById(R.id.IViewCamera);
        imageButtonCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Uri photoUri = saveImageAndGetUri(photo);

            Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
            dsPhotoEditorIntent.setData(photoUri);
            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "photoEditor");
            int[] toolsToHide = {};

            dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
            startActivityForResult(dsPhotoEditorIntent, 200);
        }
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (data.getData() != null) {
                Uri filePath = data.getData();
                Intent dsPhotoEditorIntent = new Intent(this, DsPhotoEditorActivity.class);
                dsPhotoEditorIntent.setData(filePath);
                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "photoEditor");
                int[] toolsToHide = {};

                dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, toolsToHide);
                startActivityForResult(dsPhotoEditorIntent, 200);
            }
        }

        if (requestCode == 200) {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.setData(data.getData());
            startActivity(intent);
        }

    }

    private Uri saveImageAndGetUri(Bitmap image) {
        String savedImagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                image,
                "Title",
                "Description"
        );

        return Uri.parse(savedImagePath);
    }
}