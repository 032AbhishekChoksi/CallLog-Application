package com.example.calllog_application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG}, PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.txtrslt);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void btnCallLog(View view)
    {
        textView.setText("Call Logging Started...");

        String str_output = "";

        Uri uri = Uri.parse("content://call_log/calls");
        Cursor cursorCalllogs = getContentResolver().query(uri,null,null,null);

        cursorCalllogs.moveToFirst();

        do {
            @SuppressLint("Range") String strNumber = cursorCalllogs.getString(cursorCalllogs.getColumnIndex(CallLog.Calls.NUMBER));
            @SuppressLint("Range") String strName = cursorCalllogs.getString(cursorCalllogs.getColumnIndex(CallLog.Calls.CACHED_NAME));
            @SuppressLint("Range") String strDuration = cursorCalllogs.getString(cursorCalllogs.getColumnIndex(CallLog.Calls.DURATION));
            @SuppressLint("Range") String strType = cursorCalllogs.getString(cursorCalllogs.getColumnIndex(CallLog.Calls.TYPE));

            str_output = "Number: " + strNumber
                    + "\nName: " + strName
                    + "\nDuration: " + strDuration
                    + "\nType: " + strType + "\n\n";
        }while (cursorCalllogs.moveToNext());

        textView.setText(str_output);
    }
}