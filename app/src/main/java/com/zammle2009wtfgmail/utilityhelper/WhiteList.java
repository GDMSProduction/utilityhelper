package com.zammle2009wtfgmail.utilityhelper;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class WhiteList extends AppCompatActivity {

    public EditText editText;
    public TextView textView;
    public Button save, load;

    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "UtilityHelperStorage";


    //////////////////////////// SAVE //////////////////////////////////////////////
    public static void Save(File file, String[] data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                for (int i = 0; i < data.length; i++) {
                    fos.write(data[i].getBytes());
                    if (i < data.length - 1) {
                        fos.write("\n".getBytes());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //////////////////////// LOAD /////////////////////////////////////////////////////////
    public static String[] Load(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl = 0;
        try {
            while ((test = br.readLine()) != null) {
                anzahl++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis.getChannel().position(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try {
            while ((line = br.readLine()) != null) {
                array[i] = line;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return array;
    }
////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        save = (Button) findViewById(R.id.buttonsave);
        load = (Button) findViewById(R.id.buttonload);

        File dir = new File(path);
        dir.mkdir();


    }


    public void buttonSave(View view) {
        File file = new File(path + "savedFile.txt");
        String[] saveText = String.valueOf(editText.getText()).split(System.getProperty("line.seperator"));

        editText.setText("");
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Save(file, saveText);
    }

    public void buttonLoad(View view) {
        File file = new File(path + "savedFile.txt");
        String[] loadText = Load(file);
        String finalString = "";

        for (int i =0; i < loadText.length; ++i)
        {
            finalString += loadText[i] + System.getProperty("line.separator");

        }
            textView.setText(finalString);
    }





}