package com.example.root.qrcode;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int  REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
            }
            else
            {
                RequestPermission();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onResume() {

        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkPermission())
            {
                if(scannerView == null)
                {
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);

                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
            {
                RequestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        scannerView.stopCamera();
    }

    private void RequestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CAMERA:
                    {
                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        {
                                Toast.makeText(MainActivity.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_SHORT).show();
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                                {
                                    DisplayDialog("You should grant Permission", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
                                            }
                                        }
                                    });
                                    return;
                                }

                            }
                        }
                    }
                    break;
        }
    }

    private void DisplayDialog(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK",listener)
                .setNegativeButton("Cancel",null)
                .create();
    }

    @Override
    public void handleResult(Result result) {
        final String answer = result.getText();
        Toast.makeText(getApplicationContext(),answer,Toast.LENGTH_SHORT).show();
        scannerView.resumeCameraPreview(MainActivity.this);
    }
}
