package com.example.talkapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Call extends AppCompatActivity {
EditText number;
ImageButton call;
String num;
AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        number=(EditText)findViewById(R.id.number);
        call=(ImageButton)findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED)
                    {
                        num=number.getText().toString().trim();
                        if (TextUtils.isEmpty(num))
                        {
                            dialog=new AlertDialog.Builder(Call.this);
                            dialog.setMessage("Please Enter the Phone Number!!!");
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog=dialog.create();
                            alertDialog.show();
                        }
                        else
                        {
                            String cal="tel:"+num;
                            Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(cal));
                            startActivity(intent);
                        }


                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                }
            }
        });
    }
}