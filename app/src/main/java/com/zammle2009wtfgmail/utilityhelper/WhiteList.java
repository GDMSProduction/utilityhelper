package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
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
import java.util.List;


public class WhiteList extends AppCompatActivity {

    public EditText editText;
    public TextView textView;
    public Button save, load;

    String filename = "utilityhelperstorage.txt";
    String filename2 = "utilityhelpercloselist.txt";
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
            fos.write(text.getBytes());
            fos.close();
            //Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String textread = "";
        String[] Split;

        String tryread = "";
        int tryint = 60;
        boolean trybool = false;

        try
        {
            FileInputStream fis = openFileInput(filename2);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textread = new String(buffer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        Split = text.split(System.getProperty("line.separator"));

        for (int i = 0; i < Split.length; ++ i)
        {
            if (i % 3 == 0)
            {
                tryread = Split[i];
            }

            else if (i % 3 == 1)
            {
                try {


                    tryint = Integer.parseInt(Split[i]);
                }
                catch (Exception e)
                {}

            }

            else if (i % 3 == 2)
            {
                try {


                    trybool = Boolean.parseBoolean(Split[i]);
                }
                catch (Exception e)
                {}

                templateHolder hold = new templateHolder();

                hold.setName(tryread);
                hold.setInt(tryint);
                hold.setBools(trybool);

                AppInfo.add(hold);



            }
        }

        String[] Split2 = text.split(System.getProperty("line.separator"));

        Boolean found = false;

        for (int x = 0; x < Split2.length; ++x)
        {

            for (int y = 0; y < AppInfo.size(); ++y)
            {
                if (Split2[x] == AppInfo.get(y).getname() )
                {
                    found = true;
                    break;
                }
            }

            if (found == false)
            {
                templateHolder Adding = new templateHolder();
                Adding.setName(Split2[x]);
                Adding.setInt(15);
                Boolean b = false;
                Adding.setBools(b);

                AppInfo.add(Adding);


            }

            found = false;

        }



        textView.setText(textread);





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




    }



    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(WhiteList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }



}