package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
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

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class CloseList extends AppCompatActivity {


    public Button Listsave, Listload;


    private UsageStatAdapter adapter;



////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_list);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        Listsave = (Button) findViewById(R.id.listsave);
        Listload = (Button) findViewById(R.id.listload);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new templateHolder();
        recyclerView.setAdapter(adapter);



        String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
        String hold = readFile(WhiteList.filename2);
        Boolean copy = false;

        ArrayList<String> list = new ArrayList<>();



        String[] TextWithTime = hold.split(System.getProperty("line.separator"));

        for (int i = 0; i < TextWithTime.length; ++ i)
        {
            list.add(TextWithTime[i]);
        }



        for (int i = 0; i < newText.length; ++i)
        {

            for (int z = 0; z < list.size(); z += 3)
            {
                if (newText[i] == list.get(z))
                {

                    copy = true;

                    MainActivity.ToReturn += list.indexOf(z);
                    MainActivity.ToReturn += list.indexOf(z + 1);
                    MainActivity.ToReturn += list.indexOf(z + 2);
                }

            }



            if (copy == false)
            {
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











        Listload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Listsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




         //   List <templateHolder> items ;





            recyclerView.setAdapter(adapter);




    }




    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
          //  Toast.makeText(CloseList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
          //  Toast.makeText(CloseList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
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
          //  Toast.makeText(CloseList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }














}








