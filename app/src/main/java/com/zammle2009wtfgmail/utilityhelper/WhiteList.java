package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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



    public Button save, load;

    static String filename = "utilityhelperstorage.txt";
    static  String filename2 = "utilityhelpercloselist.txt";
    List<templateHolder> AppInfo;

    static String text = "";

///////////////////////
    // MAJOR CHANGES//
    ////////////////////



    private RecyclerView mRecycle;
    private templateAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;





////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);





        save = (Button) findViewById(R.id.Save2);
        load = (Button) findViewById(R.id.Load2);
        //  Listload = (Button) findViewById(R.id.Load);















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
            CloseList.CreateOnce += 1;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ///////////////////////////// Spliting information from text file //////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        CloseList.Holder.clear();

        String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));

        for (int i = 0; i < TextWithInfo.length; i = i + 3)
        {
            if (Integer.valueOf(TextWithInfo[i+2]) == 1)
            {
                String appName = TextWithInfo[i];
                int Time = Integer.valueOf(TextWithInfo[i + 1]);
                boolean bool = true;


                CloseList.Holder.add(new templateHolder(R.drawable.defaulticon, appName, bool, Time));
            }
        }




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////// List of adapters //////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        mRecycle = findViewById(R.id.myrecycle2);
        mRecycle.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new templateAdapter(CloseList.Holder);

        mRecycle.setLayoutManager(mLayout);
        mRecycle.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener(new templateAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                changeItem(position, templateAdapter.newValue);
                changeBools(position, templateAdapter.appBool);

            }
        });




        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



       /* Listload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        //////////////////////////////////////////////////////////////////
        /////////////////////// Update Saves /////////////////////////////
        //////////////////////////////////////////////////////////////////
        load.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (WhiteList.this, CloseList.class );
                startActivity(history);
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String UpdateSave ="";



                for (int i = 0; i < CloseList.Holder.size(); ++i)
                {


                    UpdateSave += CloseList.Holder.get(i).getAppName() + (System.getProperty("line.separator"));



                    UpdateSave += CloseList.Holder.get(i).getNumberPicker() + (System.getProperty("line.separator"));

                    if (CloseList.Holder.get(i).getSwitch() == true)
                    {
                        UpdateSave += '1' + (System.getProperty("line.separator"));
                    }
                    else
                    {
                        UpdateSave += '0' + (System.getProperty("line.separator"));
                    }
                }




                MainActivity.ToReturn = UpdateSave;



                saveFile(WhiteList.filename2, MainActivity.ToReturn);
            }
        });

    }




    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);;

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
            //  Toast.makeText(CloseList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }

    public void changeItem(int position, int value)
    {
        if (templateAdapter.again == 0)
        {
            CloseList.Holder.get(position).SetValue(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.again +=1;
        }

    }


    public void changeBools(int position, boolean value)
    {
        if (templateAdapter.againBool == 0)
        {
            CloseList.Holder.get(position).SetBool(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.againBool +=1;
        }

    }

}