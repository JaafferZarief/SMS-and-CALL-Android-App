package com.example.talkapp;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Displayimage extends AppCompatActivity {
    ImageView imageView;
    Button b,b1;
    private static final int secretcode=1000;
    private static final int imagecode=1001;
    String filename;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayimage);
        b=(Button)findViewById(R.id.load);
        b1=(Button)findViewById(R.id.share);
        b1.setEnabled(false);
        imageView=(ImageView)findViewById(R.id.image);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
                    {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,imagecode);

                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},secretcode);
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable=imageView.getDrawable();
                Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
                try {
                    File file =new File(getApplicationContext().getExternalCacheDir(),File.separator +filename);
                    FileOutputStream fout=new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fout);
                    fout.flush();
                    fout.close();
                    file.setReadable(true,false);
                    final Intent intent=new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photo=FileProvider.getUriForFile(Displayimage.this,BuildConfig.APPLICATION_ID + ".provider", file);
                    intent.putExtra(Intent.EXTRA_STREAM,photo);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");
                    startActivity(Intent.createChooser(intent,"Share image via"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==imagecode)
        {
            imageView.setImageURI(data.getData());
            uri=data.getData();
            //dcim//downlaod/omage/sdcard/img_12345.jpg
            filename=uri.getLastPathSegment();
            int slash=filename.lastIndexOf('/');
            filename=filename.substring(slash+1);
            if(filename!=null)
            {
                b1.setEnabled(true);
            }

        }

    }
}

