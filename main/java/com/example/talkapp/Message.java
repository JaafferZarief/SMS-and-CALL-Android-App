package com.example.talkapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Message extends AppCompatActivity {
EditText number,message;
ImageButton send;
String num,msg;
AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        number=(EditText)findViewById(R.id.number);
        message=(EditText)findViewById(R.id.message);
        send=(ImageButton) findViewById(R.id.sms);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
                    {
                        num=number.getText().toString().trim();
                        msg=message.getText().toString().trim();
                        if (TextUtils.isEmpty(num)||(TextUtils.isEmpty(msg)))
                        {
                            dialog=new AlertDialog.Builder(Message.this);
                            dialog.setMessage("Number Or Message Fileds Are Empty!!!");
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
                            SmsManager smsManager=SmsManager.getDefault();
                            smsManager.sendTextMessage(num,null,msg,null,null);
                            dialog=new AlertDialog.Builder(Message.this);
                            dialog.setMessage("Your Message Send Successfully");
                            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            AlertDialog alertDialog=dialog.create();
                            alertDialog.show();
                        }

                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });
    }
}