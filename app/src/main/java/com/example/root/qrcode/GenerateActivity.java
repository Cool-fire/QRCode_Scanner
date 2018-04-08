package com.example.root.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

public class GenerateActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button generateBttn;
    private EditText editField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        editField = (EditText)findViewById(R.id.writeID);
        generateBttn = (Button)findViewById(R.id.but);
        imageView = (ImageView)findViewById(R.id.barcode);
        generateBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text = editField.getText().toString();
                if(!Text.isEmpty())
                {
                    Log.d("Activity", "onClick: "+Text);
                    generateBarcode(Text);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter ID",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void generateBarcode(String text) {
        if(text != null)
        {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please enter",Toast.LENGTH_SHORT).show();
        }

    }

}
