package com.imagepicklibtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.imagepicker.FilePickUtils;
import com.imagepicker.LifeCycleCallBackManager;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    protected ImageView imageView;

    @BindView(R.id.galleryButton)
    protected Button galleryButton;

    @BindView(R.id.cameraButton)
    protected Button cameraButton;

    private LifeCycleCallBackManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
            @Override
            public void onFileChoose(String s, int i) {
                File file = new File(s);
                Picasso.with(MainActivity.this).load(file).into(imageView);
            }
        };

        final FilePickUtils utils = new FilePickUtils(this, onFileChoose);
        manager = utils.getCallBackManager();

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.requestImageGallery(FilePickUtils.STORAGE_PERMISSION_IMAGE, true, true);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.requestImageCamera(FilePickUtils.CAMERA_PERMISSION, true, true);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(manager != null){
            manager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(manager != null){
            manager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
