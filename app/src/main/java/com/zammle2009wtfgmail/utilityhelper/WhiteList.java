package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import java.util.ArrayList;
import java.util.List;


public class WhiteList extends AppCompatActivity {

    public EditText editText;
    public TextView textView;
    public Button save, load;

    static String filename = "utilityhelperstorage.txt";
    static  String filename2 = "utilityhelpercloselist.txt";
    List<templateHolder> AppInfo;

    static String text = "";


////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        save = (Button) findViewById(R.id.buttonsave);
        load = (Button) findViewById(R.id.buttonload);



        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            FileOutputStream fos2 = openFileOutput(filename2, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            fos2.close();
            //Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }




        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////// Loading on create. Compares Whitelist with List of apps  ///////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> list = new ArrayList<>();


        if (CloseList.CreateOnce == 0)
        {
            String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
            String hold = readFile(WhiteList.filename2);
            Boolean copy = false;

            String[] TextWithTime = hold.split(System.getProperty("line.separator"));

            for (int i = 0; i < TextWithTime.length; ++i) {
                list.add(TextWithTime[i]);
            }


            for (int i = 0; i < newText.length; ++i) {

                for (int z = 0; z < list.size(); z += 3) {
                    if (newText[i] == list.get(z)) {

                        copy = true;

                        MainActivity.ToReturn += list.indexOf(z);
                        MainActivity.ToReturn += list.indexOf(z + 1);
                        MainActivity.ToReturn += list.indexOf(z + 2);
                    }

                }


                if (copy == false) {
                    list.add(newText[i] + (System.getProperty("line.separator")));
                    list.add("15" + (System.getProperty("line.separator")));
                    list.add("0" + (System.getProperty("line.separator")));

                    MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "0" + (System.getProperty("line.separator"));


                }

                copy = false;


            }

            saveFile(WhiteList.filename2, MainActivity.ToReturn);
            CloseList.CreateOnce += 1;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////



        textView.setText(MainActivity.ToReturn);



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////Setting Buttons ///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(readFile(filename));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                saveFile(filename, text );

                //   saveFile(filename, editText.getText().toString() );
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }


    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            //Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            //  Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }



    }


    public String readFile (String file)
    {
        String textread = "";

        try
        {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textread = new String(buffer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(WhiteList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }




}